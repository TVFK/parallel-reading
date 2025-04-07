package ru.taf.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.taf.dto.GenreDTO;
import ru.taf.entities.Genre;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenreMapper {
    GenreDTO toDto(Genre genre);
}
