public class ProductCacheChecker {
    private static final BloomFilter<Integer> productFilter =
            BloomFilter.create(Funnels.integerFunnel(), 1_000_000, 0.01);

    public static void main(String[] args) {
        // Insert product IDs
        productFilter.put(101);
        productFilter.put(102);

        // Check existence
        if (productFilter.mightContain(101)) {
            System.out.println("Might exist → Query cache");
        } else {
            System.out.println("Definitely doesn't exist → Skip cache");
        }
    }
}
