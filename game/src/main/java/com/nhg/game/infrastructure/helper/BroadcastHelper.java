package com.nhg.game.infrastructure.helper;


import com.nhg.game.adapter.in.websocket.ClientUserMap;
import com.nhg.game.domain.user.User;
import com.nhg.game.infrastructure.context.BeanRetriever;
import com.nhg.game.infrastructure.networking.Client;
import com.nhg.game.infrastructure.networking.packet.Packet;
import lombok.NonNull;

public class BroadcastHelper {

    public static void sendBroadcastMessage(@NonNull Iterable<User> users, @NonNull Packet<?,?> packet) {
        ClientUserMap clientUserMap = BeanRetriever.get(ClientUserMap.class);

        users.forEach(user -> {
            if (user == null) return;

            Client<?> client = clientUserMap.getClient(user.getId());

            client.sendMessage(packet);
        });
    }
}
