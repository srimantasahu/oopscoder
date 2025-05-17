for (int i = 0; i < 100; i++) {
int hash = hash("ServerA" + i);
    ring.put(hash, "ServerA");
}
