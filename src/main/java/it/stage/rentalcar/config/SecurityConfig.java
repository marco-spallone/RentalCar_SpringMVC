package it.stage.rentalcar.config;

import it.stage.rentalcar.domain.Utente;
import it.stage.rentalcar.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.context.request.RequestContextListener;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UtenteService utenteService;

    public SecurityConfig(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService(){
        User.UserBuilder users = User.builder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        List<Utente> customers = utenteService.getCustomers(false);
        for (Utente u:customers) {
            manager.createUser(users.username(u.getUsername()).password(new BCryptPasswordEncoder().encode(u.getPassword()))
                    .roles("CUSTOMER").build());
        }
        manager.createUser(users.username(utenteService.getUserFromId(1).getUsername()).password(new BCryptPasswordEncoder().encode(utenteService.getUserFromId(1).getPassword()))
                .roles("ADMIN").build());
        return manager;
    }

    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    private static final String[] ADMIN_CLIENTI_MATCHER={"customers"};

    @Override
    public void configure(final HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers(ADMIN_CLIENTI_MATCHER).access("hasRole('ADMIN')").and()
                .formLogin().loginPage("/login/form").loginProcessingUrl("/login")
                .usernameParameter("username").passwordParameter("password")
                .failureUrl("/login/form?error").defaultSuccessUrl("/index")
                .and().exceptionHandling().accessDeniedPage("/login/form?forbidden")
                .and().logout().logoutUrl("/login/form?logout")
                .and().csrf().disable();
    }
}
