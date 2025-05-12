package com.journalingN.journaling.controller;

import com.journalingN.journaling.cache.AppCache;
import com.journalingN.journaling.entity.User;
import com.journalingN.journaling.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/Admin")
public class AdminController {


    @Autowired
    private AppCache appCache;

    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUser(){
        List<User> all = userService.getAll();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/Create-Admin")
    public ResponseEntity<User> createNewAdmin(@RequestBody User newEntry){
        try {
            userService.saveNewAdmin(newEntry);
            return new ResponseEntity<>(newEntry,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("clear-app-cache")
    public void clearAppCache(){
        appCache.init();
    }

}
