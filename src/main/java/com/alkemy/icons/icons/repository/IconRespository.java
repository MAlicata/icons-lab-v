package com.alkemy.icons.icons.repository;

import com.alkemy.icons.icons.entity.IconEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface IconRespository extends JpaRepository<IconEntity, Long>, JpaSpecificationExecutor<IconEntity>{
    List<IconEntity> findAll(Specification<IconEntity> spec);
}
