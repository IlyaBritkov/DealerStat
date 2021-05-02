package com.leverx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Permission {
    GAMES_READ("games:read"),
    GAMES_WRITE("games:write"),

    NOT_APPROVED_USERS_READ("not_approved_users:read"),
    USERS_UPDATE("users:update"),
    NOT_APPROVED_USERS_UPDATE("not_approved_users:update");

    private final String permission;

}
