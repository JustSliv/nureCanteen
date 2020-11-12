package org.canteen.security.service;

import org.canteen.security.SecurityUtils;
import org.canteen.security.model.User;
import org.canteen.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {

   @Autowired
   private BCryptPasswordEncoder bCryptPasswordEncoder;
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
//      log.info("IN UserServiceImpl save {}", user);
      userRepo.save(user);
   }

}
