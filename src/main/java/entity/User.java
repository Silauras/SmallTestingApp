package entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "user_table")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    @JsonProperty("id")
    private Integer id;

    @Column(name = "name")
    @JsonProperty("name")
    String name;

    @Column(name = "username")
    @JsonProperty("username")
    String username;

    @Column(name = "email")
    @JsonProperty("email")
    String email;

    @Embedded
    @JsonProperty("address")
    Address address;

    @Column(name = "phone")
    @JsonProperty("phone")
    String phone;

    @Column(name = "website")
    @JsonProperty("website")
    String website;

    @Embedded
    @JsonProperty("company")
    Company company;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", phone='" + phone + '\'' +
                ", website='" + website + '\'' +
                ", company=" + company +
                '}';
    }
}
