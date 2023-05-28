package com.salpreh.products.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
  private Long id;
  private String name;
  private String description;
  private double price;
  private int stock;
}
