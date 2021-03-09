package ai.leantech.delivery.controller;

import ai.leantech.delivery.config.jwt.JwtProvider;
import ai.leantech.delivery.controller.model.AuthRequest;
import ai.leantech.delivery.controller.model.AuthResponse;
import ai.leantech.delivery.controller.model.RegistrationRequest;
import ai.leantech.delivery.model.RoleType;
import ai.leantech.delivery.model.User;
import ai.leantech.delivery.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    public AuthController(final UserService userService, final JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        User u = new User();
        u.setPassword(registrationRequest.getPassword());
        u.setLogin(registrationRequest.getLogin());
        userService.addNewUser(u, RoleType.CUSTOMER);
        return "OK";
    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) {
        User user = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(user.getLogin());
        return new AuthResponse(token);
    }
}
