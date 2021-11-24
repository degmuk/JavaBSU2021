package by.degmuk.otrabotki.repositories;

import by.degmuk.otrabotki.model.Otrabotka;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OtrabotkiRepository
        extends CrudRepository<Otrabotka, Integer> {
    Iterable<Otrabotka> findAllByOrderByStartTimeDesc();
    Optional<Otrabotka> findById(Integer id);
}
