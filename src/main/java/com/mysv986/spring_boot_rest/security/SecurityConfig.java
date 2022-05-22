package com.mysv986.spring_boot_rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
        http
            // AUTHORIZE
            .authorizeRequests()
                .mvcMatchers("/hello/**")
                    .permitAll()
                .mvcMatchers(HttpMethod.PUT, "/user/**")
                    .hasRole("ADMIN")
                .mvcMatchers(HttpMethod.PATCH, "/user/**")
                    .hasRole("ADMIN")
                .mvcMatchers(HttpMethod.DELETE, "/user/**")
                    .hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST, "/user")
                    .hasRole("ADMIN")
                .mvcMatchers(HttpMethod.GET, "/user/list")
                    .hasRole("USER")
                .mvcMatchers(HttpMethod.GET, "/user/**")
                    .hasRole("USER")
                .mvcMatchers(HttpMethod.POST, "/password")
                    .hasRole("USER")
                .mvcMatchers(HttpMethod.PUT, "/place/**")
                    .hasRole("ADMIN")
                .mvcMatchers(HttpMethod.DELETE, "/place/**")
                    .hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST, "/place")
                    .hasRole("ADMIN")
                .mvcMatchers(HttpMethod.GET, "/place/**")
                    .hasRole("USER")
                .anyRequest()
                    .authenticated()
            .and()
            // EXCEPTION
            .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler())
            .and()
            // LOGIN
            .formLogin()
                .loginProcessingUrl("/login").permitAll()
                    .usernameParameter("account")
                    .passwordParameter("password")
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler())
            .and()
            .cors()
                .configurationSource(this.corsConfigurationSource())
            .and()
            // LOGOUT
            .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(logoutSuccessHandler())
                //.addLogoutHandler(new CookieClearingLogoutHandler())
            .and()
             // CSRF
            .csrf()
                //.disable()
                .ignoringAntMatchers("/login")
                .csrfTokenRepository(new CookieCsrfTokenRepository())
            .and()
            // SESSION
            .sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false);
    // @formatter:on
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth,
      @Qualifier("simpleUserDetailsService") UserDetailsService userDetailsService, PasswordEncoder passwordEncoder)
      throws Exception {
    auth.eraseCredentials(true).userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  AuthenticationEntryPoint authenticationEntryPoint() {
    return new SimpleAuthenticationEntryPoint();
  }

  AccessDeniedHandler accessDeniedHandler() {
    return new SimpleAccessDeniedHandler();
  }

  AuthenticationSuccessHandler authenticationSuccessHandler() {
    return new SimpleAuthenticationSuccessHandler();
  }

  AuthenticationFailureHandler authenticationFailureHandler() {
    return new SimpleAuthenticationFailureHandler();
  }

  LogoutSuccessHandler logoutSuccessHandler() {
    return new HttpStatusReturningLogoutSuccessHandler();
  }

  private CorsConfigurationSource corsConfigurationSource() {

    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.addAllowedMethod("GET");
    corsConfiguration.addAllowedMethod("PUT");
    corsConfiguration.addAllowedMethod("POST");
    corsConfiguration.addAllowedMethod("PATCH");
    corsConfiguration.addAllowedMethod("DELETE");
    corsConfiguration.addAllowedOrigin("http://localhost:3000");
    corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
    corsConfiguration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
    corsSource.registerCorsConfiguration("/login", corsConfiguration);
    corsSource.registerCorsConfiguration("/logout", corsConfiguration);
    corsSource.registerCorsConfiguration("/user", corsConfiguration);
    corsSource.registerCorsConfiguration("/user/list", corsConfiguration);
    corsSource.registerCorsConfiguration("/user/**", corsConfiguration);
    corsSource.registerCorsConfiguration("/password", corsConfiguration);
    corsSource.registerCorsConfiguration("/place", corsConfiguration);
    corsSource.registerCorsConfiguration("/place/**", corsConfiguration);

    return corsSource;
  }
}
