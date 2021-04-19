package com.leverx.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@MappedSuperclass
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull
    private Integer id;
}
