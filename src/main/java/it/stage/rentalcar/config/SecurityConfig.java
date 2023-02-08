package it.stage.rentalcar.config;

import it.stage.rentalcar.domain.Utente;
import it.stage.rentalcar.service.UtenteService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.context.request.RequestContextListener;

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
        CustomDetailsManager userDetailsManager = new CustomDetailsManager(utenteService);
        List<Utente> customers = utenteService.getCustomers(false);
        for (Utente u:customers) {
            userDetailsManager.loadUserByUsername(u.getUsername());
        }
        return userDetailsManager;
    }

    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    private static final String[] ADMIN_MATCHER={
            "/customers/**",
            "/editCar/**",
            "/addCar/**",
            "/deleteCar/**",
            "/approveReservation/**",
            "/declineReservation/**",
            "/addCustomer/**",
            "/editCustomer/**",
            "/deleteCustomer/**"
    };

    private static final String[] CUSTOMER_MATCHER={
            "/addReservation/**",
            "/freeAuto/**",
            "/editReservation/**",
            "/deleteReservation/**"
    };

    @Override
    public void configure(final HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/login/form").permitAll()
                .antMatchers("/customers/userProfile").permitAll()
                .antMatchers(ADMIN_MATCHER).access("hasRole('ADMIN')")
                .antMatchers(CUSTOMER_MATCHER).access("hasRole('CUSTOMER')").and()
                .formLogin().loginPage("/login/form").loginProcessingUrl("/login")
                .usernameParameter("username").passwordParameter("password")
                .failureUrl("/login/form?error").defaultSuccessUrl("/index")
                .and().exceptionHandling().accessDeniedPage("/login/form?forbidden")
                .and().logout().logoutUrl("/login/form?logout")
                .and().csrf().disable();
    }
}
