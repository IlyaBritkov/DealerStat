package com.leverx.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "game")
@NoArgsConstructor
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
    @Enumerated(EnumType.STRING)
    @NotNull
    private OffsetDateTime createdAt;
}
