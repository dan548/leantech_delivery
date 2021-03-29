package ai.leantech.delivery.controller;

import ai.leantech.delivery.controller.model.user.UserResponse;
import ai.leantech.delivery.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/whoami")
public class WhoAmIController {

    private final UserService service;

    public WhoAmIController(final UserService service) {
        this.service = service;
    }

    // doesn't need
    @GetMapping
    @ResponseBody
    public UserResponse get() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return service.getUserResponseByAuthentication(authentication);
    }
}
