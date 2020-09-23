package nure.canteen.controller;

import nure.canteen.domain.Person;
import nure.canteen.service.Person.PersonService;
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
@RequestMapping("/api/person/")
public class PersonController {

    @Autowired
    public PersonService personService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Person> getPerson(@PathVariable("id") Long personId){
        HttpHeaders headers = new HttpHeaders();
        if(personId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Person person = this.personService.getById(personId);


        return new ResponseEntity<>(person,headers, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Person> savePerson(@RequestBody @Valid Person person){
        HttpHeaders headers = new HttpHeaders();

        if(person == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.personService.save(person);

        return new ResponseEntity<>(person, headers, HttpStatus.CREATED);
    }
    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Person> updatePerson(@RequestBody @Valid Person person, UriComponentsBuilder builder){
        HttpHeaders headers = new HttpHeaders();

        if(person == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.personService.save(person);

        return new ResponseEntity<>(person, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "id", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Person> deletePerson(Long id){
        Person person = this.personService.getById(id);

        if(person == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.personService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Person>> getAllPersons(){
        List<Person> persons = this.personService.getAll();

        if(persons.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(persons, HttpStatus.OK);
    }
}