package com.nhg.game.item;

import jakarta.persistence.Convert;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.List;


@Getter
@Setter
@jakarta.persistence.Entity
@Slf4j
public class ItemSpecification {

    @Id
    @GeneratedValue(
            generator = "sequence_item_spec_id",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "sequence_item_spec_id",
            sequenceName = "sequence_item_spec_id"
    )
    private Integer Id;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "item_type default 'FLOOR_ITEM'", nullable = false)
    private ItemType itemType;

    private String name;

    private String interaction;

    private short stateCount;

    private short width;
    private short length;
    private float height;

    private boolean allowStack;
    private boolean allowWalk;
    private boolean allowInventoryStack;
    private boolean allowSit;
    private boolean allowLay;

    @OneToMany(mappedBy = "itemSpecification")
    private List<Item> items;
}
