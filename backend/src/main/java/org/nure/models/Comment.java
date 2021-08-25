package org.nure.models;

import com.fasterxml.jackson.annotation.*;
import org.nure.security.model.User;

import javax.persistence.*;

@Entity
@Table(name = "comment")
public class Comment {

   @Id
   @Column(name = "comment_id")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @SequenceGenerator(name = "COMMENT_SEQ", sequenceName = "COMMENT_SEQ", allocationSize = 1)
   private Long comment_id;

   @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
//   @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="username")
   @JsonIdentityReference(alwaysAsId = true)
   @JoinColumn(name = "id_user")
   private User user;


   @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
   @JoinColumn(name = "id_product")
//   @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="product")
   @JsonIdentityReference(alwaysAsId = true)
   private Product product;

   @Column(name = "date")
   private String date;

   @Column(name = "msg")
   private String msg;

   public Comment() {
   }

   public Long getComment_id() {
      return comment_id;
   }

   public void setComment_id(Long comment_id) {
      this.comment_id = comment_id;
   }

   public String getDate() {
      return date;
   }

   public void setDate(String date) {
      this.date = date;
   }

   public String getMsg() {
      return msg;
   }

   public void setMsg(String msg) {
      this.msg = msg;
   }

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   public Product getProduct_id() {
      return product;
   }

   public void setProduct_id(Product product) {
      this.product = product;
   }

//   @Override
//   public boolean equals(Object o) {
//      if (this == o) return true;
//      if (o == null || getClass() != o.getClass()) return false;
//      Comment comment = (Comment) o;
//      return Objects.equals(comment_id, comment.comment_id) &&
//         Objects.equals(user, comment.user) &&
//         Objects.equals(product, comment.product) &&
//         Objects.equals(date, comment.date) &&
//         Objects.equals(msg, comment.msg);
//   }
//
//   @Override
//   public int hashCode() {
//      return Objects.hash(comment_id, user, product, date, msg);
//   }
//
//   @Override
//   public String toString() {
//      return "Comment{" +
//         "comment_id=" + comment_id +
//         ", user=" + user +
//         ", product=" + product +
//         ", date='" + date + '\'' +
//         ", msg='" + msg + '\'' +
//         '}';
//   }
}
