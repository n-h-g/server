package com.nhg.messenger.friendship;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "friendships")
public class Friendship {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private Integer senderId;

    @Column(nullable = false)
    private Integer destinationId;

    @Column
    private boolean pending;
}
