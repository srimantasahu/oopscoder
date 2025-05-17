conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
conn.setAutoCommit(false);

try (PreparedStatement checkEmail = conn.prepareStatement("SELECT * FROM users WHERE email = ?")) {
    checkEmail.setString(1, "oops@coder.com");
    ResultSet rs = checkEmail.executeQuery();

    if (!rs.next()) {
        try (PreparedStatement insertUser = conn.prepareStatement("INSERT INTO users (email) VALUES (?)")) {
            insertUser.setString(1, "oops@coder.com");
            insertUser.executeUpdate();
        }
    }

    conn.commit();
} catch (SQLException e) {
    conn.rollback();
    e.printStackTrace();
}
