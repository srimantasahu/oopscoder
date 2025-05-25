@Bean
@LoadBalanced
public RestTemplate restTemplate() {
    return new RestTemplate();
}

// Call a Service by Name
@Autowired
private RestTemplate restTemplate;

public Order getOrder(String orderId) {
    return restTemplate.getForObject(
            "http://order-service/orders/" + orderId,
            Order.class
    );
}
