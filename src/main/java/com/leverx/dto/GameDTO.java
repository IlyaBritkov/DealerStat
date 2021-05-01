package com.leverx.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

// todo validation
public enum GameDTO {
    ;

    private interface Id {
        @Positive Integer getId();
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
        public static class Create implements Name, Description {
            String name;
            String description;

        }

        @NoArgsConstructor
        @Data
        public static class Update implements Id, Name, Description {
            Integer id;
            String name;
            String description;

        }
    }

    public enum Response {
        ;

        @NoArgsConstructor
        @Data
        public static class Public implements Id, Name, Description {
            Integer id;
            String name;
            String description;
        }
    }
}