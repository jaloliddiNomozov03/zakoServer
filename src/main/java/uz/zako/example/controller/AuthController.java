package uz.zako.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uz.zako.example.entity.User;
import uz.zako.example.model.JwtResponse;
import uz.zako.example.payload.LoginReq;
import uz.zako.example.payload.UserReq;
import uz.zako.example.service.UserService;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> signin(@RequestBody LoginReq loginReq){
        return userService.signin(loginReq);
    }

    @GetMapping("/me")
    public UserReq getMet(Authentication authentication){
        return userService.getMe((User) authentication.getPrincipal());
    }

}
