public class SessionManager {
    private static SessionManager instance = null;

    private SessionManager() {
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) instance = new SessionManager();
        return instance;
    }

    public void login(String user) { /* store session */ }
}
