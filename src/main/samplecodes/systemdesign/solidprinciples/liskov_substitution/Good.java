package com.raman.oopscoder.posts.systemdesign.solidprinciples.lsp;

public interface Verifiable {
    boolean isEmailVerified();
}

public class RegisteredUser implements Verifiable {
    public boolean isEmailVerified() {
        return true;
    }
}
