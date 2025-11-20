package site.JO_France_2024.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.JO_France_2024.Models.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public UserEntity findByEmail(String email);

    public UserEntity findByRole(String role);
}
