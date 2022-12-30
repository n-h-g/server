package com.nhg.catalogue.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(
            generator = "sequence_catalogue_item_id",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "sequence_catalogue_item_id",
            sequenceName = "sequence_catalogue_item_id"
    )
    private Integer id;

    private String name;

    /**
     * Amount of copies for a single item purchased.
     */
    private int amount;

    /**
     * The amount of credits this item can be purchased for.
     */
    protected int credits;

    /**
     * Pages where this item is exposed.
     */
    @ManyToOne
    @JoinColumn(columnDefinition="integer", name="page_id")
    private Page page;
}
