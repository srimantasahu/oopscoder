// Subsystems
class UserValidator {
    public boolean isValid(User user) {
        return user.getEmail() != null && user.getPassword().length() >= 6;
    }
}

class UserRepository {
    public void save(User user) {
        System.out.println("User saved: " + user.getEmail());
    }
}

class EmailService {
    public void sendWelcomeEmail(User user) {
        System.out.println("Welcome email sent to: " + user.getEmail());
    }
}

// Model
class User {
    private String email;
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

// Facade
class UserRegistrationFacade {
    private UserValidator validator = new UserValidator();
    private UserRepository repository = new UserRepository();
    private EmailService emailService = new EmailService();

    public void registerUser(User user) {
        if (validator.isValid(user)) {
            repository.save(user);
            emailService.sendWelcomeEmail(user);
        } else {
            System.out.println("Invalid user details.");
        }
    }
}

// Client
public class Main {
    public static void main(String[] args) {
        User user = new User("john@example.com", "securePass");
        UserRegistrationFacade facade = new UserRegistrationFacade();
        facade.registerUser(user);
    }
}
