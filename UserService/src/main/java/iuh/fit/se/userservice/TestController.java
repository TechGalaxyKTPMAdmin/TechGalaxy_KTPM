package iuh.fit.se.userservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "Hello World from User Service!";
    }

    @GetMapping("/auth/test2")
    public String test2() {
        return "Hello World from User Service with Auth!";
    }
}
