package com.nhg.game.npc.bot;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

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
