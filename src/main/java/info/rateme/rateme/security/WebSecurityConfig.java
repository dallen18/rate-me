package info.rateme.rateme.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private UserDetailsService userDetailsService;

    @Autowired
    public void setUserDetailsService(UserDetailServiceImpl userDetailsService){
        this.userDetailsService = userDetailsService;
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
                .usernameParameter("email")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout").permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }


}
