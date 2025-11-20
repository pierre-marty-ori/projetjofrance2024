package site.JO_France_2024.Controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class AccountController {
    @Autowired
    private UserRepository repo;

    @Autowired
    private CommandeRepository commandeRepository;

    @GetMapping("/register")
    public String registerPage(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("userDTO", userDTO);
        model.addAttribute("title", "Création de compte");

        return "register";
    }

    @PostMapping("/register")
    public String createUser(Model model, @Valid @ModelAttribute UserDTO userDTO, BindingResult result) {

        UserEntity user = repo.findByEmail(userDTO.getEmail());
        if(user!= null) {
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
            newUser.setRole("USER");

            repo.save(newUser);

            model.addAttribute("success", true);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "register";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Connexion");
        return "login";
    }

    @GetMapping("/otpVerification")
    public String optSent(Model model, UserDTO userDTO) {
        model.addAttribute("title","Verification");
        return "otpScreen";
    }

    @PostMapping("/otpVerification")
    public String optVerification(UserDTO userDTO, HttpSession session) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails user = (UserDetails) securityContext.getAuthentication().getPrincipal();
        UserEntity users = repo.findByEmail(user.getUsername());
        if(users.getOtp().equals(userDTO.getOtp())) {
            return "redirect:/";
        }else {
            return "redirect:/user/otpVerification?error";
        }
    }

    @GetMapping("/vos-commandes/{id}")
    public String vosCommandes(@PathVariable Long id, Model model) {
        UserEntity user = repo.findById(id).orElseThrow(
                () -> new RuntimeException("Utilisateur non trouvé")
        );
        List<CommandeEntity> commandes = commandeRepository.findByUser(user, Sort.by(Sort.Direction.DESC,"id"));
        model.addAttribute("title", "Vos Commandes");
        model.addAttribute("commandes", commandes);
        model.addAttribute("user", user);
        return "vos-commandes";
    }

    @GetMapping("/votre-profil/{id}")
    public String votreProfil(@PathVariable Long id, Model model) {
        UserEntity user = repo.findById(id).orElseThrow(
                () -> new RuntimeException("Utilisateur non trouvé")
        );
        model.addAttribute("title","Votre profil");
        model.addAttribute("user", user);
        return "votre-profil";
    }
}
