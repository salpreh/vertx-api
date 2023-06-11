package com.salpreh.products.persistence.mappers;

import com.salpreh.products.domain.models.Product;
import com.salpreh.products.persistence.entities.ProductEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "jakarta")
public interface DbMapper {
  Product map(ProductEntity src);
  ProductEntity map(Product src);

  List<Product> mapProducts(List<ProductEntity> src);

  @Mapping(target = "id", ignore = true)
  ProductEntity map(Product src, @MappingTarget ProductEntity target);
}
