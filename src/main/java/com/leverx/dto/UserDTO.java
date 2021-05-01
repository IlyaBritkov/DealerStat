package com.leverx.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

// todo validation
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
        public static class Update implements Id, FirstName, LastName, Email, Password {
            private Integer id;
            private String firstName;
            private String lastName;
            private String email;
            private String password;
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
