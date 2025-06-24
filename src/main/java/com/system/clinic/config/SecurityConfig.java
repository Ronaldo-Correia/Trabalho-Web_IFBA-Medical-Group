package com.system.clinic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.system.clinic.repository.PapelRepository;
import com.system.clinic.service.impl.CustomUserDetailService;

//import ifba.saj.demo.petshop.domain.entity.PapelEntity;
//import ifba.saj.demo.petshop.repository.PapelRepository;
//import ifba.saj.demo.petshop.service.impl.CustomUserDetailService;
import lombok.RequiredArgsConstructor;

//import static ifba.saj.demo.petshop.consts.RequestPathConstants.VETERINARIO;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailService userDetailsService;
    private final PapelRepository papelRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/login",
                                "/cadastro",
                                "/cadastroSucesso",
                                "/",
                                "/img/**",
                                "/js/**",
                                "/css/**",
                                "/cadastroPaciente",
                                "/paciente/salvar",
                                "/listarProfissional",
                                "/cadastroProfissional/**",
                                "/sucesso/**",
                                "/salvarProfissional",
                                "/editarProfissional/**",
                                "/error",
                                "/salvar/**",
                                "/h2-console/**",
                                "/esqueci-senha",
                                "/solicitar-redefinicao",
                                "/redefinir-senha"

                        ).permitAll()
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/login?error")
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll())
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions().disable())
                .build();
    }
    

  

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}