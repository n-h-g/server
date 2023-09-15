package com.nhg.game.item;

import com.nhg.game.utils.PostgreSQLEnumType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.List;


@Getter
@Setter
@javax.persistence.Entity
@Slf4j
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
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
    @Type(type = "pgsql_enum")
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
