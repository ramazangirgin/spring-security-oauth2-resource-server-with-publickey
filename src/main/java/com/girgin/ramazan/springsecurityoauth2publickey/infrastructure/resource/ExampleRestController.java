package com.girgin.ramazan.springsecurityoauth2publickey.infrastructure.resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class ExampleRestController {

    @PreAuthorize("hasRole('ROLE_A')")
    @GetMapping(value = "/hello-role-a")
    public String helloRoleA(Principal principal) {
        return String.format("Hello-Role-A, Your User Name: %s", principal.getName());
    }

    @PreAuthorize("hasRole('ROLE_B')")
    @GetMapping(value = "/hello-role-b")
    public String helloRoleB(Principal principal) {
        return String.format("Hello-Role-B, Your User Name: %s", principal.getName());
    }

    @PreAuthorize("hasRole('ROLE_C')")
    @GetMapping(value = "/hello-role-c")
    public String helloRoleC(Principal principal) {
        return String.format("Hello-Role-C, Your User Name: %s", principal.getName());
    }

    @GetMapping(value = "/non-secure-hello")
    public String helloNonSecure() {
        return "Hello-Non-Secure";
    }
}
