package site.JO_France_2024.Models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "commandes")
public class CommandeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    public Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "offre_id")
    private OffreEntity offre;

    private String keyCommande;

    private LocalDateTime date_commande;

    private String qrCodeImage;

    public String getQrCodeImage() {
        return qrCodeImage;
    }

    public void setQrCodeImage(String qrCodeImage) {
        this.qrCodeImage = qrCodeImage;
    }

    public LocalDateTime getDate_commande() {
        return date_commande;
    }

    public void setDate_commande(LocalDateTime date_commande) {
        this.date_commande = date_commande;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public OffreEntity getOffre() {
        return offre;
    }

    public void setOffre(OffreEntity offre) {
        this.offre = offre;
    }

    public String getKeyCommande() {
        return keyCommande;
    }

    public void setKeyCommande(String keyCommande) {
        this.keyCommande = keyCommande;
    }
}
