package com.nhg.game.bot;

import com.nhg.game.shared.HumanData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.nhg.game.shared.PersistentPosition;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Getter
@NoArgsConstructor
@javax.persistence.Entity
public class Bot {

    @Id
    @GeneratedValue(
            generator = "sequence_bot_id",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "sequence_bot_id",
            sequenceName = "sequence_bot_id"
    )
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Embedded
    private PersistentPosition position;

    @Embedded
    private HumanData humanData;
}
