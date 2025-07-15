package ru.taf.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.taf.dto.DictionaryCardDTO;
import ru.taf.dto.NewDictionaryCardDTO;
import ru.taf.dto.UpdateDictionaryCardDTO;
import ru.taf.entities.DictionaryCard;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DictionaryCardMapper {
    DictionaryCardDTO toDto(DictionaryCard dictionaryCard);

    DictionaryCard toEntity(NewDictionaryCardDTO newDictionaryCardDTO);

    @Mapping(target = "id", ignore = true)
    DictionaryCard toEntity(UpdateDictionaryCardDTO updateDictionaryCardDTO);
}
