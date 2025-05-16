package com.raman.oopscoder.posts.systemdesign.solidprinciples.ocp;

public class AuthService_Bad {
    public boolean authenticate(String type, String username, String password) {
        if ("basic".equals(type)) {
            return basicAuth(username, password);
        } else if ("oauth".equals(type)) {
            return oauthLogin(username, password);
        }
        return false;
    }
}
