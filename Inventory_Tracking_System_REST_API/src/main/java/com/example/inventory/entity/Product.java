package com.example.inventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "products")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
  @SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1)
  private Long id;

  @NotBlank(message = "Product name is required")
  @Column(nullable = false)
  private String name;

  @NotBlank(message = "Description is required")
  @Column(nullable = false, length = 1000)
  private String description;

  @Positive(message = "Quantity must be greater than 0")
  @Column(nullable = false)
  private Integer quantity;

  @Positive(message = "Price must be greater than 0")
  @Column(nullable = false)
  private Double price;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "supplier_id", nullable = false)
  @JsonIgnoreProperties({"products"})
  private Supplier supplier;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
  public Integer getQuantity() { return quantity; }
  public void setQuantity(Integer quantity) { this.quantity = quantity; }
  public Double getPrice() { return price; }
  public void setPrice(Double price) { this.price = price; }
  public Supplier getSupplier() { return supplier; }
  public void setSupplier(Supplier supplier) { this.supplier = supplier; }
}
