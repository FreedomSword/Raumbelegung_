package SWT2.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsSeviceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }


    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/register", "/register_success", "/", "/process_register","/css/**", "/js/**", "/webjars/**", "/login", "/logout", "*", "/resources/**").permitAll() // here i allowed the CSS pages  so it dont get blocked by spring security
                .antMatchers("/").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/new").hasAnyAuthority("ADMIN")
                .antMatchers("/edit/**").hasAnyAuthority("ADMIN")
                .antMatchers("/delete/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login") // loginpage() method allows us to use a custom LOGIN page instead of the default one
                .defaultSuccessUrl("/index",true)
                .and()
                .logout()
                .logoutSuccessUrl("/");
    }
}