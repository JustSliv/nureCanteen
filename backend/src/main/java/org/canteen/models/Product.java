package org.canteen.models;

import com.fasterxml.jackson.annotation.*;
import org.canteen.config.EntityIdResolver;
import org.canteen.security.model.User;

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
   property = "product_id",
   resolver = EntityIdResolver.class,
   scope=Product.class)
public class Product {

   @Id
   @Column(name = "product_id")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @SequenceGenerator(name = "PRODUCT_SEQ", sequenceName = "PRODUCT_SEQ", allocationSize = 1)
   private Long product_id;


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

   @Column(name = "image", length = 10000)
   @NotNull
   private String image;

   @Column(name = "calories")
   private int calories;

   @Column(name = "weight")
   private int weight;


   @OneToMany(mappedBy = "product_id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   private Set<Comment> comments = new HashSet<Comment>();

   @JsonIgnore
   @OneToMany(mappedBy = "product_id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   private Set<Basket> baskets = new HashSet<Basket>();

   public Product() {
   }


   public Long getProduct_id() {
      return product_id;
   }

   public void setProduct_id(Long product_id) {
      this.product_id = product_id;
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
         "product_id=" + product_id +
         ", name='" + name + '\'' +
         ", price=" + price +
         ", category='" + category + '\'' +
         ", description='" + description + '\'' +
         ", total_count=" + total_count +
         ", available_count=" + available_count +
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
      return Objects.equals(product_id, product.product_id);
   }

   @Override
   public int hashCode() {
      return Objects.hash(product_id);
   }
}
