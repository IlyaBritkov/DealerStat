package com.leverx.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.leverx.enums.UserRole;
import lombok.*;
import org.hibernate.annotations.Type;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"user\"")
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Getter
@Setter
@ToString(callSuper = true)
public class User extends AbstractBaseEntity {
    @Column(name = "first_name")
    @NotNull
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    private String lastName;

    @Column(name = "email")
    @NotNull
    private String email;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm z")
    @NotNull
    private final ZonedDateTime createdAt = ZonedDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "user_role_enum")
    @Type(type = "pgsql_enum")
    @NotNull
    private UserRole role;

    @Column(name = "approved")
    @NotNull
    private Boolean approved = false;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinTable(name = "user_game",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id"))
    @ToString.Exclude
    private List<Game> games = new ArrayList<>();

    public void addGame(Game game) {
        games.add(game);
    }

}
