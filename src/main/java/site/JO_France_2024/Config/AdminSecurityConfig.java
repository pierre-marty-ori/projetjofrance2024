package site.JO_France_2024.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@EnableWebSecurity
@Order(1)
public class AdminSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChainAdmin(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/admin/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/login", "/admin/register", "/img/**", "/css/**").permitAll()
                        .requestMatchers("/admin/dashboard").hasRole("ADMIN")
                        .requestMatchers("/admin/clients").hasRole("ADMIN")
                        .requestMatchers("/admin/offres").hasRole("ADMIN")
                        .requestMatchers("/admin/add-offre").hasRole("ADMIN")
                        .requestMatchers("/admin/editer-offre").hasRole("ADMIN")
                        .requestMatchers("/admin/commandes").hasRole("ADMIN")
                        .requestMatchers("/admin/mon-profil-admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
//                .formLogin(form->form
//                        .defaultSuccessUrl("/", true))
                .formLogin(form -> form
                        .loginPage("/admin/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/admin/dashboard", true)
                        .loginProcessingUrl("/admin/doLogin")
                )
                .logout(config->config
                        .logoutRequestMatcher(new RegexRequestMatcher("/admin/logout","GET"))
                        .logoutSuccessUrl("/")
                )
                .build();
    }
}
