class UserMemento {
    String state;

    UserMemento(String state) {
        this.state = state;
    }
}

class UserProfile {
    String state;

    public void setState(String s) {
        this.state = s;
    }

    public UserMemento save() {
        return new UserMemento(state);
    }

    public void restore(UserMemento m) {
        this.state = m.state;
    }
}
