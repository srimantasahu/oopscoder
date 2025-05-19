public class CacheEntry {
    String value;
    long expiryTime;

    public CacheEntry(String value, long ttlMillis) {
        this.value = value;
        this.expiryTime = System.currentTimeMillis() + ttlMillis;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}
