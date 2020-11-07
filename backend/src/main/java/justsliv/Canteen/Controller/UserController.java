package justsliv.Canteen.Controller;

import justsliv.Canteen.Models.User;
import justsliv.Canteen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/user/")
public class UserController {

    @Autowired
    public UserService userService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> getUser(@PathVariable("id") Long userId){
        HttpHeaders headers = new HttpHeaders();
        if(userId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = this.userService.getById(userId);


        return new ResponseEntity<>(user,headers, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> saveUser(@RequestBody @Valid User user){
        HttpHeaders headers = new HttpHeaders();

        if(user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.userService.save(user);

        return new ResponseEntity<>(user, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> updateUser(@RequestBody @Valid User user, UriComponentsBuilder builder){
        HttpHeaders headers = new HttpHeaders();

        if(user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.userService.save(user);

        return new ResponseEntity<>(user, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "id", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> deleteUser(Long id){
        User user = this.userService.getById(id);

        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.userService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = this.userService.getAll();

        if(users.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
