package site.JO_France_2024.Models;

import jakarta.validation.constraints.NotEmpty;

public class OffreDTO {

    @NotEmpty(message = "Le champ est requis")
    private String nom;

    @NotEmpty(message = "Le champ est requis")
    private String description;

    private float price;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
