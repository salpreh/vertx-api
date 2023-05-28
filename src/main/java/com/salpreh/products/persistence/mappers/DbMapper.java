package com.salpreh.products.persistence.mappers;

import com.salpreh.products.domain.models.Product;
import com.salpreh.products.persistence.entities.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jakarta")
public interface DbMapper {
  Product map(ProductEntity src);
  ProductEntity map(Product src);
}
