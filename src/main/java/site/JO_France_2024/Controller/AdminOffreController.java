package site.JO_France_2024.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import site.JO_France_2024.Models.OffreDTO;
import site.JO_France_2024.Models.OffreEntity;
import site.JO_France_2024.Repository.OffreRepository;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminOffreController {

    @Autowired
    private OffreRepository offreRepository;

    @GetMapping("/offres")
    public String offres(Model model) {
        model.addAttribute("title","Les Offres");
        List<OffreEntity> offres = offreRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        model.addAttribute("offres", offres);
        return "offres-admin";
    }

    @GetMapping("/ajouter-offre")
    public String ajoutOffreForm(Model model) {
        OffreDTO offreDTO = new OffreDTO();
        model.addAttribute("offreDTO", offreDTO);
        model.addAttribute("title","Ajout d'une offre");
        return "ajouter-offre";
    }

    @PostMapping("/ajouter-offre")
    public String ajouterOffre(@Valid @ModelAttribute OffreDTO offreDTO, BindingResult result) {

        if(result.hasErrors()) {
            return "ajouter-offre";
        }

        OffreEntity offre = new OffreEntity();
        offre.setNom(offreDTO.getNom());
        offre.setNom(offreDTO.getNom());
        offre.setDescription(offreDTO.getDescription());
        offre.setPrice(offreDTO.getPrice());

        offreRepository.save(offre);

        return "redirect:/admin/offres";

    }

    @GetMapping("/editer-offre")
    public String editOffrePage(Model model, @RequestParam Long id) {

        try {
            OffreEntity offre = offreRepository.findById((long)id).get();
            model.addAttribute("offre", offre);

            model.addAttribute("title","Modification");

            OffreDTO offreDTO = new OffreDTO();
            offreDTO.setNom(offre.getNom());
            offreDTO.setDescription(offre.getDescription());
            offre.setPrice(offre.getPrice());
            model.addAttribute("offreDTO", offreDTO);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/admin/offres";
        }

        return "editer-offre";
    }

    @PostMapping("/editer-offre")
    public String editOffre(Model model, @RequestParam Long id, @Valid @ModelAttribute OffreDTO offreDTO, BindingResult result) {
        try {

            OffreEntity offre = offreRepository.findById((long)id).get();
            model.addAttribute("offre", offre);

            offre.setNom(offreDTO.getNom());
            offre.setDescription(offreDTO.getDescription());
            offre.setPrice(offreDTO.getPrice());

            offreRepository.save(offre);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin/offres";
    }

    @GetMapping("/delete-offre")
    public String deleteOffre(@RequestParam Long id) {
        try {
            OffreEntity offre = offreRepository.findById((long)id).get();
            offreRepository.delete(offre);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "redirect:/admin/offres";
    }

}