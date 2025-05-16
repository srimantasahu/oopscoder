public interface AuthOperations {
    void login();

    void logout();
}

public interface AdminOperations {
    void deleteUser();

    void resetPassword();
}

public interface RegistrationOperations {
    void register();
}