public class CacheWithDBFallback implements Cache {
    private final Cache localCache;
    private final DatabaseClient db;

    public CacheWithDBFallback(Cache localCache, DatabaseClient db) {
        this.localCache = localCache;
        this.db = db;
    }

    @Override
    public String get(String key) {
        String value = localCache.get(key);
        if (value == null) {
            value = db.get(key);
            if (value != null) {
                localCache.put(key, value, 60_000); // cache for 1 min
            }
        }
        return value;
    }

    @Override
    public void put(String key, String value, long ttlMillis) {
        db.put(key, value);
        localCache.put(key, value, ttlMillis);
    }

    @Override
    public void delete(String key) {
        db.delete(key);
        localCache.delete(key);
    }
}
