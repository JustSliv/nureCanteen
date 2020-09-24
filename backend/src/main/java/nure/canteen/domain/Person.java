package nure.canteen.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@ToString(of = {"id", "fName", "sName", "age", "personGroup", "phone", "email", "avatar"})
@EqualsAndHashCode(of = {"id"})
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String fName;
    @NotNull
    private String avatar;
    @NotNull
    private String sName;
    @NotNull
    private int age;
    @NotNull
    private String personGroup;
    @Nullable
    private String phone;
    @Nullable
    private String email;

    public Person(Long id, String fName, String avatar, String sName, int age, String personGroup, String phone, String email, LocalDateTime creationTime) {
        this.id = id;
        this.fName = fName;
        this.avatar = avatar;
        this.sName = sName;
        this.age = age;
        this.personGroup = personGroup;
        this.phone = phone;
        this.email = email;
        this.creationTime = creationTime;
    }

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")



    private LocalDateTime creationTime;

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }
}
