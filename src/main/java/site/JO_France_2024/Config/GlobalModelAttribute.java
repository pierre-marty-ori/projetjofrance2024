package site.JO_France_2024.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import site.JO_France_2024.Models.UserEntity;
import site.JO_France_2024.Repository.UserRepository;
import site.JO_France_2024.Service.CommandeService;

import java.util.Optional;

@ControllerAdvice
public class GlobalModelAttribute {

    @Autowired
    private CommandeService commandeService;
    @Autowired
    private UserRepository userRepository;


    @ModelAttribute("cartCount")
    public int getCartCount() {
        return commandeService.getCount();
    }

    @ModelAttribute("currentUser")
    public UserEntity getCurrentUser(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Optional<UserEntity> user = Optional.ofNullable(userRepository.findByEmail(authentication.getName()));
            return user.orElse(null);
        }
        return null;
    }
}
