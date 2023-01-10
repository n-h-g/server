package com.nhg.catalogue.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Page implements Comparable<Page> {

    @Id
    @GeneratedValue(
            generator = "sequence_catalogue_page_id",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "sequence_catalogue_page_id",
            sequenceName = "sequence_catalogue_page_id"
    )
    private Integer id;

    private String title;

    private String description;

    /**
     * The pages are displayed on the catalog with this order.
     */
    private int sequence;

    @JsonIgnore
    @OneToMany(mappedBy = "page", fetch = FetchType.EAGER)
    private List<Item> items;

    @Override
    public int compareTo(@NonNull Page o) {
        return sequence - o.sequence;
    }
}
