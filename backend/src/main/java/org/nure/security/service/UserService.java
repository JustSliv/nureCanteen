package org.nure.security.service;

import org.nure.security.SecurityUtils;
import org.nure.security.model.Authority;
import org.nure.security.model.User;
import org.nure.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {


   @Autowired
   private PasswordEncoder passwordEncoder;

   @Autowired
   UserRepository userRepo;


   private final UserRepository userRepository;

   public UserService(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   @Transactional(readOnly = true)
   public Optional<User> getUserWithAuthorities() {
      return SecurityUtils.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);
   }

   public void save(User user) {
      userRepo.save(user);
   }

   public User registerUser(User user){

      if(userRepository.getAuthCount() == 0){
         userRepository.setAuth();
      }
      user.setActivated(true);
      user.setAuthorities(Collections.singleton(new Authority("ROLE_USER")));
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      this.save(user);
      return user;
   }

   public User updateUser(User user){

      User oldUser = this.getUserWithAuthorities().get();

      User userToChange = userRepository.findByUsername(oldUser.getUsername());

      if(user.getPhone() != null) {
         userToChange.setPhone(user.getPhone());
      }
      if(user.getlName() != null) {
         userToChange.setlName(user.getlName());
      }
      if(user.getfName() != null) {
         userToChange.setfName(user.getfName());
      }
      if(user.getEmail() != null) {
         userToChange.setEmail(user.getEmail());
      }
      if(user.getAge() != null) {
         userToChange.setAge(user.getAge());
      }
      if(user.getPersonGroup() != null) {
         userToChange.setPersonGroup(user.getPersonGroup());
      }
      if(user.getAvatar() != null){
         userToChange.setPersonGroup(user.getAvatar());
      }


      this.save(userToChange);
      return userToChange;
   }

   public User updateUserById(User user,Long userId){
      User oldUser = userRepository.findById(userId).get();

      if(user.getPhone() != null) {
         oldUser.setPhone(user.getPhone());
      }
      if(user.getlName() != null) {
         oldUser.setlName(user.getlName());
      }
      if(user.getfName() != null) {
         oldUser.setfName(user.getfName());
      }
      if(user.getEmail() != null) {
         oldUser.setEmail(user.getEmail());
      }
      if(user.getAge() != null) {
         oldUser.setAge(user.getAge());
      }
      if(user.getPersonGroup() != null) {
         oldUser.setPersonGroup(user.getPersonGroup());
      }
      if(user.getAvatar() != null){
         oldUser.setPersonGroup(user.getAvatar());
      }

      this.save(oldUser);

      return oldUser;
   }

   public User findUserById(Long userId){
      return userRepo.findById(userId)
         .orElseThrow(() -> new ResourceNotFoundException("User with userId " + userId + " not found"));
   }

   public List<User> findAllUsers(){
      return userRepo.findAll();
   }

}
