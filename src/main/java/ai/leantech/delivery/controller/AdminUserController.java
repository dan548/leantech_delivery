package ai.leantech.delivery.controller;

import ai.leantech.delivery.controller.model.user.AdminRegistrationRequest;
import ai.leantech.delivery.controller.model.user.UserResponse;
import ai.leantech.delivery.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping()
    public String registerUser(@RequestBody @Valid AdminRegistrationRequest request) {
        userService.addNewUser(request);
        return "OK";
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public UserResponse editUser(@PathVariable Long id, @RequestBody JsonPatch patch)
            throws JsonPatchException, JsonProcessingException {
        return userService.editUser(id, patch);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}
