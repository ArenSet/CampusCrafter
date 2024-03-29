package campusCrafter.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MyUserDetailsService userDetailsService;


    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers(HttpMethod.GET, "/api/login/**")
                .antMatchers("user/registration/**")
                .antMatchers("user/forgot/**")
                .antMatchers("/swagger-ui/**",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/v3/api-docs/**",
                        "/crm-ws/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.cors().disable().csrf().disable().headers().frameOptions().disable()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .anonymous()
                .and()
                .authorizeRequests()
                .antMatchers("/api/**").permitAll()
                .antMatchers(HttpMethod.GET,
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/v3/api-docs/**",
                        "/crm-ws/**").permitAll()
                .anyRequest()
                .authenticated();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }


    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}