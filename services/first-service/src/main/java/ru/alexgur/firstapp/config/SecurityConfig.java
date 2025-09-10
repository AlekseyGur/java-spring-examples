package ru.alexgur.firstapp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/manager/**").authenticated()
                        .requestMatchers("/**").permitAll()
                )
                // подумать над STATELESS:
                // .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2Login(Customizer.withDefaults())
                .oauth2Client(Customizer.withDefaults())
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }

    @Bean
    public OAuth2UserService<OidcUserRequest, OidcUser> oAuth2UserService() {
        OidcUserService oidcUserService = new OidcUserService();
        return userRequest -> {
            try {
                OidcUser oidcUser = oidcUserService.loadUser(userRequest);

                List<GrantedAuthority> authorities =
                        Stream.concat(oidcUser.getAuthorities().stream(),
                                        Optional.ofNullable(oidcUser.getClaimAsStringList("roles"))
                                                .orElseGet(List::of)
                                                .stream()
                                                .filter(role -> role.startsWith("ROLE_"))
                                                .map(SimpleGrantedAuthority::new)
                                                .map(GrantedAuthority.class::cast))
                                .toList();

                log.info("Привилегии пользователя: {}", authorities.toString());

                return new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
            } catch (Exception e) {
                log.error("Ошибка при обработке привилегий пользователя", e);
                throw new RuntimeException("Ошибка при обработке привилегий пользователя", e);
            }
        };
    }

}