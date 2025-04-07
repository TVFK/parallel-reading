package ru.taf.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.taf.dto.ChapterDTO;
import ru.taf.entities.Book;
import ru.taf.entities.Chapter;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChapterMapper {
    @Mapping(target = "bookId", source = "book.id")
    ChapterDTO toDto(Chapter chapter);
}
