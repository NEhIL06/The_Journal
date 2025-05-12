package com.journalingN.journaling.controller;

import com.journalingN.journaling.entity.User;
import com.journalingN.journaling.services.UserDetailsServiceImpl;
import com.journalingN.journaling.services.UserService;
import com.journalingN.journaling.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/health-Check")
    public String HealthCheck() {
        return "ok";
    }

    @PostMapping("/SignUp")
    public ResponseEntity<User> SignUp (@RequestBody User newEntry){
        try{
            userService.saveNewUser(newEntry);
            return new ResponseEntity<>(newEntry, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/Login")
    public ResponseEntity<String> Login (@RequestBody User user){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("The User Auth Details are not Correct",HttpStatus.BAD_REQUEST);
        }
    }


}
