package org.canteen.models;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.canteen.security.model.User;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "checks")
public class Check {

   @Id
   @Column(name = "check_id", unique = true)
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long check_id;

   @JsonIgnore
   @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
//   @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="username")
   @JsonIdentityReference(alwaysAsId = true)
   @JoinColumn(name = "id_user")
   private User user_id;

   @Column(name = "canteen")
   private String canteen;

   @Column(name = "date")
   private Date  purchaseDate;

   @Column(name = "time", columnDefinition = "time")
   private Time time;

   @JsonIgnore
   @OneToMany(mappedBy = "check_id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   private Set<Basket> products = new HashSet<Basket>();

   @Column(name = "sum")
   private int sum;

   public Check() {
   }

   public Long getCheck_id() {
      return check_id;
   }

   public void setCheck_id(Long check_id) {
      this.check_id = check_id;
   }

   public User getUser_id() {
      return user_id;
   }

   public void setUser_id(User user_id) {
      this.user_id = user_id;
   }

   public String getCanteen() {
      return canteen;
   }

   public void setCanteen(String canteen) {
      this.canteen = canteen;
   }

   public Date getPurchaseDate() {
      return purchaseDate;
   }

   public void setPurchaseDate(Date purchaseDate) {
      this.purchaseDate = purchaseDate;
   }

   public Time getTime() {
      return time;
   }

   public void setTime(Time time) {
      this.time = time;
   }

   public Set<Basket> getProducts() {
      return products;
   }

   public void setProducts(Set<Basket> products) {
      this.products = products;
   }

   public int getSum() {
      return sum;
   }

   public void setSum(int sum) {
      this.sum = sum;
   }

   @Override
   public String toString() {
      return "Check{" +
         "check_id=" + check_id +
         ", user=" + user_id +
         ", canteen='" + canteen + '\'' +
         ", purchaseDate='" + purchaseDate + '\'' +
         ", products=" + products +
         ", sum=" + sum +
         '}';
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Check check = (Check) o;
      return Objects.equals(check_id, check.check_id);
   }

   @Override
   public int hashCode() {
      return Objects.hash(check_id);
   }
}
