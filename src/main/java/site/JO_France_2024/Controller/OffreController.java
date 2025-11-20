package site.JO_France_2024.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import site.JO_France_2024.Models.OffreEntity;
import site.JO_France_2024.Repository.OffreRepository;

import java.util.List;

@Controller
public class OffreController {

    @Autowired
    private OffreRepository offreRepository;

    @GetMapping("/offres")
    public String offres(Model model) {
        List<OffreEntity> offres = offreRepository.findAll();
        model.addAttribute("title","Les Offres");
        model.addAttribute("offres", offres);
        return "offres";
    }

}
