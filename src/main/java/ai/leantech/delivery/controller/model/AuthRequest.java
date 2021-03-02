package ai.leantech.delivery.controller.model;

import lombok.Data;

@Data
public class AuthRequest {
    private String login;
    private String password;
}
