package site.JO_France_2024;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import site.JO_France_2024.Models.UserEntity;

import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class ProfileTest {

    @Test
    public void addProfile() {

        var bCryptEncoder = new BCryptPasswordEncoder();

        UserEntity newUser = new UserEntity();
        newUser.setNom("Doe");
        newUser.setPrenom("John");
        newUser.setEmail("john.doe@test.fr");
        newUser.setRole("USER");
        newUser.setKeyProfil(UUID.randomUUID().toString());
        newUser.setPassword(bCryptEncoder.encode("123456"));

    }
}
