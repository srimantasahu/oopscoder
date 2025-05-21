@Service
public class ProductService {
    @Autowired
    private RedisTemplate<String, Product> redisTemplate;

    @Autowired
    private ProductRepository productRepository;

    public Product getProductById(String id) {
        String key = "product:" + id;

        // Check cache first
        Product product = redisTemplate.opsForValue().get(key);
        if (product != null) return product;

        // Fallback to DB
        product = productRepository.findById(id).orElseThrow();
        redisTemplate.opsForValue().set(key, product, Duration.ofMinutes(30));
        return product;
    }
}
