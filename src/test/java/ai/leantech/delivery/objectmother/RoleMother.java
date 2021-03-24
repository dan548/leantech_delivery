package ai.leantech.delivery.objectmother;

import ai.leantech.delivery.entity.Role;

public class RoleMother {

    public static Role.RoleBuilder admin() {
        return Role.builder()
                .name("ROLE_ADMIN");
    }

    public static Role.RoleBuilder client() {
        return Role.builder()
                .name("ROLE_CLIENT");
    }
}
