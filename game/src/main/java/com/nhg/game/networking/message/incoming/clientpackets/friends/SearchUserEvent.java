package com.nhg.game.networking.message.incoming.clientpackets.friends;

import com.nhg.game.dto.UserSearchFilterRequest;
import com.nhg.game.dto.UserSearchFilterResponse;
import com.nhg.game.networking.WebSocketClient;
import com.nhg.game.networking.message.incoming.ClientPacket;
import com.nhg.game.networking.message.outgoing.OutgoingPacketHeaders;
import com.nhg.game.networking.message.outgoing.ServerPacket;
import com.nhg.game.user.User;
import com.nhg.game.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
public class SearchUserEvent extends ClientPacket {

    private final RestTemplate restTemplate;
    private final UserService userService;

    public SearchUserEvent() {
        restTemplate = this.getBean("restTemplate", RestTemplate.class);
        userService = this.getBean(UserService.class);
    }

    @Override
    public void handle() {
        try {
            WebSocketClient wsClient = (WebSocketClient) client;

            String username = body.getString("username");

            User user = wsClient.getUser();

            List<User> users = userService.filterUser(username);

            List<User> userList = users.stream().filter(fUser -> !fUser.getUsername().equals(user.getUsername())).toList();

            wsClient.sendMessage(new ServerPacket(
                    OutgoingPacketHeaders.UserSearchFiltering,
                    userList
            ));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
