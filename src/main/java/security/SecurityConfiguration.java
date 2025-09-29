package security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    System.out.println("=== SECURITY CONFIG CARICATO ===");
	    http
	        .csrf(csrf -> csrf.disable())
	    //.csrf(csrf -> csrf.ignoringRequestMatchers( "/api/inserimento-ticket"))
	        .cors(cors -> cors.disable())
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/api/**").permitAll()
	            .requestMatchers("/css/**", "/js/**", "/webjars/**","/images/**").permitAll()
	            .requestMatchers("/login", "/logout").permitAll()
	            .anyRequest().authenticated()
	        )
	        .formLogin(login -> 
	            login.loginPage("/login")
	                .defaultSuccessUrl("/dashboard")
	                .permitAll()
	        );
	 //   http.csrf(AbstractHttpConfigurer::disable); 
	    return http.build();
	}
	
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//	    System.out.println("=== SECURITY CONFIG CARICATO ===");
//	    http
//	        .csrf(csrf -> csrf.disable())
//	        .authorizeHttpRequests(auth -> auth
//	            .requestMatchers("/api/**").permitAll()
//	            .requestMatchers("/css/**", "/js/**", "/webjars/**","/images/**").permitAll()
//	            .requestMatchers("/login", "/logout").permitAll()
//	            .requestMatchers("/api/**").permitAll()
//	            .requestMatchers("/css/**", "/js/**", "/webjars/**","/images/**", "/api/**").permitAll()
//	            .requestMatchers(HttpMethod.POST, "/api/inserimento-ticket").permitAll()
//	            .anyRequest().authenticated()
//	        )
//	        .formLogin(login -> 
//	            login.loginPage("/login")
//	                .defaultSuccessUrl("/dashboard")
//	                .permitAll()
//	        )
//	        .logout(logout -> 
//	            logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//	                .logoutSuccessUrl("/login")
//	                .permitAll()
//	        );
//	    return http.build();
//	}

//	@Bean
//    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//       	.csrf(csrf -> csrf.disable())
//           .cors(cors -> cors.disable())
//            .authorizeHttpRequests(request -> 
//                request
//                	//queste sono rotte accessibili a tutti
//                	.requestMatchers("/dashboard").hasAnyAuthority("ADMIN", "UTENTE")
//                	//.requestMatchers("/login", "/logout", "/SpringBlog/preview","/utente/create").permitAll()
//                	.requestMatchers("/css/**", "/js/**", "/webjars/**","/images/**", "/api/**").permitAll()
//                	//.requestMatchers("/api/inserimento-ticket/**").hasAnyAuthority("ADMIN")
//                	
//                	//questo vuol dire che TUTTE le altre devono essere sotto autenticazione
//                    .anyRequest().authenticated())
//            .formLogin(login -> 
//                login.loginPage("/login") // Pagina di login personalizzata
////                    .loginProcessingUrl("/authentication") // URL di elaborazione del login
//                    .defaultSuccessUrl("/dashboard") // Reindirizza dopo il successo del login
//                    .permitAll())
//            .logout(logout -> 
//                logout
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                    .logoutSuccessUrl("/SpringBlog/preview")
//                    .permitAll()); // Imposta il logout di default
//
//        return http.build();
//    }
	
//	@Bean
//	DatabaseUserDetailsService userDetailsService() {
//		return new DatabaseUserDetailsService();
//	}
//	
//	@Bean
//	DaoAuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//		authProvider.setUserDetailsService(userDetailsService());
//		authProvider.setPasswordEncoder(new BCryptPasswordEncoder(10));
//		return authProvider;
//	}
//
//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
//			throws Exception {
//		return authenticationConfiguration.getAuthenticationManager();
//	}
}
