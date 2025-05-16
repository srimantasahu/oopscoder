package com.raman.oopscoder.posts.systemdesign.solidprinciples.lsp;

public class User {
    public boolean isEmailVerified() {
        return true;
    }
}

public class GuestUser extends User {
    public boolean isEmailVerified() {
        throw new UnsupportedOperationException("Guest users don't have emails");
    }
}
