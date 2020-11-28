package org.canteen.models;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.canteen.security.model.User;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "basket")
public class Basket {

   @Id
   @Column(name = "basket_id")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long basket_id;

   @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
   @JoinColumn(name = "id_product")
//   @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="product_id")
//   @JsonIdentityReference(alwaysAsId = true)
   private Product product_id;

   @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
//   @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="username")
   @JsonIdentityReference(alwaysAsId = true)
   @JoinColumn(name = "id_user")
   private User user;

   @Column(name = "count")
   private int count;

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

   @Override
   public String toString() {
      return "Basket{" +
         "basket_id=" + basket_id +
         ", product_id=" + product_id +
         ", user=" + user +
         ", count=" + count +
         '}';
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Basket basket = (Basket) o;
      return count == basket.count &&
         Objects.equals(basket_id, basket.basket_id) &&
         Objects.equals(product_id, basket.product_id) &&
         Objects.equals(user, basket.user);
   }

   @Override
   public int hashCode() {
      return Objects.hash(basket_id, product_id, user, count);
   }
}
