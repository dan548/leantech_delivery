package ai.leantech.delivery.objectmother;

import ai.leantech.delivery.entity.User;

import java.util.Set;

public class UserMother {

    public static User.UserBuilder userNoRoleBuilder() {
        return User.builder()
                .login("user")
                .password("$2y$10$2/FasRnmrCVrK9GYCS6F0efrMmt71WvBcNF69nXwzfN9rR38eurX2");
    }

    public static User completeClient() {
        User user = userNoRoleBuilder().build();
        user.setRoles(Set.of(RoleMother.client().build()));
        return user;
    }

}
