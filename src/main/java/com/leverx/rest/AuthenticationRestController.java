package com.leverx.rest;

import com.leverx.dto.AuthenticationDTO;
import com.leverx.dto.UserDTO;
import com.leverx.security.JwtTokenProvider;
import com.leverx.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor(onConstructor_ = {@Autowired})

@RestController
@RequestMapping("/api/auth")
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;


    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationDTO.Request.Login authenticationDtoRequest) throws UsernameNotFoundException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationDtoRequest.getEmail(), authenticationDtoRequest.getPassword()));
            UserDTO.Response.Public userDtoResponse = userService.findByEmail(authenticationDtoRequest.getEmail()).get();
            String token = jwtTokenProvider.createToken(authenticationDtoRequest.getEmail(), userDtoResponse.getRole());

            Map<Object, Object> response = new HashMap<>();
            response.put("email", authenticationDtoRequest.getEmail());
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
