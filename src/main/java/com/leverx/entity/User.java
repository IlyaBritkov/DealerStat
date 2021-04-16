package com.leverx.entity;

import lombok.*;
import org.hibernate.annotations.Type;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "user")
@NoArgsConstructor
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

    @Column(name = "created_at")
    @NotNull
    private OffsetDateTime createdAt;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @Type(type = "enum_postgressql")
    @NotNull
    private UserRole role;
}
