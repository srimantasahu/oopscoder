package com.raman.oopscoder.posts.systemdesign.solidprinciples.srp;

public class UserService {
    private UserValidator validator;
    private UserRepository repository;
    private EmailService emailService;

    public void registerUser(SecurityProperties.User user) {
        validator.validate(user);
        repository.save(user);
        emailService.sendWelcomeEmail(user);
    }
}
