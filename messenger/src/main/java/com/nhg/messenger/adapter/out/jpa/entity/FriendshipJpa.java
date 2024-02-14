package com.nhg.messenger.adapter.out.jpa.entity;

import com.nhg.messenger.domain.Friendship;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipJpa {

    @EmbeddedId
    private Friendship.Id id;

    private boolean pending;

    public Friendship toFriendship() {
        return new Friendship(id.getUser1(), id.getUser2(), pending);
    }

    public static FriendshipJpa fromDomain(Friendship friendship) {
        return FriendshipJpa.builder()
                .id(friendship.getId())
                .pending(friendship.isPending())
                .build();
    }
}
