package com.nhg.game.npc.bot;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class BotSpeech {

    @Id
    @GeneratedValue(
            generator = "sequence_bot_speech_id",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "sequence_bot_speech_id",
            sequenceName = "sequence_bot_speech_id"
    )
    private Integer id;

    @Column(nullable = false)
    private String speech;

    @ManyToOne
    @JoinColumn(columnDefinition="integer", name="bot_id")
    private Bot bot;


}
