package makara.co.min_pos.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import makara.co.min_pos.base.BaseApi;
import makara.co.min_pos.config.jwt.LoginRequest;
import makara.co.min_pos.config.security.AuthUser;
import makara.co.min_pos.config.security.UserService;
import makara.co.min_pos.models.response.LoginResponse;
import makara.co.min_pos.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;


    @PostMapping("/login")
    @ApiResponse(description = "User can log in with email and password")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        AuthUser user = userService.findUserByUsername(authRequest.getUsername()).orElseThrow();
        String accessToken = jwtUtil.generateToken(user.getUsername(), 1000 * 60 * 10); // 10 min
        String refreshToken = jwtUtil.generateToken(user.getUsername(), 1000 * 60 * 60 * 24 * 7); // 7 days

        // userService.findUserByUsername(user);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        return ResponseEntity.ok(tokens);
    }

//    @Operation(summary = "User login", description = "Authenticates user and returns JWT tokens")
//    @PostMapping("/login")
//    public BaseApi<?> login(@Valid @RequestBody LoginRequest authRequest) {
//        // Authenticate user
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
//
//        // Get user details
//        AuthUser user = userService.findUserByUsername(authRequest.getUsername())
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        // Generate tokens
//        String accessToken = jwtUtil.generateToken(user.getUsername(), 1000 * 60 * 10); // 10 min
//        String refreshToken = jwtUtil.generateToken(user.getUsername(), 1000 * 60 * 60 * 24 * 7); // 7 days
//
//        // Build response
//        LoginResponse response = LoginResponse.builder()
//                .accessToken(accessToken)
//                .refreshToken(refreshToken)
//                .username(user.getUsername())
//                .build();
//
//        return BaseApi.builder()
//                .status(true)
//                .code(HttpStatus.OK.value())
//                .message("Login successful")
//                .timeStamp(LocalDateTime.now())
//                .data(response)
//                .build();
//    }
//

}
