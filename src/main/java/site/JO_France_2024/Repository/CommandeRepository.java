package site.JO_France_2024.Repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import site.JO_France_2024.Models.CommandeEntity;
import site.JO_France_2024.Models.UserEntity;

import java.util.List;

public interface CommandeRepository extends JpaRepository<CommandeEntity, Long> {

    public List<CommandeEntity> findByUser(UserEntity user, Sort id);

    long countByOffreNom(String nom);

}
