package com.nhg.messenger.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Friendship {

    @Id
    @GeneratedValue(
            generator = "sequence_friendship_id",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "sequence_friendship_id",
            sequenceName = "sequence_friendship_id"
    )
    private Integer id;

    @Column(nullable = false)
    private Integer senderId;

    @Column(nullable = false)
    private Integer destinationId;

    private boolean pending;
}
