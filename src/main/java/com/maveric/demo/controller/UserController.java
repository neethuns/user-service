package com.maveric.demo.controller;

import com.maveric.demo.dto.UserDto;
import com.maveric.demo.model.User;
import com.maveric.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.QueryParam;
import java.util.List;


@CrossOrigin(value = "*")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(@QueryParam("page") Integer page, @QueryParam("pageSize") Integer pageSize)
    {

            return new ResponseEntity<>(userService.getAllUsers(page, pageSize), HttpStatus.OK);


    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody User user)
    {

    return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }



    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable("userId") String userId) {

        return new ResponseEntity<>(userService.getUserDetails(userId), HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody User user, @PathVariable("userId") String userId )
    {
        return new ResponseEntity<> (userService.updateUser(user, userId), HttpStatus.OK);

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") String userId) {
        return new ResponseEntity<> (userService.deleteUser(userId), HttpStatus.OK);
    }


    @GetMapping("/getUserByEmail/{emailId}")
    public UserDto getUserDetailsByEmail(@PathVariable("emailId") String emailId){
        return userService.userByEmail(emailId);
    }


}
