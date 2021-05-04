package com.leverx.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

public enum GameDTO {
    ;

    private interface Id {
        @Positive Integer getId();
    }

    private interface TraderId {
        @Positive Integer getTraderId();
    }

    private interface Name {
        @NotBlank String getName();
    }

    private interface Description {
        @Nullable String getDescription();
    }

    public enum Request {
        ;

        @NoArgsConstructor
        @Data
        public static class Create implements TraderId, Name, Description {
            private String name;
            private String description;
            private Integer traderId;

        }

        @NoArgsConstructor
        @Data
        public static class Update implements Name, Description {
            private String name;
            private String description;

        }
    }

    public enum Response {
        ;

        @NoArgsConstructor
        @Data
        public static class Public implements Id, Name, Description {
            private Integer id;
            private String name;
            private String description;
        }
    }
}