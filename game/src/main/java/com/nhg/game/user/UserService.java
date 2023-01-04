package com.nhg.game.user;

import com.nhg.game.room.RoomService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoomService roomService;


    /**
     * Map of active users online.
     */
    private final Map<Integer, User> activeUsers = new ConcurrentHashMap<>();

    /**
     * Add user in game when he gets online
     **/
    public void connect(@NonNull User user) {
        user.setOnline(true);

        this.activeUsers.putIfAbsent(user.getId(), user);
    }

    /**
     * Disconnect the user from the game.
     * If the user is in a room its remove it
     *
     * @param user user to disconnect
     * @see RoomService#userExitRoom 
     */
    public void disconnect(@NonNull User user) {
        user.setOnline(false);
        roomService.userExitRoom(user);

        this.activeUsers.remove(user.getId());
    }

    public void createUser(User user) {
        this.userRepository.save(user);
    }

    /**
     * Retrieve an online user
     *
     * @param id active room id
     * @return User
     */
    public User getActiveUserById(Integer id) {
        return this.activeUsers.get(id);
    }

    /**
     * Get the user with the given username.
     *
     * @param username user's username
     * @return the User if it exists, null otherwise.
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    /**
     * Get the user with the given id.
     *
     * @param id user's id.
     * @return the User if it exists, null otherwise.
     */
    public User getUserById(int id) {
        return activeUsers.containsKey(id)
            ? this.getActiveUserById(id)
            : userRepository.findById(id).orElse(null);
    }

    /**
     * Check if the user with the given id exists.
     *
     * @param id user's id.
     * @return true if the user exits.
     */
    public boolean existsWithId(int id) {
        return userRepository.existsById(id);
    }
}
