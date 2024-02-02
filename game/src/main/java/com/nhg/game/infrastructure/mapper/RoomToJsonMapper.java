package com.nhg.game.infrastructure.mapper;

import com.nhg.game.application.repository.ActiveRoomRepository;
import com.nhg.game.domain.room.Room;
import com.nhg.game.domain.room.entity.Entity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RoomToJsonMapper {

    private final ActiveRoomRepository activeRoomRepository;

    public JSONObject roomToJson(@NonNull Room room) {
        return new JSONObject()
                .put("id", room.getId())
                .put("name", room.getName())
                .put("desc", room.getDescription())
                .put("max_users", 100)
                .put("layout", room.getRoomLayout().getLayout())
                .put("door_x", room.getRoomLayout().getDoorX())
                .put("door_y", room.getRoomLayout().getDoorY())
                .put("users_count", getUsersCount(room))
                .put("owner_id", room.getOwner().getId())
                .put("owner_name", room.getOwner().getUsername());
    }

    public JSONObject roomToNavigatorRoomJson(@NonNull Room room) {
        return new JSONObject()
                .put("id", room.getId())
                .put("name", room.getName())
                .put("users_count", getUsersCount(room))
                .put("max_users", 100);
    }

    public JSONObject roomsToNavigatorRoomsJson(@NonNull Iterable<Room> rooms) {
        JSONObject json = new JSONObject();
        JSONArray data = new JSONArray();
        try {
            for (Room room : rooms) {
                data.put(roomToNavigatorRoomJson(room));
            }

            json.put("data", data);
        } catch (JSONException ignored) {}

        return json;
    }

    private int getUsersCount(@NonNull Room room ) {
        return room.getUsers().isEmpty()
                ? activeRoomRepository.findById(room.getId())
                .map(activeRoom -> activeRoom.getUsers().size())
                .orElse(0)
                : room.getUsers().size();
    }
}
