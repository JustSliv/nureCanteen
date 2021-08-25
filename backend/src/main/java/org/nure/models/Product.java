package org.nure.models;

import com.fasterxml.jackson.annotation.*;
import org.nure.config.EntityIdResolver;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "product")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "comments"})
@JsonIdentityInfo(
   generator = ObjectIdGenerators.PropertyGenerator.class,
   property = "product",
   resolver = EntityIdResolver.class,
   scope=Product.class)
public class Product {

   @Id
   @Column(name = "product_id")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @SequenceGenerator(name = "PRODUCT_SEQ", sequenceName = "PRODUCT_SEQ", allocationSize = 1)
   private Long product;


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
   private int totalСount;

   @Column(name = "available_count", length = 50)
   @NotNull
   private int availableCount;

   @Column(name = "image", length = 10000)
   @NotNull
   private String image;

   @Column(name = "calories")
   private int calories;

   @Column(name = "weight")
   private int weight;


   @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   private Set<Comment> comments = new HashSet<Comment>();

   @JsonIgnore
   @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   private Set<Basket> baskets = new HashSet<Basket>();

   public Product() {
   }


   public Long getProduct_id() {
      return product;
   }

   public void setProduct_id(Long product) {
      this.product = product;
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
      return totalСount;
   }

   public void setTotal_count(int totalСount) {
      this.totalСount = totalСount;
   }

   public int getAvailable_count() {
      return availableCount;
   }

   public void setAvailable_count(int availableCount) {
      this.availableCount = availableCount;
   }

   public String getImage() {
      return image;
   }

   public void setImage(String image) {
      this.image = image;
   }

   public Set<Comment> getComments() {
      return comments;
   }

   public void setComments(Set<Comment> comments) {
      this.comments = comments;
   }

   public Set<Basket> getBaskets() {
      return baskets;
   }

   public void setBaskets(Set<Basket> baskets) {
      this.baskets = baskets;
   }

   public int getCalories() {
      return calories;
   }

   public void setCalories(int calories) {
      this.calories = calories;
   }

   public int getWeight() {
      return weight;
   }

   public void setWeight(int weight) {
      this.weight = weight;
   }

   @Override
   public String toString() {
      return "Product{" +
         "product=" + product +
         ", name='" + name + '\'' +
         ", price=" + price +
         ", category='" + category + '\'' +
         ", description='" + description + '\'' +
         ", totalСount=" + totalСount +
         ", availableCount=" + availableCount +
         ", image='" + image + '\'' +
         ", calories=" + calories +
         ", weight=" + weight +
         ", comments=" + comments +
         ", baskets=" + baskets +
         '}';
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Product product = (Product) o;
      return Objects.equals(product, product.product);
   }

   @Override
   public int hashCode() {
      return Objects.hash(product);
   }
}
