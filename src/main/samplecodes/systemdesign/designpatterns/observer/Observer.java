interface Observer {
    void update(String event);
}

class EmailNotifier implements Observer {
    public void update(String event) {
        System.out.println("Email: " + event);
    }
}

class UserRegistrationSubject {
    private final List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void registerUser(String user) {
        for (Observer o : observers) o.update("User registered: " + user);
    }
}
