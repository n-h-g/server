package com.nhg.messenger.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Friendship {

    @Getter
    @Setter
    @Embeddable
    public static class Id implements Serializable {
        /**
         * One of the two users who forms the friendship.
         * In case <code>pending</code> is true, it represents the sender of a friendship request.
         *
         * @see #pending
         */
        private Integer user1;
        /**
         * One of the two users who forms the friendship.
         * In case <code>pending</code> is true, it represents the recipient of a friendship request.
         *
         * @see #pending
         */
        private Integer user2;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Id that = (Id) o;
            return user1.equals(that.user1) && user2.equals(that.user2)
                    || user1.equals(that.user2) && user2.equals(that.user1);
        }

        @Override
        public int hashCode() {
            return Objects.hash(user1, user2);
        }
    }

    @EmbeddedId
    private Id id;

    /**
     * If it's true, this class represents a friendship request,
     * otherwise it represents an already accepted friendship.
     */
    private boolean pending;
}