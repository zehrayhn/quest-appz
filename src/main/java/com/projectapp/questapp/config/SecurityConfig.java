package com.projectapp.questapp.config;

import com.projectapp.questapp.security.JwtAuthenticationEntryPoint;
import com.projectapp.questapp.security.JwtAuthenticationFilter;
import com.projectapp.questapp.services.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig  {
    //@Autowired
    private UserDetailsServiceImpl userDetailsService;
    //@Autowired
    private JwtAuthenticationEntryPoint handler;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService,JwtAuthenticationEntryPoint handler){this.userDetailsService=userDetailsService;
        this.handler=handler;
        this.userDetailsService = userDetailsService;
   }

//filter istediğimizde yeni bir filter döner
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();

    }

    //security den authenticationmanager alıyoruz.

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



    //tüm metodlara izin veriyoruz
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);


        return new CorsFilter(source);
    }

    @Bean
   public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
       httpSecurity
                .cors() //cors filter ı ekler eğer bir bean varsa cors filter adında onu ekler. yukrıda cors filter ımızı oluşturduk
                .and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(handler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**")//bu tarz bi linke gitmek isterse user lar bu link için token istemeyeceğiz o yüzden permitAll dedik.
                .permitAll()
                .anyRequest().authenticated();

    //    usernamePasswordAuthenticationfilter dan önce jwtAuthenticationFilter eklemiş olduk.
        httpSecurity.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }


}
