package com.nhg.game.adapter.out.persistence.jpa.item;

import com.nhg.game.domain.item.ItemPrototype;
import com.nhg.game.domain.item.ItemType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ItemPrototypeJpa {

    @Id
    @GeneratedValue(
            generator = "sequence_item_spec_id",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "sequence_item_spec_id",
            sequenceName = "sequence_item_spec_id"
    )
    private Integer id;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "item_type", columnDefinition = "item_type default 'FLOOR_ITEM'")
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

    @OneToMany(mappedBy = "prototype")
    private List<ItemJpa> items;

    public ItemPrototype toItemPrototype() {
        return ItemPrototype.builder()
                .id(id)
                .itemType(itemType)
                .name(name)
                .interaction(interaction)
                .stateCount(stateCount)
                .width(width)
                .length(length)
                .height(height)
                .allowStack(allowStack)
                .allowWalk(allowWalk)
                .allowInventoryStack(allowInventoryStack)
                .allowSit(allowSit)
                .allowLay(allowLay)
                .build();
    }
}
