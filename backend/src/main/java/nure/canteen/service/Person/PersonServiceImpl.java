package nure.canteen.service.Person;

import lombok.extern.slf4j.Slf4j;
import nure.canteen.domain.Person;
import nure.canteen.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepo personRepo;

    @Override
    public Person getById(Long id) {
        log.info("IN PersonServiceImpl getById {}", id);
        Optional<Person> personResponse = personRepo.findById(id);
        Person person = personResponse.get();
        return person;
    }

    @Override
    public void save(Person person) {
        log.info("IN PersonServiceImpl save {}", person);
        personRepo.save(person);
    }

    @Override
    public void delete(Long id) {
        log.info("IN PersonServiceImpl delete {}", id);
        personRepo.deleteById(id);

    }

    @Override
    public List<Person> getAll() {
        log.info("IN PersonServiceImpl getAll");
        return personRepo.findAll();
    }
}
