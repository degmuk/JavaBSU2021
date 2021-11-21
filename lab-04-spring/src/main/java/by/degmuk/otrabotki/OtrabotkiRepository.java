package by.degmuk.otrabotki;

import org.springframework.data.repository.CrudRepository;

public interface OtrabotkiRepository
        extends CrudRepository<Otrabotka, Integer> {
    Iterable<Otrabotka> getAllByOrderByStartTimeDesc();
    Otrabotka getById(Integer id);
}
