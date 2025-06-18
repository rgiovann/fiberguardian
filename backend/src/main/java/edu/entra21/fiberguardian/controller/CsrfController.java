package edu.entra21.fiberguardian.controller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fiberguardian")
public class CsrfController {

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(CsrfToken token) {
        return token;
    }
}
