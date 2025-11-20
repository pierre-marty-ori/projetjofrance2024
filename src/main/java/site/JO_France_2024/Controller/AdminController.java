package site.JO_France_2024.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import site.JO_France_2024.Models.CommandeEntity;
import site.JO_France_2024.Models.UserDTO;
import site.JO_France_2024.Models.UserEntity;
import site.JO_France_2024.Repository.CommandeRepository;
import site.JO_France_2024.Repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommandeRepository commandeRepository;

    @GetMapping("/register")
    public String adminRegisterForm(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("userDTO", userDTO);
        model.addAttribute("title", "Création Compte Admin");
        return "register-admin";
    }

    @PostMapping("/register")
    public String createAdmin(Model model, @Valid @ModelAttribute UserDTO userDTO, BindingResult result) {

        UserEntity user = userRepository.findByEmail(userDTO.getEmail());

        if(user != null) {
            result.addError(
                    new FieldError("userDTO", "email", "Email déjà utilisé")
            );
        }

        if(result.hasErrors()) {
            return "register";
        }

        try {

            var bCryptEncoder = new BCryptPasswordEncoder();

            UserEntity newUser = new UserEntity();
            newUser.setNom(userDTO.getNom());
            newUser.setPrenom(userDTO.getPrenom());
            newUser.setEmail(userDTO.getEmail());
            newUser.setPassword(bCryptEncoder.encode(userDTO.getPassword()));
            newUser.setKeyProfil(UUID.randomUUID().toString());
            newUser.setRole("ADMIN");

            userRepository.save(newUser);

            model.addAttribute("success", true);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "register";
    }

    @GetMapping("/login")
    public String loginAdminForm(Model model) {
        model.addAttribute("title", "Connexion Admin");
        return "login-admin";
    }

    @GetMapping("/dashboard")
    public String dashboardAdmin(Model model) {
        model.addAttribute("title","Dashboard");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getName();
        model.addAttribute("user", user);

        long totalCommandes = commandeRepository.count();
        model.addAttribute("totalCommandes",totalCommandes);

        long totalCommandesOffreSolo = commandeRepository.countByOffreNom("Offre Solo");
        model.addAttribute("totalCommandesOffreSolo",totalCommandesOffreSolo);

        long totalCommandesOffreDuo = commandeRepository.countByOffreNom("Offre Duo");
        model.addAttribute("totalCommandesOffreDuo",totalCommandesOffreDuo);

        long totalCommandesOffreFamille = commandeRepository.countByOffreNom("Offre Famille");
        model.addAttribute("totalCommandesOffreFamille",totalCommandesOffreFamille);

        return "dashboard";
    }

    @GetMapping("/clients")
    public String ClientsList(Model model) {
        String role = "USER";
        List<UserEntity> clients = Collections.singletonList(userRepository.findByRole(role));
        model.addAttribute("clients", clients);
        model.addAttribute("title","Liste des clients");
        return "users-admin";
    }

    @GetMapping("/commandes")
    public String CommandesList(Model model) {

        List<CommandeEntity> commandes = commandeRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
        model.addAttribute("commandes", commandes);
        model.addAttribute("title","Liste des Commandes");

        return "commandes-admin";
    }

    @GetMapping("/mon-profil-admin/{id}")
    public String monProfilAdmin(@PathVariable Long id, Model model) {
        UserEntity user = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Utilisateur non trouvé")
        );
        model.addAttribute("title","Votre profil admin");
        model.addAttribute("user", user);
        return "votre-profil-admin";
    }

}
