package justsliv.Canteen.service;

import justsliv.Canteen.Models.User;
import justsliv.Canteen.Repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Override
    public User getById(Long id){
        log.info("IN UserServiceImpl getById {}", id);
        Optional<User> userResponse= userRepo.findById(id);
        User user = userResponse.get();
        return user;
    }

    @Override
    public void save(User user) {
        log.info("IN UserServiceImpl save {}", user);
        userRepo.save(user);
    }

    @Override
    public void delete(Long id) {
        log.info("IN UserServiceImpl delete {}", id);
        userRepo.deleteById(id);
    }

    @Override
    public List<User> getAll() {
        log.info("IN UserServiceImpl getAll");
        return userRepo.findAll();
    }
}
