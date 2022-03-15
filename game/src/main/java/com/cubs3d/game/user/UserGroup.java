package com.cubs3d.game.user;

import com.cubs3d.game.networking.message.Packet;
import lombok.NonNull;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An object that represents a group of users.
 * It can't contain duplicates or null objects.
 * It can also be used to send messages to users' clients.
 */
public class UserGroup implements Iterable<User> {

    /**
     * Map used internally to manage users.
     */
    private final Map<Integer, User> users;

    public UserGroup() {
        users = new ConcurrentHashMap<>();
    }

    /**
     * Add the specified user to the group.
     *
     * @param user the user to add to the group
     */
    public void add(@NonNull User user) {
        users.putIfAbsent(user.getId(), user);
    }

    /**
     * Remove the user with the specified id from the group.
     *
     * @param id the id of the user to be removed.
     */
    public void remove(Integer id) {
        users.remove(id);
    }

    /**
     * Returns the user to with the specified id, or null if this group does not contain a user with that id.
     *
     * @param id the id of the user to be returned.
     * @return the user to with the specified id, or null if this group does not contain a user with that id.
     */
    public User get(Integer id) {
        return users.get(id);
    }

    /**
     * Returns true if this group contains the user.
     *
     * @param user user whose presence in this group is to be tested.
     * @return true if the group contains the user.
     */
    public boolean contains(@NonNull User user) {
        return users.containsKey(user.getId());
    }

    /**
     * Returns the number of users in this group.
     *
     * @return the number of users in this group.
     */
    public int count() {
        return users.size();
    }

    /**
     * Send a message to all users in this group.
     *
     * @param packet the package to be sent.
     * @see com.cubs3d.game.networking.Client#SendMessage 
     */
    public void SendBroadcastMessage(@NonNull Packet<?,?> packet) {
        for (User user : users.values()) {
            if (user == null || user.getClient() == null) continue;

            user.getClient().SendMessage(packet);
        }
    }

    @Override
    public Iterator<User> iterator() {
        return users.values().iterator();
    }
}
