package com.leverx.entity;

import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "game")
@NoArgsConstructor
@RequiredArgsConstructor
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

    @Column(name = "created_at")
    @NotNull
    private OffsetDateTime createdAt;

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

    @ToString.Exclude
    @OneToMany(mappedBy = "game",
            fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.DETACH,
                    CascadeType.REFRESH})
    private List<Feedback> feedbacks = new ArrayList<>();

    public Game(@NotNull String name, @Nullable String description, @NotNull OffsetDateTime createdAt) {
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }

    public boolean addTrader(User user) {
        return traders.add(user);
    }

}
