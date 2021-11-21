package by.degmuk.otrabotki;

import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Integer> {
    Iterable<Student> getAllByOrderByName();

    Iterable<Student> getAllByOrderByTotalHoursAsc();
    Iterable<Student> getAllByOrderByTotalHoursDesc();

    Student getByNameAndRoom(String name, Integer room);
    Iterable<Student> getByName(String name);
    Student getByStudak(Integer studak);
}
