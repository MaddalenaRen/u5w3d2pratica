package it.epicode.u5w3d2pratica.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //abilita la classe ad essere responsabile della sicurezza dei servizi
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //formLogin serve per creare in automatico una pagina di login. A noi non serve,perchè non usiamo pagine
        httpSecurity.formLogin(http->http.disable());
        //csrf serve per evitare la possibilità di utilizzi di sessioni aperte, ma i rest non usano sessioni e quindi disable
        httpSecurity.csrf(http->http.disable());
        httpSecurity.sessionManagement(http->http.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        //serve per bloccare richieste che provengono da domini(indirizzo ip e porta) esterni a quelli del servizio
        httpSecurity.cors(Customizer.withDefaults());

        httpSecurity.authorizeHttpRequests(http->http.requestMatchers("/auth/**").permitAll());
//        httpSecurity.authorizeHttpRequests(http->http.requestMatchers(HttpMethod.GET,"/studenti/**").permitAll());

        httpSecurity.authorizeHttpRequests(http->http.requestMatchers("/dipendenti/**").permitAll());
        httpSecurity.authorizeHttpRequests(http->http.requestMatchers("/viaggi/**").permitAll());
        httpSecurity.authorizeHttpRequests(http->http.requestMatchers("/prenotazioni/**").permitAll());
        httpSecurity.authorizeHttpRequests(http->http.anyRequest().denyAll());

        return httpSecurity.build();
    }
}