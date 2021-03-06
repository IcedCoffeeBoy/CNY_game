package com.dsta.CNYBackend.shared.security;


import com.dsta.CNYBackend.user.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String[] ALLOWED_ORIGINS = {
            "http://perrywzm.github.io", "https://perrywzm.github.io",
            "https://eitcny2020.z23.web.core.windows.net", "https://eitcnyproj.z23.web.core.windows.net", "https://eitcny2020.azureedge.net",
            "http://eitcnyadmin.z23.web.core.windows.net", "https://eitcnyadmin.z23.web.core.windows.net",
            "http://localhost:8080", "http://localhost:3000", "http://localhost:4200"
    };
    public static final String[] ALLOWED_API = {
            "/authenticate", "/topic/*", "/game/**", "/actuator/**",
            "/api/user/create", "/api/question/*", "/api/poll/*", "/api/game/state", "/api/game/participants", "/api/game/rank"
    };
    public static final String[] ALLOWED_SWAGGER_RESOURCES = {"/swagger-resources/**", "/swagger-ui.html", "/v2/api-docs", "/webjars/**"};
    public static final String[] ALLOWED_RESOURCES = {"/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html", "/**/*.css", "/**/*.js"};
    public static final String[] ALL_API = {"/api/question/*", "/api/game/*", "/api/user/*", "/api/poll/*", "/authenticate", "/topic/*", "/game/*", "/actuator"};

    @Value("${jwt.protect:false}")
    private Boolean isProtected;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private UsersService usersService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(usersService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(ALLOWED_ORIGINS)
                        .allowCredentials(true);
            }

            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addRedirectViewController("/", "/swagger-ui.html");
            }


            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("swagger-ui.html")
                        .addResourceLocations("classpath:/META-INF/resources/");

                registry.addResourceHandler("/webjars/**")
                        .addResourceLocations("classpath:/META-INF/resources/webjars/");
            }
        };
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        if (isProtected) {
            System.out.println("JWT protection is enable");
            httpSecurity
                    .headers().frameOptions().disable()
                    .and()
                    .cors().and()
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers(ALLOWED_API).permitAll().filterSecurityInterceptorOncePerRequest(false)
                    .antMatchers(ALLOWED_RESOURCES).permitAll().filterSecurityInterceptorOncePerRequest(false)
                    // all other requests need to be authenticated
                    .anyRequest().authenticated()

                    // make sure we use stateless session; session won't be used to
                    // store user's state.
//                .and()
//                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        } else {
            System.out.println("JWT protection is disable");
            httpSecurity
                    .headers().frameOptions().disable()
                    .and()
                    .cors().and()
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers(ALL_API).permitAll()
                    .antMatchers(ALLOWED_RESOURCES)
                    .permitAll()
                    // all other requests need to be authenticated
                    .anyRequest().authenticated()

                    // make sure we use stateless session; session won't be used to
                    // store user's state.
//                .and()
//                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }

        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/game/**");
        web.ignoring().antMatchers(ALLOWED_SWAGGER_RESOURCES);
    }
}
