package org.dominicDev.repository;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.dominicDev.model.Film;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class FilmRepository {

    @Inject
    JPAStreamer jpaStreamer;

    private static final int PAGE_SIZE = 20;

    public Optional<Film> getFilmById(short filmId) {
        return jpaStreamer.stream(Film.class).filter(film -> film.getFilmId() == filmId).findFirst();
    }

    public Stream<Film> getFilms(short minLength, int pageNumber) {
        return jpaStreamer.stream(Film.class)
            .filter(film -> film.getLength() > minLength)
            .sorted(Comparator.comparing(Film::getLength))
            .skip(pageNumber * PAGE_SIZE)
            .limit(PAGE_SIZE);
    }

}
