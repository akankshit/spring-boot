package com.spring.restfulwebservices.user;

import com.spring.restfulwebservices.exceptions.UserNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserResource {
    @Autowired
    UserDaoService userDaoService = new UserDaoService();

    @GetMapping("/users")
    public List<User> retriveAllUsers(){
        return userDaoService.findAll();
    }

    @GetMapping("/users/{id}")
    public User retriveUser(@PathVariable int id)
    {
        User user = userDaoService.findOne(id);
        if(user == null){
            throw new UserNotFound("id: "+ id);
        }
        return user;
    }

    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody User user){
        User savedUser = userDaoService.saveUser(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

}
