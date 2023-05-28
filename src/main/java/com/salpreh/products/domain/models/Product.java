package com.salpreh.products.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
  private Long id;
  private String name;
  private String description;
  private double price;
  private int stock;
}
