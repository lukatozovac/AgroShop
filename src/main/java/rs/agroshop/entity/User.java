package rs.agroshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import rs.agroshop.model.UserType;

@Entity
@Table(name  = "user")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    
    @Column(name="name", nullable = false, length = 50)
    private String name;

    @Column(name="surname", nullable = false, length = 50)
    private String surname;

    @Column(name="email", unique = true, nullable = false, length = 100)
    private String email;

    @Column(name="password", nullable = false, length = 255)
    private String password;

    @Column(name="phone_number", nullable = false, length = 50)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name ="role", nullable = false)
    private UserType userType;
}
