package site.JO_France_2024.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.JO_France_2024.Models.UserEntity;
import site.JO_France_2024.Repository.UserRepository;

@Service
public class UserService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByEmail(email);

        if(user!= null) {

            return User.withUsername(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRole())
                    .build();
        }
        return null;
    }

    public String generateOtp(UserEntity user) {
        try {
            int randomPIN = (int) (Math.random() * 900000) + 100000;
            user.setOtp(String.valueOf(randomPIN));
            userRepository.save(user);
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom("joparis2024@admin.fr");
            msg.setTo(user.getEmail());
            msg.setSubject("Code Vérification");
            msg.setText("Hello, your code is" + " " + randomPIN);
            mailSender.send(msg);
            return "success";

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
