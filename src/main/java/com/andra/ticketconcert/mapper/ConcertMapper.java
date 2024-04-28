package com.andra.ticketconcert.mapper;

import com.andra.ticketconcert.dto.ResConcertDto;
import com.andra.ticketconcert.mapper.decorator.ConcertMapperDecorator;
import com.andra.ticketconcert.model.Concert;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
@DecoratedWith(ConcertMapperDecorator.class)
public interface ConcertMapper {

    ConcertMapper INSTANCE = Mappers.getMapper(ConcertMapper.class);

    @Named("toResConcertDto")
    ResConcertDto toResConcertDto(Concert concert);

    default Page<ResConcertDto> toResConcertDtoPage(Page<Concert> page) {
        return page.map(this::toResConcertDto);
    }

    @IterableMapping(qualifiedByName = "toResConcertDto")
    List<ResConcertDto> toListResConcertDto(List<Concert> concerts);
}
