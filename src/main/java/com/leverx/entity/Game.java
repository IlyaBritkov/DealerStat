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

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinTable(name = "user_game",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @ToString.Exclude
    private List<User> traders = new ArrayList<>();


    public boolean addTrader(User user) {
        return traders.add(user);
    }

}
