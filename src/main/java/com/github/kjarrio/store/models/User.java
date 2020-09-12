package com.github.kjarrio.store.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

@Entity(name = "users")
@ApiModel("Users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @ApiModelProperty(hidden = true)
    private Integer id;

    @Column(nullable = false, updatable = false)
    @ApiModelProperty(name = "Name", value = "", position = 1)
    private String name;

    @Column(unique = true, nullable = false, updatable = false)
    @ApiModelProperty(name = "Email", value = "", position = 2)
    @Email(message = "Email should be valid")
    private String email;

    @Column(nullable = false)
    @ApiModelProperty(name = "Password", value = "", position = 3)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    @ApiModelProperty(name = "Telephone", value = "", position = 3)
    private String telephone;

    @Column(nullable = false)
    @ApiModelProperty(name = "Address", value = "", position = 4)
    private String address;

    @Column(nullable = false, updatable = false)
    @ApiModelProperty(name = "BirthDate", value = "", position = 5)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthDate;

    public User() {

    }

    public User(String name, String email, String password, String telephone, String address, Date birthDate) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.address = address;
        this.birthDate = birthDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    public String getUsername() {
        return email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }



    @Override
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getEmail().equals("admin@admin.com") ? Arrays.asList((GrantedAuthority) () -> "ROLE_ADMIN", (GrantedAuthority) () -> "ROLE_USER") : new ArrayList<>();
    }

    @Override
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(email, user.email) &&
                Objects.equals(telephone, user.telephone) &&
                Objects.equals(address, user.address) &&
                Objects.equals(birthDate, user.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, telephone, address, birthDate);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", telephone='" + telephone + '\'' +
                ", address='" + address + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }

}