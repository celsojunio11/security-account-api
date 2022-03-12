package com.security.account.entity;

import com.security.account.controller.request.user.UserRequest;
import com.security.account.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tb_user")
public class User {
    @Id
    @Column(updatable = false, unique = true, nullable = false)
    private String clientId;

    @Column(nullable = false)
    private String nameClient;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDate createdAt;

    @Column(nullable = false)
    private LocalDate updatedAt;

    public static User create(
            UserRequest request,
            UserRepository repository
    ) {
        return repository.save(new User(
                UUID.randomUUID().toString(),
                request.getNameClient(),
                request.getEmail(),
                new BCryptPasswordEncoder().encode(request.getPassword()),
                LocalDate.now(),
                LocalDate.now())
        );
    }

}
