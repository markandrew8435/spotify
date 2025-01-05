package voosh.ai.spotify.repository;//package voosh.ai.spotify.repository;
//import voosh.ai.spotify.entities.BaseEntity;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import java.time.LocalDateTime;
//
//@Entity
//@Getter
//@Setter
//@Table(name = "user_details")  // Ensures email and phoneNumber are unique
//public class UserDetailsEntity extends BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)  // Makes email non-nullable
//    private String email;
//
//    private String firstName;
//
//    private String lastName;
//
//    @Column(unique = true, nullable = false)  // Email is unique and non-nullable
//    private String email;
//
//    @Column(unique = true, nullable = false)  // Phone number is unique and non-nullable
//    private String phoneNumber;
//
//    private LocalDateTime dateOfBirth;
//
//}
