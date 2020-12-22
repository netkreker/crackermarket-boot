package com.crackermarket.app.config;

import com.crackermarket.app.security.Permission;
import com.crackermarket.app.user.entities.Role;
import com.crackermarket.app.user.enumerations.LevelAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    private final UserDetailsService userDetailsService;
//
//    @Autowired
//    public SecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic().and()
                .authorizeRequests()
                .antMatchers("/").permitAll()

                // CATEGORIES ACCESS

//                .antMatchers(HttpMethod.GET, "/category/**").hasAuthority(Permission.READ.getPermission())
//                .antMatchers(HttpMethod.POST, "/category/**").hasAuthority(Permission.WRITE.getPermission())
//                .antMatchers(HttpMethod.DELETE, "/category/**").hasAuthority(Permission.WRITE.getPermission())

                // PRODUCTS ACCESS

                .antMatchers(HttpMethod.GET, "/product/**").hasAuthority(Permission.READ.getPermission())
                .antMatchers(HttpMethod.POST, "/product/**").hasAuthority(Permission.WRITE.getPermission())
                .antMatchers(HttpMethod.DELETE, "/product/**").hasAuthority(Permission.WRITE.getPermission())

                // PARAMETER ACCESS

                .antMatchers(HttpMethod.GET, "/parameter/**").hasAuthority(Permission.READ.getPermission())
                .antMatchers(HttpMethod.POST, "/parameter/**").hasAuthority(Permission.WRITE.getPermission())
                .antMatchers(HttpMethod.DELETE, "/parameter/**").hasAuthority(Permission.WRITE.getPermission());


//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/auth/login").permitAll()
//                .defaultSuccessUrl("/auth/success")
//                .and()
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))
//                .invalidateHttpSession(true)
//                .clearAuthentication(true)
//                .deleteCookies("JSESSIONID")
//                .logoutSuccessUrl("/auth/login");

    }


    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("admin")
                        .password(passwordEncoder().encode("admin"))
                        .authorities(Role.ADMIN.getGrantedAuthorities())
                        .build(),
                User.builder()
                        .username("user")
                        .password(passwordEncoder().encode("user"))
                        .authorities(Role.USER.getGrantedAuthorities())
                        .build()
        );
    }


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(daoAuthenticationProvider());
//    }
//
//    @Bean
//    protected DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
//        return daoAuthenticationProvider;
//    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
