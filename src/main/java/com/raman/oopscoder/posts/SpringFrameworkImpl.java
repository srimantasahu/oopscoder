package com.raman.oopscoder.posts;

@Autowired
private JdbcTemplate jdbcTemplate;

public List<User> findAllUsers() {
    return jdbcTemplate.query("SELECT * FROM users", new BeanPropertyRowMapper<>(User.class));
}

public class SpringFrameworkImpl {
}

@Component
public class OrderService {
    private final PaymentService paymentService;

    @Autowired    // Constructor injection (preferred)
    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.example.service.*.*(..))")
    public void logBefore() {
        System.out.println("Method execution started");
    }
}

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String greet() {
        return "Hello, Spring!";
    }
}

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
    }
}

public class User {
    @NotNull
    private String name;

    @Email
    private String email;
}

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .oauth2Login();
        return http.build();
    }
}

