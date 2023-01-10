package shared;

import com.nhg.game.utils.Gender;
import com.nhg.game.utils.PostgreSQLEnumType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@Embeddable
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
@NoArgsConstructor
public class HumanData {

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "gender default 'MALE'")
    @Type(type = "pgsql_enum")
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
