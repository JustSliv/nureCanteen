package org.canteen.models;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.canteen.security.model.User;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "basket")
public class Basket {

   @Id
   @Column(name = "basket_id")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @SequenceGenerator(name = "BASKET_SEQ", sequenceName = "BASKET_SEQ", allocationSize = 1)
   private Long basket_id;

   @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
   @JoinColumn(name = "id_product")
   private Product product_id;

   @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
//   @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="username")
   @JsonIdentityReference(alwaysAsId = true)
   @JoinColumn(name = "id_user")
   private User user;

   @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
//   @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="username")
   @JsonIdentityReference(alwaysAsId = true)
   @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
   @JoinColumn(name = "check_id")
   private Check check_id;

   @Column(name = "product_name")
   private String productName;

   @Column(name = "count")
   private int count;

   @Column(name = "active")
   private boolean active;

   public Basket() {
   }

   public Long getBasket_id() {
      return basket_id;
   }

   public void setBasket_id(Long basket_id) {
      this.basket_id = basket_id;
   }

   public Product getProduct_id() {
      return product_id;
   }

   public void setProduct_id(Product product_id) {
      this.product_id = product_id;
   }

   public int getCount() {
      return count;
   }

   public void setCount(int count) {
      this.count = count;
   }

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   public Check getCheck_id() {
      return check_id;
   }

   public void setCheck_id(Check check_id) {
      this.check_id = check_id;
   }

   public boolean isActive() {
      return active;
   }

   public void setActive(boolean active) {
      this.active = active;
   }

   public String getProductName() {
      return productName;
   }

   public void setProductName(String productName) {
      this.productName = productName;
   }

   @Override
   public String toString() {
      return "Basket{" +
         "basket_id=" + basket_id +
         ", product_id=" + product_id +
         ", user=" + user +
         ", check_id=" + check_id +
         ", count=" + count +
         ", active=" + active +
         '}';
   }
}
