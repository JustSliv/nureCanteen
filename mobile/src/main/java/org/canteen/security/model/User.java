package org.canteen.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "usr")
public class User {

   @Id
   @Column(name = "id")
   @GeneratedValue(strategy = GenerationType.AUTO)
   @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ", allocationSize = 1)
   private Long id;

   @Column(name = "username", length = 50, unique = true)
   @NotNull
   @Size(min = 4, max = 50)
   private String username;

   @Column(name = "password", length = 100)
   @NotNull
   @Size(min = 4, max = 100)
   private String password;

   @Column(name = "fName", length = 50)
   @Size(min = 4, max = 50)
   private String fName;

   @Column(name = "lName", length = 50)
   @Size(min = 4, max = 50)
   private String lName;

   @Column(name = "email", length = 50)
   @NotNull
   @Size(min = 4, max = 50)
   private String email;

   @Column(name = "avatar", length = 10000)
   @Size(min = 4, max = 10000)
   private String avatar;

   @Column(name = "personGroup", length = 50)
   @Size(min = 4, max = 50)
   private String personGroup;

   @Column(name = "phone", length = 50)
   @NotNull
   @Size(min = 4, max = 50)
   private String phone;

   @Column(name = "age", length = 50)
   private String age;

   @JsonIgnore
   @Column(name = "activated")
   private boolean activated;

   @Transient
   private String passwordConfirm;

   @ManyToMany
   @JoinTable(
      name = "USER_AUTHORITY",
      joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
      inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_NAME", referencedColumnName = "NAME")})
   @BatchSize(size = 20)
   private Set<Authority> authorities = new HashSet<>();

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getfName() {
      return fName;
   }

   public void setfName(String fName) {
      this.fName = fName;
   }

   public String getlName() {
      return lName;
   }

   public void setlName(String lName) {
      this.lName = lName;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public boolean isActivated() {
      return activated;
   }

   public void setActivated(boolean activated) {
      this.activated = activated;
   }

   public Set<Authority> getAuthorities() {
      return authorities;
   }

   public void setAuthorities(Set<Authority> authorities) {
      this.authorities = authorities;
   }

   public void setPasswordConfirm(String passwordConfirm) {
      this.passwordConfirm = passwordConfirm;
   }

   public String getAvatar() {
      return avatar;
   }

   public void setAvatar(String avatar) {
      this.avatar = avatar;
   }

   public String getPersonGroup() {
      return personGroup;
   }

   public void setPersonGroup(String personGroup) {
      this.personGroup = personGroup;
   }

   public String getPhone() {
      return phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public String getAge() {
      return age;
   }

   public void setAge(String age) {
      this.age = age;
   }


   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      User user = (User) o;
      return id.equals(user.id);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id);
   }

   @Override
   public String toString() {
      return "User{" +
         "username='" + username + '\'' +
         ", password='" + password + '\'' +
         ", fName='" + fName + '\'' +
         ", lName='" + lName + '\'' +
         ", email='" + email + '\'' +
         ", phone=" + phone +
         ", age=" + age +
         ", authorities=" + authorities +
         '}';
   }

   public String getPasswordConfirm() {
      return passwordConfirm;
   }

   public User() {
   }

   public User(Long id, @NotNull @Size(min = 4, max = 50) String username, @NotNull @Size(min = 4, max = 100) String password, @NotNull @Size(min = 4, max = 50) String fName, @NotNull @Size(min = 4, max = 50) String lName, @NotNull @Size(min = 4, max = 50) String email, @NotNull boolean activated, String passwordConfirm, Set<Authority> authorities) {
      this.id = id;
      this.username = username;
      this.password = password;
      this.fName = fName;
      this.lName = lName;
      this.email = email;
      this.activated = activated;
      this.passwordConfirm = passwordConfirm;
      this.authorities = authorities;
   }


}
