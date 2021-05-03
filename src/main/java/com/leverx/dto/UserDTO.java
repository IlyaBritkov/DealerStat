package com.leverx.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

public enum UserDTO {
    ;

    private interface Id {
        @Positive Integer getId();
    }

    private interface FirstName {
        @NotBlank String getFirstName();
    }

    private interface LastName {
        @NotBlank String getLastName();
    }

    private interface Email {
        @NotBlank String getEmail();
    }

    private interface Password {
        @NotBlank String getPassword();
    }

    private interface Role {
        @NotBlank String getRole();
    }

    private interface Approved {
        @Nullable Boolean getApproved();
    }

    public enum Request {
        ;

        @NoArgsConstructor
        @Data
        public static class Create implements FirstName, LastName, Email, Password, Role {
            private String firstName;
            private String lastName;
            private String email;
            private String password;
            private String role;
        }

        @NoArgsConstructor
        @Data
        public static class Update implements FirstName, LastName, Email, Password {
            private String firstName;
            private String lastName;
            private String email;
            private String password;
        }

        @NoArgsConstructor
        @Data
        public static class Approve implements Approved {
            private Boolean approved;
        }
    }

    public enum Response {
        ;

        @NoArgsConstructor
        @Data
        public static class Public implements Id, FirstName, LastName, Email, Role {
            private Integer id;
            private String firstName;
            private String lastName;
            private String email;
            private String role;
        }
    }
}
