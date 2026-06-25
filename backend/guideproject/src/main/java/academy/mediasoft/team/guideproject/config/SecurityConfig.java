package academy.mediasoft.team.guideproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests((requests) ->
                requests.requestMatchers(HttpMethod.POST, "/api/landmarks").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/landmarks/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/landmarks/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/categories").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/reviews/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/ratings/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/reviews").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/reviews**").authenticated()

                        .requestMatchers(HttpMethod.POST, "/api/ratings").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/ratings**").authenticated()

                        .requestMatchers("/api/landmarks/**").permitAll()
                        .requestMatchers("/api/reviews/**").permitAll()
                        .requestMatchers("/api/ratings/**").permitAll()
                        .requestMatchers("/api/categories/**").permitAll()
                        .requestMatchers("/api/persons/register").permitAll()

                        .anyRequest().authenticated())

                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
