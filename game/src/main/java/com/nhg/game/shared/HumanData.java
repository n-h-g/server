package com.nhg.game.shared;

import com.nhg.game.utils.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class HumanData {

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "gender default 'MALE'")
    private Gender gender;

    @Column(nullable = false, columnDefinition = "varchar(255) default 'hd-180-1.ch-255-66.lg-280-110.sh-305-62.ha-1012-110.hr-828-61'")
    private String look;

    public HumanData(Gender gender, String look) {
        this.gender = gender;
        this.look = look;
    }

    public HumanData(String look) {
        this.gender = Gender.MALE;
        this.look = look;
    }
}
