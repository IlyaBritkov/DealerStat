package com.leverx.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "game")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName(value = "Game")
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Getter
@Setter
@ToString(callSuper = true)
public class Game extends AbstractBaseEntity {
    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "description")
    @Nullable
    private String description;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm z")
    @NotNull
    private ZonedDateTime createdAt;

    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.DETACH,
                    CascadeType.REFRESH})
    @JoinTable(name = "user_game",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> traders = new ArrayList<>();

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "game",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL})
    private List<Feedback> feedbacks = new ArrayList<>();

    public Game(@NotNull String name, @Nullable String description, @NotNull ZonedDateTime createdAt) {
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }

    public boolean addTrader(User user) {
        return traders.add(user);
    }

}
