package com.nhg.moderation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "word_filters")
public class WordFilter {
    @Id
    private String word;

    @Column(nullable = false)
    private String replacement;

    @Column(nullable = false, columnDefinition = "bit default b'0'")
    private Boolean hideMessage;

    @Column(nullable = false, columnDefinition = "bit default b'0'")
    private Boolean autoReport;

    @Column(nullable = false)
    private Integer muteTime;
}
