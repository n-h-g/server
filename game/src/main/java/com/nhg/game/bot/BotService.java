package com.nhg.game.bot;

import com.nhg.game.room.Room;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class BotService {

    private final BotRepository botRepository;

    public List<Bot> getBotsByRoom(@NonNull Room room) {
        return botRepository.findByRoom(room);
    }
}
