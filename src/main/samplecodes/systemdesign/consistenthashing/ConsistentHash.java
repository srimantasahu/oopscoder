class ConsistentHash<T> {
    private final SortedMap<Integer, T> ring = new TreeMap<>();
    private final int virtualNodes;

    public ConsistentHash(List<T> nodes, int virtualNodes) {
        this.virtualNodes = virtualNodes;
        for (T node : nodes) {
            add(node);
        }
    }

    public void add(T node) {
        for (int i = 0; i < virtualNodes; i++) {
            int hash = (node.toString() + i).hashCode();
            ring.put(hash, node);
        }
    }

    public T get(String key) {
        if (ring.isEmpty()) return null;
        int hash = key.hashCode();
        SortedMap<Integer, T> tail = ring.tailMap(hash);
        hash = tail.isEmpty() ? ring.firstKey() : tail.firstKey();
        return ring.get(hash);
    }
}
