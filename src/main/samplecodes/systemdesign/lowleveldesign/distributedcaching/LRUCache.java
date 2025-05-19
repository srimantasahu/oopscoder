public class LRUCache implements Cache {
    private final int capacity;
    private final Map<String, CacheEntry> cache;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<>(capacity, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<String, CacheEntry> eldest) {
                return size() > LRUCache.this.capacity;
            }
        };
    }

    public synchronized String get(String key) {
        CacheEntry entry = cache.get(key);
        if (entry == null || entry.isExpired()) {
            cache.remove(key);
            return null;
        }
        return entry.value;
    }

    public synchronized void put(String key, String value, long ttlMillis) {
        cache.put(key, new CacheEntry(value, ttlMillis));
    }

    public synchronized void delete(String key) {
        cache.remove(key);
    }
}
