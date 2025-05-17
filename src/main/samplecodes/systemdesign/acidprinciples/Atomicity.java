try (Connection conn = dataSource.getConnection()) {
    conn.setAutoCommit(false); // Begin transaction

    try (PreparedStatement debit = conn.prepareStatement("UPDATE users SET balance = balance - ? WHERE id = ?")) {
        debit.setDouble(1, 100);
        debit.setInt(2, userId);
        debit.executeUpdate();
    }

    try (PreparedStatement order = conn.prepareStatement("INSERT INTO orders (user_id, amount) VALUES (?, ?)")) {
        order.setInt(1, userId);
        order.setDouble(2, 100);
        order.executeUpdate();
    }

    conn.commit(); // All success, commit transaction
} catch (Exception e) {
    conn.rollback(); // Something failed, roll back
    e.printStackTrace();
}
