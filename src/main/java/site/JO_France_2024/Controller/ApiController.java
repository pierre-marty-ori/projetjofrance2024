package site.JO_France_2024.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.JO_France_2024.Models.OffreEntity;
import site.JO_France_2024.Models.UserEntity;
import site.JO_France_2024.Repository.OffreRepository;
import site.JO_France_2024.Repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private OffreRepository offreRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/offres")
    public List<OffreEntity> getOffres() {
        return offreRepository.findAll();
    }

    @GetMapping("/users")
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

}
