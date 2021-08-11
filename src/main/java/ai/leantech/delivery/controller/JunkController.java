package ai.leantech.delivery.controller;

import ai.leantech.delivery.network.Junk;
import ai.leantech.delivery.network.JunkClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/junk")
public class JunkController {

    private final JunkClient junkClient;

    public JunkController(JunkClient junkClient) {
        this.junkClient = junkClient;
    }

    @GetMapping("/{id}")
    public Junk getJunkById(@PathVariable Long id) {
        return junkClient.getSingleJunk(id);
    }
}
