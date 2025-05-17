public interface User {
    void register();
}

public class AdminUser implements User {
    public void register() { /* logic */ }
}

public class GuestUser implements User {
    public void register() { /* logic */ }
}

public class UserFactory {
    public static User create(String type) {
        return switch (type) {
            case "admin" -> new AdminUser();
            case "guest" -> new GuestUser();
            default -> throw new IllegalArgumentException("Unknown type");
        };
    }
}
