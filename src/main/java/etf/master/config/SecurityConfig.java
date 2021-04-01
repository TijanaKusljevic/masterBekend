package etf.master.config;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import etf.master.filter.AuthenticationFilter;
import etf.master.filter.AuthorizationFilter;
import etf.master.service.ApplicationUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private ApplicationUserDetailsService userDetailsService;
   // private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public SecurityConfig(ApplicationUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
       // this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
         
        return authProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
        		.antMatchers("/images/**").anonymous()
                .antMatchers(HttpMethod.POST, "/api/signup").permitAll()
                .antMatchers(HttpMethod.GET, "/api//guides").hasAnyAuthority("USER", "GUIDE", "ADMIN")
                .antMatchers(HttpMethod.GET, "/api/guide/{id}").hasAnyAuthority("USER", "GUIDE", "ADMIN")
            
                //.antMatchers(HttpMethod.GET, "/tour/allTours").hasAnyAuthority("GUIDE")
                .anyRequest().authenticated()
                .and()
                .addFilter(new AuthenticationFilter(authenticationManager()))
                .addFilter(new AuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
    
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
    	List<String> list = new ArrayList<String>();
    	list.add("token");
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration c = new CorsConfiguration().applyPermitDefaultValues();
        c.addAllowedMethod(HttpMethod.DELETE);
        c.addAllowedMethod(HttpMethod.POST);
        c.addAllowedMethod(HttpMethod.GET);
        c.setExposedHeaders(list);
        source.registerCorsConfiguration("/**", c);
        return source;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

}
