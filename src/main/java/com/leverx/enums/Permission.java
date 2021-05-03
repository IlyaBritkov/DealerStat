package com.leverx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Permission {
    NOT_APPROVED_USERS_READ("not_approved_users:read"),
    USERS_UPDATE("users:update"),
    NOT_APPROVED_USERS_UPDATE("not_approved_users:update"),

    GAMES_ADD("games:add"),
    GAMES_UPDATE("games:update"),
    GAMES_DELETE("games:delete"),

    NOT_APPROVED_FEEDBACKS_READ("not_approved_feedbacks:read"),
    FEEDBACKS_UPDATE("feedbacks:update");

    private final String permission;

}
