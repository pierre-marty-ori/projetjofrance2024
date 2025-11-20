package site.JO_France_2024.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@EnableWebSecurity
@Order(0)
public class SecurityConfig {

    @Autowired
    AuthenticationSuccessHandler successHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth-> auth
                .requestMatchers("/").permitAll()
//                .requestMatchers("/user/**").permitAll()
                .requestMatchers("/user/login").permitAll()
                .requestMatchers("/user/login/otpVerification").permitAll()
                .requestMatchers("/qrcodes/**").permitAll()
                .requestMatchers("/api/**").permitAll()
                .requestMatchers("/user/register").permitAll()
//                .requestMatchers("/logout").permitAll()
                .requestMatchers("/img/**","/css/**").permitAll()
                .requestMatchers("/offres").permitAll()
                .requestMatchers("/panier").permitAll()
                .requestMatchers("/panier/ajouter/**").permitAll()
                .requestMatchers("/panier/supprimer/**").permitAll()
                .requestMatchers("/panier/valider").authenticated()
                .requestMatchers("/user/vos-commandes/**").authenticated()
                .requestMatchers("/user/votre-profil/**").authenticated()
                .anyRequest().authenticated()
        )
//                .formLogin(form-> form
//                        .defaultSuccessUrl("/",true)
//                )
                .formLogin(form -> form
                        .loginPage("/user/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .loginProcessingUrl("/doLogin")
                        .permitAll().successHandler(successHandler)
//                        .defaultSuccessUrl("/", true)
                )
                .logout(config->config
                        .logoutRequestMatcher(new RegexRequestMatcher("/logout","GET"))
                        .logoutSuccessUrl("/")
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
