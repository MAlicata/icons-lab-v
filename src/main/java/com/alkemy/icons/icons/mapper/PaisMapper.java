package com.alkemy.icons.icons.mapper;

import com.alkemy.icons.icons.dto.IconDTO;
import com.alkemy.icons.icons.dto.PaisDTO;
import com.alkemy.icons.icons.entity.PaisEntity;

import java.util.ArrayList;
import java.util.List;

public class PaisMapper {


    public PaisDTO paisEntityList2DTO(PaisEntity entity, boolean loadIcons){
        PaisDTO dto = new PaisDTO();
        dto.setId(entity.getId());
        dto.setImagen(entity.getImagen());
        dto.setDenominacion(entity.getDenominacion());
        dto.setCantidadHabitantes(entity.getCantidadHabitantes());
        dto.setContinenteId(entity.getContinenteId());
        dto.setSuperficie(entity.getSuperficie());
        if(loadIcons){
            List<IconDTO> iconDTOS = this.iconMapper.iconEntitySet2DTOList(entity.getIcons(), dto.setIcons(iconDTOS));
        }
    }



    public List<PaisDTO> paisEntityList2DTOList(List<PaisEntity> entites , boolean loadIcons){
        List<PaisDTO> dtos = new ArrayList<>();
        for(PaisEntity entity : entites){
            dtos.add(this.paisEntityList2DTOList(entity, loadIcons));
        }
        return dtos;
    }
}
