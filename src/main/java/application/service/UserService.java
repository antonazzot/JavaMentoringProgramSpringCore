package application.service;

import application.repository.UserRepo;
import application.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;


public class UserService {
    @Autowired
    private  UserRepo userRepo;

    public Integer createUser (String name) {
      return userRepo.saveUser(User.builder().name(name).build());
    }

    public User createUserReturnUser (String name) {
      return userRepo.findUserById(userRepo.saveUser(User.builder().name(name).build()));
    }

    public User deleteUserByName (String name) {
        return userRepo.deleteUser(userRepo.findUserByName(name).getId());
    }
    public Optional<User> findByName (String name) {
        return Optional.ofNullable(userRepo.findUserByName(name));
    }
    public Optional<User> findById (Integer id) {
        return Optional.ofNullable(userRepo.findUserById(id));
    }
}
