package com.nhg.game.command;

import com.nhg.game.command.impl.TestCommand;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CommandHandler {

    @Value("${messages.command_prefixes}")
    private String[] commandPrefixes;

    private final Map<String, Command> commands;

    public CommandHandler() {
        commands = new HashMap<>();
        registerCommands();
    }

    /**
     * Check the given text: if it starts with a command prefix and has a valid command name then execute the command.
     *
     * @param text the text that could be a command.
     * @return true if the command is executed correctly, false if the text doesn't contain a valid command.
     */
    public boolean handle(@NonNull String text) {
        if (!hasCommandPrefixes(text)) return false;

        // remove the command prefix
        text = text.substring(1);

        String[] nameAndParams = text.split(" ", 2);

        String name = nameAndParams[0];
        String[] params = new String[]{};

        if (nameAndParams.length > 1) {
            params = nameAndParams[1].split(" ");
        }

        Command command = commands.get(name);

        if (command == null) return false;

        try {
            command.execute(params);
        } catch(Exception e) {
            log.error("Error while handling command '"+ command.getName() +"': "+ e);
        }

        return true;
    }

    /**
     * Check if the given text starts with any of the command prefixes.
     *
     * @param text the text to check.
     * @return true if the text starts with any of the command prefixes, false otherwise.
     */
    private boolean hasCommandPrefixes(@NonNull String text) {
        return StringUtils.startsWithAny(text, commandPrefixes);
    }

    private void registerCommands() {
        Command[] commands = {
                new TestCommand()
        };

        for (Command command : commands) {
            this.commands.put(command.getName(), command);
        }
    }
}
