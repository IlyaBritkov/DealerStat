package com.leverx.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Value;
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

        @Value
        public static class Create implements Name, Description {
            String name;
            String description;

        }
    }

    public enum Response {
        ;

        @Value
        public static class Public implements Id, Name, Description {
            Integer id;
            String name;
            String description;
        }
    }
}