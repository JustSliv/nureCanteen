package org.canteen.security.rest;

import org.canteen.security.model.Authority;
import org.canteen.security.model.User;
import org.canteen.security.repository.UserRepository;
import org.canteen.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/api")
public class UserRestController {
   @Autowired
   private PasswordEncoder passwordEncoder;
   @Autowired
   private UserRepository userRepository;

   private final UserService userService;


   public UserRestController(UserService userService) {
      this.userService = userService;
   }

   @GetMapping("/user")
   public ResponseEntity<User> getActualUser() {
      return ResponseEntity.ok(userService.getUserWithAuthorities().get());
   }

   @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   public ResponseEntity<User> saveUser(@RequestBody @Valid User user){
      HttpHeaders headers = new HttpHeaders();

      if(user == null){
         System.out.println(user.getPassword());
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
      user.setActivated(true);
      user.setAuthorities(Collections.singleton(new Authority("ROLE_USER")));
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      this.userService.save(user);

      return new ResponseEntity<>(user, headers, HttpStatus.CREATED);
   }

   @RequestMapping(value = "/user", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   public ResponseEntity<User> updateUser(@RequestBody User user){
      HttpHeaders headers = new HttpHeaders();

      User authUser = userService.getUserWithAuthorities().get();

      User userToChange = userRepository.findByUsername(authUser.getUsername());

      userToChange.setPhone(user.getPhone());
      userToChange.setlName(user.getlName());
      userToChange.setfName(user.getfName());
      userToChange.setEmail(user.getEmail());
      userToChange.setAge(user.getAge());
      userToChange.setPersonGroup(user.getPersonGroup());

      if(user == null){
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }




      this.userService.save(userToChange);


      return new ResponseEntity<>(user, headers, HttpStatus.OK);
   }

}
