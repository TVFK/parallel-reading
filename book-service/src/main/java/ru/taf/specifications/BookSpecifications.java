package ru.taf.specifications;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.taf.entities.Book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookSpecifications {

    public static Specification<Book> withFilters(String title, String genres, String level) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (title != null && !title.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
            }

            if (genres != null && !genres.isEmpty()) {
                String[] genresArray = genres.split(",");
                if (genresArray.length > 0) {
                    Predicate[] genrePredicates = Arrays.stream(genresArray)
                            .map(genre -> cb.equal(root.join("genres").get("name"), genre.trim()))
                            .toArray(Predicate[]::new);
                    predicates.add(cb.and(genrePredicates));
                }
            }

            if (level != null && !level.isEmpty()) {
                predicates.add(cb.equal(root.get("level"), level));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}