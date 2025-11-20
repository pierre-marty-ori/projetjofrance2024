package site.JO_France_2024;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import site.JO_France_2024.Models.CommandeEntity;
import site.JO_France_2024.Models.OffreEntity;
import site.JO_France_2024.Models.UserEntity;
import site.JO_France_2024.Repository.CommandeRepository;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class ShoppingCartTest {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testAddOneCartItem() {
        OffreEntity offre = testEntityManager.find(OffreEntity.class, 1);
        UserEntity user = testEntityManager.find(UserEntity.class,1);

        CommandeEntity newCommande = new CommandeEntity();
        newCommande.setUser(user);
        newCommande.setOffre(offre);
        newCommande.setKeyCommande(UUID.randomUUID().toString());
        newCommande.setDate_commande(LocalDateTime.now());

        CommandeEntity saveCommande = commandeRepository.save(newCommande);

        assertTrue(saveCommande.getId() > 0);

    }

//    @Test
//    public void testGetCommandeByUser() {
//        UserEntity user = new UserEntity();
//        user.setId(1L);
//
//        List<CommandeEntity> commandes = commandeRepository.findByUser(user);
//        assertEquals(2, commandes.size());
//    }

}
