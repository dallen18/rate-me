package info.rateme.rateme.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .antMatchers("/","/user/**").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/user/**").anonymous()
                .and()
                .authorizeRequests()
                .antMatchers( "/view-users/**").hasRole("ADMIN")
                .and()
                .authorizeRequests()
                .antMatchers("/movie/**", "/review/**","/display-movies/**","/display-reviews/**").hasAnyRole("ADMIN", "USER")
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/display-movies",true).permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout").permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .and()
                .withUser("admin")
                .password("admin")
                .password(passwordEncoder().encode("password"))
                .roles("ADMIN");
    }


}
