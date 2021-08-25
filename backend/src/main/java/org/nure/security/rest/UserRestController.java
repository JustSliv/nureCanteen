package org.nure.security.rest;

import org.nure.security.model.User;
import org.nure.security.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {


   private final UserService userService;


   public UserRestController(UserService userService) {
      this.userService = userService;
   }

   @GetMapping("/user/{user_id}")
   public ResponseEntity<User> getuserbyId(@PathVariable("user_id") Long userId){
      User user = userService.findUserById(userId);
      return ResponseEntity.ok(user);
   }

   @GetMapping("/user/all")
   public ResponseEntity<List<User>> getUsers() {
      List<User> allUsers = userService.findAllUsers();
      return ResponseEntity.ok(allUsers);
   }

   @GetMapping("/user")
   public ResponseEntity<User> getActualUser() {
      return ResponseEntity.ok(userService.getUserWithAuthorities().get());
   }

   @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   public ResponseEntity<User> saveUser(@RequestBody @Valid User user){

      if(user == null){
         System.out.println(user.getPassword());
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
      userService.registerUser(user);

      return ResponseEntity.ok(user);
   }

   @RequestMapping(value = "/user", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   public ResponseEntity<User> updateUser(@RequestBody User user){

      if(user == null){
         System.out.println(user.getlName());
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
      userService.updateUser(user);

      return ResponseEntity.ok(user);
   }

   @RequestMapping(value = "/user/{user_id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   public ResponseEntity<User> updateUserById(@RequestBody User user, @PathVariable("user_id") Long userId){

      if(user == null){
         System.out.println(user.getlName());
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
      userService.updateUserById(user, userId);

      return ResponseEntity.ok(user);
   }

}
