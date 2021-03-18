package ai.leantech.delivery.controller.model.user;

import ai.leantech.delivery.model.User;

public class UserDtoConverter {

    public UserResponse convertUserToUserResp(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .login(user.getLogin())
                .roles(user.getRoles())
                .build();
    }

}
