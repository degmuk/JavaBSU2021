package by.degmuk.otrabotki;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OtrabotkiRepository
        extends CrudRepository<Otrabotka, Integer> {
    Iterable<Otrabotka> findAllByOrderByStartTimeDesc();
    Optional<Otrabotka> findById(Integer id);
}
