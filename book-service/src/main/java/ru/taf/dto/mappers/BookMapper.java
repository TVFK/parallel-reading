package ru.taf.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.taf.dto.BookDTO;
import ru.taf.dto.NewBookDTO;
import ru.taf.entities.Book;
import ru.taf.entities.Genre;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = GenreMapper.class)
public interface BookMapper {
    BookDTO toDto(Book book);

    @Mapping(target = "genres", expression = "java(mapGenres(bookDTO.genres()))")
    Book toEntity(NewBookDTO bookDTO);

    default Set<Genre> mapGenres(Set<String> genreNames) {
        if (genreNames == null) {
            return new HashSet<>();
        }
        return genreNames.stream()
                .map(name -> {
                    Genre genre = new Genre();
                    genre.setName(name);
                    return genre;
                })
                .collect(Collectors.toSet());
    }
}
