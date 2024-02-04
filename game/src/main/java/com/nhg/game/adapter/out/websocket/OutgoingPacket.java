package com.nhg.game.adapter.out.websocket;

import com.nhg.game.adapter.in.websocket.ClientUserMap;
import com.nhg.game.domain.user.User;
import com.nhg.game.infrastructure.context.BeanRetriever;
import com.nhg.game.infrastructure.networking.Client;
import com.nhg.game.infrastructure.networking.packet.Packet;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

@Getter
@Setter
@Slf4j
public class OutgoingPacket implements Packet<Integer, JSONObject> {

    protected Integer header;
    protected JSONObject body;
    protected Client<?> client;

    @Override
    public String toString() {
        return "{\"header\": " + this.getHeader() + ", \"body\": " + this.body.toString() + "}";
    }

    private OutgoingPacket(int header) {
        this.header = header;
        this.body = new JSONObject();
    }

    public static void send(@NonNull Client<?> client, int header, @NonNull Object data) {
        OutgoingPacket packet = new OutgoingPacket(header);

        if (data instanceof JSONObject jsonObject) {
            packet.body = jsonObject;
        } else {
            packet.body.put("data", data);
        }

        client.sendMessage(packet);
    }

    public static void send(@NonNull Iterable<User> users, int header, @NonNull Object data) {
        ClientUserMap clientUserMap = BeanRetriever.get(ClientUserMap.class);

        users.forEach(user -> {
            if (user == null) return;

            Client<?> client = clientUserMap.getClient(user.getId());

            send(client, header, data);
        });
    }

}
