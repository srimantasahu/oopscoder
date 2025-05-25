public class ShardRouter {
    public DataSource getDataSource(int userId) {
        int shardId = userId % 4;
        return shardMap.get(shardId); // Return DataSource for shard
    }
}
