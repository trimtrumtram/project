package com.crudapi.crud.mapper.entityMapper;

import com.crudapi.crud.dto.product.CreateProductDTO;
import com.crudapi.crud.dto.product.ProductResponseDTO;
import com.crudapi.crud.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    Product mapToEntity(CreateProductDTO dto);

    @Mapping(target = "id", source = "id")
    ProductResponseDTO mapToDTO(Product product);
}
