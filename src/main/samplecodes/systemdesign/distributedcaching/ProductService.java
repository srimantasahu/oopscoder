@Service
public class ProductService {

    @Autowired
    private RedisTemplate<String, Product> redisTemplate;

    @Autowired
    private ProductRepository productRepository;

    public Product getProductById(String id) {
        String key = "product:" + id;

        // Step 1: Check Redis cache
        Product product = redisTemplate.opsForValue().get(key);
        if (product != null) return product;

        // Step 2: Fallback to DB
        product = productRepository.findById(id).orElseThrow();

        // Step 3: Cache the result for 30 minutes
        redisTemplate.opsForValue().set(key, product, Duration.ofMinutes(30));
        return product;
    }
}
