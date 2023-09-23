package com.nhg.game.command.impl;

import com.nhg.game.command.Command;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.user.User;
import com.nhg.game.user.UserService;
import com.nhg.game.utils.BeanRetriever;
import lombok.NonNull;

public class CreditsCommand implements Command {

    private final UserService userService;

    public CreditsCommand() {
        userService = BeanRetriever.get(UserService.class);
    }

    @Override
    public String getName() {
        return "credits";
    }

    @Override
    public void execute(@NonNull User user, String... params) throws Exception {
        User receiver = userService.getUserByUsername(params[0]);

        if (receiver == null) return;

        int creditsAmount;

        try {
            creditsAmount = Integer.parseInt(params[1]);
        } catch(NumberFormatException ignored) {
            //TODO client command error
            return;
        }

        receiver.updateCredits(creditsAmount);

        userService.save(receiver);
        receiver.getClient().sendMessage(new ServerPacket(OutgoingPacketHeaders.UpdateUserInformation, receiver));
    }
}
