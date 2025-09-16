package com.example.inventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "suppliers")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","products"})
public class Supplier {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supplier_seq")
  @SequenceGenerator(name = "supplier_seq", sequenceName = "supplier_seq", allocationSize = 1)
  private Long id;

  @NotBlank(message = "Supplier name is required")
  @Column(nullable = false)
  private String name;

  @Email(message = "Invalid email format")
  @NotBlank(message = "Email is required")
  @Column(nullable = false, unique = true)
  private String email;

  @NotBlank(message = "Phone number is required")
  @Column(nullable = false)
  private String phoneNumber;

  @NotBlank(message = "Address is required")
  @Column(nullable = false, length = 500)
  private String address;

  @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Product> products = new ArrayList<>();

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public String getPhoneNumber() { return phoneNumber; }
  public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
  public String getAddress() { return address; }
  public void setAddress(String address) { this.address = address; }
  public List<Product> getProducts() { return products; }
  public void setProducts(List<Product> products) { this.products = products; }
}
