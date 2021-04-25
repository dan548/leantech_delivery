package ai.leantech.delivery.controller.model.user;

import ai.leantech.delivery.entity.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class UserResponse {

    private Long id;
    private String login;
    private Set<Role> roles;

}
