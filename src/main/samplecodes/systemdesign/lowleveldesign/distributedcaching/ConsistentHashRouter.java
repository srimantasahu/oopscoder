public class ConsistentHashRouter {
    private TreeMap<Integer, Cache> ring = new TreeMap<>();
    private List<Cache> nodes;

    public ConsistentHashRouter(List<Cache> nodes) {
        this.nodes = nodes;
        for (Cache node : nodes) {
            int hash = node.hashCode();
            ring.put(hash, node);
        }
    }

    public Cache getNode(String key) {
        int hash = key.hashCode();
        SortedMap<Integer, Cache> tail = ring.tailMap(hash);
        if (!tail.isEmpty()) return tail.get(tail.firstKey());
        return ring.firstEntry().getValue();
    }
}
