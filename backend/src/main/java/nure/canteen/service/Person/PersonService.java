package nure.canteen.service.Person;

import nure.canteen.domain.Person;

import java.util.List;

public interface PersonService {

    Person getById(Long id);

    void save(Person person);

    void delete(Long id);

    List<Person> getAll();
}
