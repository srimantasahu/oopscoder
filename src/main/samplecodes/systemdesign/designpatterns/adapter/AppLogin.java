interface AppLogin {
    void login(String username, String password);
}

class GoogleAuth {
    void authenticate(String token) { /* logic */ }
}

class GoogleAuthAdapter implements AppLogin {
    GoogleAuth google = new GoogleAuth();

    public void login(String username, String password) {
        String token = username + ":" + password;
        google.authenticate(token);
    }
}
