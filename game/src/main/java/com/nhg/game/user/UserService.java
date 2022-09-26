package com.nhg.game.user;

import lombok.AllArgsConstructor;
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


    /**
     * Map of active users online.
     */
    private final Map<Integer, User> activeUsers = new ConcurrentHashMap<>();

    /**
     * Add user in game when he gets online
     **/
    public void userJoin(User user) {
        user.setOnline(true);
        if(!this.activeUsers.containsKey(user.getId())) {
            this.activeUsers.put(user.getId(), user);
        }
        this.userRepository.save(user);
    }

    /**
     * Remove user from the game.
     * @param user
     */
    public void userLeave(User user) {
        user.setOnline(false);
        this.userRepository.save(user);
        this.activeUsers.remove(user.getId());
    }

    public void createUser(User user) {
        this.userRepository.save(user);
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

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }
}
