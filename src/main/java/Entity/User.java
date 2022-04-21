package Entity;

import javax.persistence.*;

@Entity
@Table(name = "user_table")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    String name;

    @Column(name = "username")
    String username;

    @Column(name = "email")
    String email;

    @Embedded
    Address address;

    @Column(name = "phone")
    String phone;

    @Column(name = "website")
    String website;

    @Embedded
    Company company;


}
