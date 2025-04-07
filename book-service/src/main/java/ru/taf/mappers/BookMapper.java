package ru.taf.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.taf.dto.BookDTO;
import ru.taf.entities.Book;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = GenreMapper.class)
public interface BookMapper {
    BookDTO toDto(Book book);
}
