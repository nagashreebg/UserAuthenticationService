package com.scaler.userauthenticationservice.security;

import com.scaler.userauthenticationservice.oauth2.CustomAuthenticationProvider;
import com.scaler.userauthenticationservice.oauth2.CustomWebAuthenticationDetailsSource;
import com.scaler.userauthenticationservice.repositories.UserRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.crypto.SecretKey;
import java.util.UUID;

//@Configuration
//@EnableWebSecurity
//@Primary

public class SpringSecurity {

//    @Autowired
//    private AuthenticationDetailsSource authenticationDetailsSource;
//

    @Autowired
    PasswordEncoderConfig passwordEncoderConfig;

    @Autowired
    private CustomUserDetailsService userDetailsService;


    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    //@Bean
    //SecurityFilterChain getSecurityFilterChain(HttpSecurity httpSecurity) throws Exception
//    {
//        httpSecurity.cors().disable();
//        httpSecurity.csrf().disable();
//        httpSecurity.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
//        return httpSecurity.build();
//    }
//    {
//        httpSecurity
          //      .authenticationProvider(customAuthenticationProvider) // Register CustomAuthenticationProvider
//                .authorizeRequests()
//                .requestMatchers("/user/update/2fa").authenticated()
//                .anyRequest().permitAll()
//                .and()
//                .formLogin()
//                .loginProcessingUrl("/login")
//                .authenticationDetailsSource(new CustomWebAuthenticationDetailsSource())
//                .permitAll()
//                .and()
//                .csrf().disable()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
//                //.and()
////                .authorizeHttpRequests(authorize ->
////                        authorize
////                                .requestMatchers("/login", "/public/**").permitAll()
////                                .requestMatchers("/user/update/2fa","/registrationConfirm").authenticated()
////                                .anyRequest().authenticated()
////                );
//                 // Temporarily disable for testing
//
//        return httpSecurity.build();
//    }

    @Bean
    SecretKey secretKey()
    {
        MacAlgorithm algorithm = Jwts.SIG.HS256;
        SecretKey secretKey = algorithm.key().build();
        return secretKey;
    }

//    @Bean
//    public DaoAuthenticationProvider authProvider() {
//        CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider(userDetailsService, passwordEncoderConfig.passwordEncoder());
//        return authProvider;
//    }

//    @Bean
//    public RegisteredClientRepository registeredClientRepository() {
//        RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
//                .clientId("oidc-client")
//                .clientSecret("{noop}secret")
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//                .redirectUri("http://127.0.0.1:9000/login/oauth2/code/oidc-client")
//                .postLogoutRedirectUri("http://127.0.0.1:9000/")
//                .scope(OidcScopes.OPENID)
//                .scope(OidcScopes.PROFILE)
//                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
//                .build();
//
//        return new InMemoryRegisteredClientRepository(oidcClient);
//    }

}
