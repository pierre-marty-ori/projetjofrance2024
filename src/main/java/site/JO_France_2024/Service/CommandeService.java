package site.JO_France_2024.Service;

import org.springframework.stereotype.Service;
import site.JO_France_2024.Models.OffreEntity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class CommandeService {
    private final Map<Long, OffreEntity> commande = new HashMap<>();

    public void ajouter(OffreEntity offre) {
        commande.put(offre.getId(), offre);
    }

    public void supprimer(Long id) {
        commande.remove(id);
    }

    public void vider() {
        commande.clear();
    }

    public Collection<OffreEntity> getArticles() {
        return commande.values();
    }

    public int getCount() {
        return commande.size();
    }

}
