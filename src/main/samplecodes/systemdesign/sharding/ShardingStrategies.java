// Range-Based Sharding
int shardId = (userId <= 10000) ? 1 : 2;
DataSource ds = shardManager.getDataSource(shardId);

// Hash-Based Sharding
int shardId = userId.hashCode() % totalShards;
DataSource ds = shardManager.getDataSource(shardId);
