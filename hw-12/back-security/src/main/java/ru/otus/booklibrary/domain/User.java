package ru.otus.booklibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;
import ru.otus.booklibrary.config.SecurityConfig;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 512)
    @Column(name = "name", nullable = false)
    @NotNull
    private String name;
    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotEmpty
    @Size(max = 128)
    private String email;
    @Column(name = "password")
    @NotEmpty
    @Size(max = 256)
    private String password;
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public User(User user) {
        this(user.id, user.email, user.name, user.password, user.roles);
    }

    public void setEmail(String email) {
        this.email = StringUtils.isEmpty(email) ? null : email.toLowerCase();
    }

    //    https://stackoverflow.com/questions/30260582/password-encoding-with-spring-data-rest
    public void setPassword(String rawPassword) {
        this.password = StringUtils.isEmpty(rawPassword) ? null : SecurityConfig.DELEGATING_PASSWORD_ENCODER.encode(rawPassword);
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", roles=" + roles +
                '}';
    }
}
