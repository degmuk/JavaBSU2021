package by.degmuk.otrabotki;

import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Integer> {
    Iterable<Student> getAllByOrderByName();

    Iterable<Student> getAllByNameContainsOrderByName(String name);
    Iterable<Student> getAllByNameContainsOrderByTotalHoursAsc(String name);
    Iterable<Student> getAllByNameContainsOrderByTotalHoursDesc(String name);

    Student getByNameAndRoom(String name, Integer room);
    Student getByStudak(Integer studak);
}
