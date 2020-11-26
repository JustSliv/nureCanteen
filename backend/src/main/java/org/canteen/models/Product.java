package org.canteen.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Product")
public class Product {

   @Id
   @Column(name = "product_id")
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;


   @Column(name = "name", length = 50, unique = true)
   @NotNull
   private String name;

   @Column(name = "price", length = 50)
   @NotNull
   private int price;

   @Column(name = "category", length = 50)
   @NotNull
   private String category;

   @Column(name = "description", length = 50)
   @NotNull
   private String description;

   @Column(name = "total_count", length = 50)
   @NotNull
   private int total_count;

   @Column(name = "available_count", length = 50)
   @NotNull
   private int available_count;

   @Column(name = "image", length = 50)
   @NotNull
   private String image;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public int getPrice() {
      return price;
   }

   public void setPrice(int price) {
      this.price = price;
   }

   public String getCategory() {
      return category;
   }

   public void setCategory(String category) {
      this.category = category;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public int getTotal_count() {
      return total_count;
   }

   public void setTotal_count(int total_count) {
      this.total_count = total_count;
   }

   public int getAvailable_count() {
      return available_count;
   }

   public void setAvailable_count(int available_count) {
      this.available_count = available_count;
   }

   public String getImage() {
      return image;
   }

   public void setImage(String image) {
      this.image = image;
   }
}
