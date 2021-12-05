package com.alkemy.icons.icons.repository.specifications;

import antlr.StringUtils;
import com.alkemy.icons.icons.dto.IconFiltersDTO;
import com.alkemy.icons.icons.entity.IconEntity;
import com.alkemy.icons.icons.entity.PaisEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

public class IconSpecification {
    public Specification<IconEntity> getByFilters(IconFiltersDTO filtersDTO){
        return (root, query, criteriaBuilder) ->{
            List<Predicate> predicates = new ArrayList<>();

            if(StringUtils.hasLength(filtersDTO.getName())){
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("denominacion")),
                                pattern: "%" + filtersDTO.getName().toLowerCase() + "%";
                        )
                );
            }
            if(StringUtils.hasLength(filtersDTO.getDate())){
                //TODO: Reuse this in a fuction
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(filtersDTO.getDate(), formatter);

                predicates.add(
                        criteriaBuilder.equal(root.<LocalDate>get("fechaCreacion"),date)
                );
            }
            if (!CollectionUtils.isEmpty((filtersDTO.getCities()))) {

                Join<PaisEntity, IconEntity> join = root.join( "paises", JoinType.INNER);
                Expression<String> citiesId = join.get("id");
                predicates.add(citiesId.in(filtersDTO.getCities()));
            }
            //Remove duplicates
            query.distinct(true);

            //Order resolver
            String orderByField = "denominacion";
            query.orderBy(
                    filtersDTO.isASC() ?
                            criteriaBuilder.asc(root.get(orderByField)) :
                            criteriaBuilder.desc(root.get(orderByField))
            );
            return criteriaBuilder.add(predicates.toArray(new Predicate[0]));
        };
    }
}
