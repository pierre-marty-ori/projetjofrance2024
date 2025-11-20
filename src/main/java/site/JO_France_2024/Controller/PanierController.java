package site.JO_France_2024.Controller;

import com.google.zxing.WriterException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import site.JO_France_2024.Models.CommandeEntity;
import site.JO_France_2024.Models.OffreEntity;
import site.JO_France_2024.Models.UserEntity;
import site.JO_France_2024.Repository.CommandeRepository;
import site.JO_France_2024.Repository.OffreRepository;
import site.JO_France_2024.Repository.UserRepository;
import site.JO_France_2024.Service.CommandeService;
import site.JO_France_2024.Utils.QRCodeGenerator;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequestMapping("/panier")
public class PanierController {

    @Autowired
    private CommandeService commandeService;

    @Autowired
    private OffreRepository offreRepository;

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender emailSender;

    @GetMapping
    public String panier(Model model) {
        model.addAttribute("title","Votre panier");
        model.addAttribute("articles", commandeService.getArticles());
        model.addAttribute("count", commandeService.getCount());
        return "panier";
    }

    @PostMapping("/ajouter/{id}")
    @ResponseBody
    public String ajouter(@PathVariable Long id) {
        OffreEntity offre = offreRepository.findById(id).orElseThrow();
        commandeService.ajouter(offre);
        return String.valueOf(commandeService.getCount()); // renvoie le nouveau count (AJAX)
    }

    @PostMapping("/valider")
    public String validerCommande(Authentication authentication) throws IOException, WriterException, MessagingException {

        String email = authentication.getName();
        UserEntity user = userRepository.findByEmail(email);

        for (OffreEntity offre : commandeService.getArticles()) {
            CommandeEntity commande = new CommandeEntity();
            commande.setUser(user);
            commande.setOffre(offre);
            commande.setKeyCommande(user.getKeyProfil() + "_" + UUID.randomUUID().toString());
            commande.setDate_commande(LocalDateTime.now());
            commande.setQrCodeImage(QRCodeGenerator.generateQRCode(commande));
            commandeRepository.save(commande);

            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom("jo2024@admin.fr");
            helper.setTo(email);
            helper.setSubject("Votre e-billet");
            helper.setText("Merci pour votre réservation !" + " " + "Vous trouverez votre e-billet en pièce jointe.");
//            helper.addInline(commande.getQrCodeImage(), new File("public/qrcodes/"+commande.getQrCodeImage()));
            helper.addAttachment(commande.getQrCodeImage(), new File("public/qrcodes/"+commande.getQrCodeImage()));
            emailSender.send(mimeMessage);


//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setFrom("jo2024@admin.fr");
//            message.setTo(email);
//            message.setSubject("Votre e-billet");
//            message.setText("Merci de votre commande !" + "\n" + "Votre billet :" + " " + commande.getOffre() );
//            emailSender.send(message);
        }

//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("jo@admin.fr");
//        message.setTo(email);
//        message.setSubject("Votre e-billet");
//        message.setText("Merci de votre commande !");
//        emailSender.send(message);

        commandeService.vider();
        return "redirect:/";
    }

    @PostMapping("/supprimer/{id}")
    public String supprimerArticle(@PathVariable Long id) {
        commandeService.supprimer(id);
        return "redirect:/panier";
    }
}
