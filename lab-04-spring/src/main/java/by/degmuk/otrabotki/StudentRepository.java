package by.degmuk.otrabotki;

import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Integer> {
    Iterable<Student> getAllByOrderByName();

    Iterable<Student> getAllByOrderByTotalHoursAsc();

    Student getByNameAndRoom(String name, Integer room);
    Student getByStudak(Integer studak);
}
