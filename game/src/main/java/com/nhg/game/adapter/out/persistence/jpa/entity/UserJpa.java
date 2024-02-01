package com.nhg.game.adapter.out.persistence.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nhg.game.domain.shared.human.Gender;
import com.nhg.game.domain.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserJpa {

    @Id
    @GeneratedValue(
            generator = "sequence_user_id",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "sequence_user_id",
            sequenceName = "sequence_user_id"
    )
    private Integer id;

    @Column(nullable = false)
    private String username;

    @Column(columnDefinition = "varchar(255) default 'Welcome to NHG'")
    private String motto;

    @Column(columnDefinition = "integer default 0")
    private int credits;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "gender default 'MALE'")
    private Gender gender;

    @Column(nullable = false, columnDefinition = "varchar(255) default 'hd-180-1.ch-255-66.lg-280-110.sh-305-62.ha-1012-110.hr-828-61'")
    private String look;

    /**
     * Rooms owned by the user
     */
    @JsonIgnore
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RoomJpa> rooms;

    public User toUser() {
        return User.builder()
                .id(id)
                .username(username)
                .motto(motto)
                .credits(credits)
                .gender(gender)
                .look(look)
                .build();
    }

    public static UserJpa fromDomain(User user) {
        UserJpa userJpa = UserJpa.builder()
                .username(user.getUsername())
                .motto(user.getMotto())
                .credits(user.getCredits())
                .gender(user.getGender())
                .look(user.getLook())
                .build();

        if (user.getId() != null) {
            userJpa.setId(user.getId());
        }

        return userJpa;
    }
}
