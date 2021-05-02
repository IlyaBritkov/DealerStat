package com.leverx.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

public enum FeedbackDTO {
    ;

    private interface Id {
        @Positive Integer getId();
    }

    private interface Message {
        @Nullable String getMessage();
    }

    private interface TraderId {
        @Positive Integer getTraderId();
    }

    private interface GameId {
        @Positive Integer getGameId();
    }

    private interface Approved {
        @Nullable Boolean getApproved();
    }

    private interface Rating {
        @NotBlank String getRating();
    }

    public enum Request {
        ;

        @NoArgsConstructor
        @Data
        public static class Create implements Message, TraderId, GameId, Rating{
            private String message;
            private Integer traderId;
            private Integer gameId;
            private String rating;
        }

        @NoArgsConstructor
        @Data
        public static class Update implements Id, Approved {
            private Integer id;
            private Boolean approved;
        }
    }

    public enum Response {
        ;

        @NoArgsConstructor
        @Data
        public static class Public implements Id, Message, TraderId, GameId, Rating {
            private Integer id;
            private String message;
            private Integer traderId;
            private Integer gameId;
            private String rating;
        }
    }
}
