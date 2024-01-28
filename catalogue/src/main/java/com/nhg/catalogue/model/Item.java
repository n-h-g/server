package com.nhg.catalogue.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

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
    @JsonIgnore
    @ManyToOne
    @JoinColumn(columnDefinition="integer", name="page_id")
    private Page page;
}
