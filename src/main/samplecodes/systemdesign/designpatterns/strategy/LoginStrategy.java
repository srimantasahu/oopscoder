interface LoginStrategy {
    void login(String input);
}

class EmailLogin implements LoginStrategy {
    public void login(String input) {
        System.out.println("Login via email");
    }
}

class OTPLogin implements LoginStrategy {
    public void login(String input) {
        System.out.println("Login via OTP");
    }
}

class LoginContext {
    private LoginStrategy strategy;

    public void setStrategy(LoginStrategy strategy) {
        this.strategy = strategy;
    }

    public void login(String input) {
        strategy.login(input);
    }
}
