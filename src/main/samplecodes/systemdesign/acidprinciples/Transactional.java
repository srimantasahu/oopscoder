@Transactional
public void placeOrder(User user, double amount) {
    userRepo.debit(user.getId(), amount);
    orderRepo.createOrder(user.getId(), amount);
}
