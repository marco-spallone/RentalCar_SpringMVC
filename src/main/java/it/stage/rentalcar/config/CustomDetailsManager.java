package it.stage.rentalcar.config;

import it.stage.rentalcar.domain.Utente;
import it.stage.rentalcar.service.UtenteService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class CustomDetailsManager implements UserDetailsService {
    UtenteService utenteService;

    private final PasswordEncoder passwordEncoder;

    public CustomDetailsManager(UtenteService utenteService, @Lazy PasswordEncoder passwordEncoder) {
        this.utenteService = utenteService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Utente utente = utenteService.getUserFromUsername(s);
        MyUserDetails myUserDetails = new MyUserDetails();
        if (utente != null) {
            myUserDetails.setUsername(s);
            myUserDetails.setId(utente.getIdUtente());
            myUserDetails.setPassword(passwordEncoder.encode(utente.getPassword()));
            Set<GrantedAuthority> authorities = new HashSet<>();
            if(utente.getIsAdmin()) {
                authorities.add(new SimpleGrantedAuthority("ADMIN"));
            } else {
                authorities.add(new SimpleGrantedAuthority("CUSTOMER"));
            }
            myUserDetails.setAuthorities(authorities);
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
        return myUserDetails;
    }
}
