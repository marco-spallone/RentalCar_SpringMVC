package it.stage.rentalcar.config;

import it.stage.rentalcar.domain.Utente;
import it.stage.rentalcar.service.UtenteService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CustomDetailsManager implements UserDetailsService {
    UtenteService utenteService;

    public CustomDetailsManager(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Utente utente = utenteService.getUserFromUsername(s);
        User.UserBuilder builder;
        if (utente != null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(s);
            builder.password(new BCryptPasswordEncoder().encode(utente.getPassword()));
            if(utente.getIsAdmin()) {
                builder.roles("ADMIN");
            } else {
                builder.roles("CUSTOMER");
            }
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
        return builder.build();
    }
}
