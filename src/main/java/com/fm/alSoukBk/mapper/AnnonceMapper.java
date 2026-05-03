package com.fm.alSoukBk.mapper;

import com.fm.alSoukBk.dto.AnnonceRequestDTO;
import com.fm.alSoukBk.dto.AnnonceResponseDTO;
import com.fm.alSoukBk.dto.PageResponseDTO;
import com.fm.alSoukBk.dto.UserSummaryDTO;
import com.fm.alSoukBk.model.Annonce;
import com.fm.alSoukBk.model.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface AnnonceMapper {

    AnnonceMapper INSTANCE = Mappers.getMapper(AnnonceMapper.class);

    Annonce toEntity(AnnonceRequestDTO annonceRequestDTO);
    AnnonceResponseDTO toDTO(Annonce annonce);

}