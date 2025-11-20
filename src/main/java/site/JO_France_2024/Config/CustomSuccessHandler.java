package site.JO_France_2024.Config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import site.JO_France_2024.Models.UserEntity;
import site.JO_France_2024.Repository.UserRepository;
import site.JO_France_2024.Service.UserService;

import java.io.IOException;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String redirectUrl = null;

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        UserEntity user = userRepository.findByEmail(username);
        String output = userService.generateOtp(user);
        if(output.equals("success")){
            redirectUrl="/user/otpVerification";
        }

        new DefaultRedirectStrategy().sendRedirect(request,response,redirectUrl);
    }
}
