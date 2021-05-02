package com.leverx.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

public enum AuthenticationDTO {
    ;

    private interface Email {
        @NotBlank String getEmail();
    }

    private interface Password {
        @NotBlank String getPassword();
    }

    public enum Request {
        ;

        @NoArgsConstructor
        @Data
        public static class Login implements Email, Password {
            private String email;
            private String password;
        }
    }
}
