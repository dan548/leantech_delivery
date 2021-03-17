package ai.leantech.delivery.controller;

import ai.leantech.delivery.controller.model.AuthRequest;
import ai.leantech.delivery.controller.model.AuthResponse;
import ai.leantech.delivery.controller.model.RegistrationRequest;
import ai.leantech.delivery.model.RoleType;
import ai.leantech.delivery.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        userService.addNewUser(registrationRequest, RoleType.CUSTOMER);
        return "OK";
    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) {
        return userService.authByLoginAndPassword(request.getLogin(), request.getPassword());
    }
}
