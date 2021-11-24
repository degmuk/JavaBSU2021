package by.degmuk.otrabotki;

import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Integer> {
    Iterable<Student> findAllByOrderByName();

    Iterable<Student> findAllByNameContainsOrderByName(String name);
    Iterable<Student> findAllByNameContainsOrderByTotalHoursAsc(String name);
    Iterable<Student> findAllByNameContainsOrderByTotalHoursDesc(String name);

    Student findByNameAndRoom(String name, Integer room);
    Student findByStudak(Integer studak);
}
