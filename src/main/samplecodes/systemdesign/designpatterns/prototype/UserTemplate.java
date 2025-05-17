public class UserTemplate implements Cloneable {
    private String role = "user";

    public UserTemplate clone() throws CloneNotSupportedException {
        return (UserTemplate) super.clone();
    }
}
