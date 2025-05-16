package com.raman.oopscoder.posts.systemdesign.solidprinciples.ocp;

public interface AuthStrategy {
    boolean authenticate(String username, String password);
}

public class BasicAuthStrategy implements AuthStrategy {
    public boolean authenticate(String username, String password) {
        // basic auth logic
    }
}

public class OAuthStrategy implements AuthStrategy {
    public boolean authenticate(String username, String password) {
        // oauth logic
    }
}

public class AuthService {
    private final AuthStrategy strategy;

    public AuthService(AuthStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean authenticate(String username, String password) {
        return strategy.authenticate(username, password);
    }
}
