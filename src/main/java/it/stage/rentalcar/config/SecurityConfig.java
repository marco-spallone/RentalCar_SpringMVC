package it.stage.rentalcar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.context.request.RequestContextListener;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }

    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
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
            "/freeCars/**",
            "/editReservation/**",
            "/deleteReservation/**"
    };

    @Override
    public void configure(final HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/login/form").permitAll()
                .antMatchers("/customers/userProfile").access("hasAnyAuthority('ADMIN', 'CUSTOMER')")
                .antMatchers(ADMIN_MATCHER).access("hasAuthority('ADMIN')")
                .antMatchers(CUSTOMER_MATCHER).access("hasAuthority('CUSTOMER')").and()
                .formLogin().loginPage("/login/form").loginProcessingUrl("/login")
                .usernameParameter("username").passwordParameter("password")
                .failureUrl("/login/form?error").defaultSuccessUrl("/index")
                .and().exceptionHandling().accessDeniedPage("/login/form?forbidden")
                .and().logout().logoutUrl("/login/form?logout").addLogoutHandler(new SecurityContextLogoutHandler())
                .and().csrf().disable();
    }
}
