package com.leverx.entity;

import lombok.*;
import org.hibernate.annotations.Type;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "feedback")
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Getter
@Setter
@ToString(callSuper = true)
public class Feedback extends AbstractBaseEntity {
    @Column(name = "message")
    private String message;

    @ManyToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH})
    @JoinColumn(name = "trader_id")
    private User trader;

    @ManyToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH})
    @JoinColumn(name = "game_id")
    private Game game;

    @Column(name = "created_at")
    @NotNull
    private OffsetDateTime createdAt;

    @Column(name = "approved")
    private Boolean approved;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "rating_enum")
    @Type(type = "pgsql_enum")
    @NotNull
    private RatingEnum rating;

    public Feedback(String message, User trader, Game game, @NotNull OffsetDateTime createdAt, @NotNull RatingEnum rating) {
        this.message = message;
        this.trader = trader;
        this.game = game;
        this.createdAt = createdAt;
        this.rating = rating;
    }
}
