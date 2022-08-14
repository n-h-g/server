package com.cubs3d.game.user;

import com.cubs3d.game.room.Room;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    /**
     * Map of active users online.
     */
    private final Map<Integer, User> activeUsers = new ConcurrentHashMap<>();

    /**
     * Add user in a map when he gets online.
     **/
    public void userJoin(User user) {
        if(!this.activeUsers.containsKey(user.getId())) {
            this.activeUsers.put(user.getId(), user);
        }
    }

    /**
     * Remove user from the map .
     * @param user
     */
    public void userLeave(User user) {
        this.activeUsers.remove(user.getId());
    }

    /**
     * Retrieve a online user
     * @param id
     * @return User
     */
    public User getActiveUser(Integer id) {
        if(this.activeUsers.containsKey(id)) {
            return this.activeUsers.get(id);
        }

        return null;
    }

    /**
     * Check if a user is inside the map.
     * @param id
     * @return boolean
     */
    public boolean hasUserOnline(Integer id) {
        return this.activeUsers.containsKey(id);
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
    public User getUserById(int id) { return userRepository.findById(id).orElse(null); }

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
