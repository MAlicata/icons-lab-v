package com.alkemy.icons.icons.service.impl;

import com.alkemy.icons.icons.dto.IconBasicDTO;
import com.alkemy.icons.icons.dto.IconDTO;
import com.alkemy.icons.icons.dto.IconFiltersDTO;
import com.alkemy.icons.icons.entity.IconEntity;
import com.alkemy.icons.icons.exception.ParamNotFound;
import com.alkemy.icons.icons.mapper.IconMapper;
import com.alkemy.icons.icons.service.IconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class IconServiceImpl implements IconService {
    @Autowired
    private IconRepository iconRepository;

    @Autowired
    private IconSpecification iconSpecification;

    @Autowired
    private IconMapper iconMapper;




    @Override
    public List<IconBasicDTO> getAll() {
        return null;
    }

    public void delete(Long id){
        this.iconRepository.deleteById(id);
    }

    public IconDTO getByFiltersById(Long id){
        Optional<IconEntity> entity = this.iconRepository.findById(id);
        if(!entity.isPresent()){
            throw new ParamNotFound("Id icono no valido");
        }
        IconDTO iconDTO = this.iconMapper.iconEntity2DTO(entity.get(), true);
        return iconDTO;
    }

    public List<IconBasicDTO> getAll(){
        List<IconEntity> entities = this.iconRepository.findAll();
        List<IconBasicDTO> iconBasicDTOS = this.iconMapper.iconEntitySet2BasicDTOList(entities);
        return iconBasicDTOS;
    }

    public List<IconDTO> getByFilters(String name, String date, Set<Long> cities, String order){
        IconFilterDTO filtersDTO = new IconFiltersDTO(name, date, cities, order);
        List<IconEntity> entities = this.iconRepository.findAll(this.iconSpecification.getByFilters(filtersDTO));
        List<IconDTO> dtos = this.iconMapper.iconEntitySet2BasicDTOList(entites, true);
        return dtos;
    }

    public IconDTO save(IconDTO iconDTO){
        IconEntity entity = this.iconMapper.iconDTO2Entity(iconDTO);
        IconEntity entitySaved = this.iconRepository.save(entity);
        IconDTO result = this.iconMapper.iconEntity2DTO(entitySaved, false);
        return result;
    }

}
