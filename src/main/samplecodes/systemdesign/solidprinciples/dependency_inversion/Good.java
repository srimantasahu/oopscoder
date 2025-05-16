public interface UserRepository {
    void save(User user);
}

public class MySQLUserRepository implements UserRepository {
    public void save(User user) {
        // MySQL logic
    }
}

public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void registerUser(User user) {
        repository.save(user);
    }
}
