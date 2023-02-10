package it.stage.rentalcar.config;

import it.stage.rentalcar.domain.Utente;
import it.stage.rentalcar.service.UtenteService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CustomDetailsManager implements UserDetailsService {
    UtenteService utenteService;


    public CustomDetailsManager(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Utente utente = utenteService.getUserFromUsername(s);
        MyUserDetails myUserDetails = new MyUserDetails();
        if (utente != null) {
            myUserDetails.setUsername(s);
            myUserDetails.setId(utente.getIdUtente());
            myUserDetails.setPassword(utente.getPassword());
            myUserDetails.setIsAdmin(utente.getIsAdmin());
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
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
