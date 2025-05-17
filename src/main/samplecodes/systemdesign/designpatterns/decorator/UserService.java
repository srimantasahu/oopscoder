interface UserService {
    void registerUser(String email);
}

class BasicUserService implements UserService {
    public void registerUser(String email) {
        System.out.println("Registered " + email);
    }
}

class LoggingUserService implements UserService {
    private final UserService service;

    public LoggingUserService(UserService service) {
        this.service = service;
    }

    public void registerUser(String email) {
        service.registerUser(email);
        System.out.println("Log: Registered user " + email);
    }
}
