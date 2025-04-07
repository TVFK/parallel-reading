package ru.taf.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.taf.dto.PageDTO;
import ru.taf.entities.Page;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PageMapper {
    @Mapping(target = "chapterId", source = "chapter.id")
    PageDTO toDto(Page page);
}
