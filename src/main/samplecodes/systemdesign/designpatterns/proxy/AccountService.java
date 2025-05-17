interface AccountService {
    void accessAccount();
}

class RealAccountService implements AccountService {
    public void accessAccount() {
        System.out.println("Accessing account");
    }
}

class AuthProxy implements AccountService {
    private final AccountService service;
    private final boolean isAuthenticated;

    public AuthProxy(AccountService service, boolean isAuthenticated) {
        this.service = service;
        this.isAuthenticated = isAuthenticated;
    }

    public void accessAccount() {
        if (isAuthenticated) service.accessAccount();
        else System.out.println("Access denied");
    }
}
