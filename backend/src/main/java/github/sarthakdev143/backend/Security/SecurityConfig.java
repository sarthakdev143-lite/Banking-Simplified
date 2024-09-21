// package github.sarthakdev143.backend.Security;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

//     @Bean
//     public static PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//                 .authorizeHttpRequests((requests) -> requests

//                         .requestMatchers("/sign-up/**").permitAll()
//                         .requestMatchers("/login/**").permitAll()

//                         .requestMatchers("/user/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
//                         .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
        
//                         .anyRequest().authenticated())

//                 .formLogin((form) -> form
//                         .loginPage("/login")
//                         .loginProcessingUrl("/login")
//                         .defaultSuccessUrl("/")
//                         .usernameParameter("email")
//                         .passwordParameter("password")
//                         .permitAll())
//                 .logout(obj -> obj.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                         .logoutSuccessUrl("/")
//                         .invalidateHttpSession(true).deleteCookies("JESSIONID"));
//         http.exceptionHandling((hi) -> hi.accessDeniedPage("/access-denied"));
//         System.out.println("Hello from Security Config");
//         return http.build();
//     }

//     @Bean
//     public WebSecurityCustomizer webSecurityCustomizer() {
//         return (web) -> web.ignoring().requestMatchers("/");
//     }
// }
