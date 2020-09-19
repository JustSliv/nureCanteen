package nure.canteen.controller;

import nure.canteen.domain.Person;
import nure.canteen.repo.PersonRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("person")
public class PersonController {
    private final PersonRepo personRepo;

    @Autowired
    public PersonController(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @GetMapping
    public List<Person> list(){
        return personRepo.findAll();
    }

    @GetMapping("{id}")
    public Person getOne(@PathVariable("id") Person person){
        return person;
    }

    @PostMapping
    public Person create(@RequestBody Person person){
        person.setCreationTime(LocalDateTime.now());
        return personRepo.save(person);
    }
    @PutMapping({"{id}"})
    public Person update(@PathVariable("id") Person personFromDb,
                         @RequestBody Person person){
        BeanUtils.copyProperties(person, personFromDb, "id");

        return personRepo.save(personFromDb);
    }

    @DeleteMapping("{id}")
    public void delete (@PathVariable("id") Person person){
        personRepo.delete(person);

    }
}
//Ну и дрысня