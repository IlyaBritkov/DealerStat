package com.leverx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Permission {
    GAMES_READ("games:read"),
    GAMES_WRITE("games:write"),

    APPROVED_USERS_READ("approved_users:read"),
    NOT_APPROVED_USERS_READ("not_approved_users:read"),
    USERS_WRITE("users:write");

    private final String permission;

}
