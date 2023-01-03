package com.nhg.catalogue.model;

import lombok.*;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "page", fetch = FetchType.EAGER)
    private List<Item> items;

    @Override
    public int compareTo(@NonNull Page o) {
        return sequence - o.sequence;
    }
}
