public class User {
    private final String email;
    private final String password;
    private final String avatar;

    private User(Builder builder) {
        this.email = builder.email;
        this.password = builder.password;
        this.avatar = builder.avatar;
    }

    public static class Builder {
        private String email, password, avatar;

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder avatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
