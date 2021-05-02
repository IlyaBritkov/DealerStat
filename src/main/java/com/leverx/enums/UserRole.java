package com.leverx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum UserRole {
    TRADER(Set.of(
            Permission.APPROVED_USERS_READ,
            Permission.GAMES_READ,
            Permission.GAMES_WRITE
    )),
    ADMIN(Set.of(
            Permission.NOT_APPROVED_USERS_READ,
            Permission.GAMES_READ,
            Permission.GAMES_WRITE
    ));

    private final Set<Permission> permissions;

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
