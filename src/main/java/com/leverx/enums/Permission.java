package com.leverx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Permission {
    GAMES_READ("games:read"),
    GAMES_WRITE("games:write");

    private final String permission;

}
