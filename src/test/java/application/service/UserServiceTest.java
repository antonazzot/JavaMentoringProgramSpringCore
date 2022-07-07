package application.service;

import application.AbstractTest;
import application.config.StorageType;
import application.model.User;
import application.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest extends AbstractTest {

    private String userName = "TestUserName";
    private User user;

    @Autowired
    private UserRepo userRepo;

    @BeforeEach
    private void userInit () {
        user = User.builder().name(userName).build();
        database.cleanStorageByType(StorageType.USER);
    }

    @Test
    void createUser() {
        Integer id = userRepo.saveUser(user);
        assertThat(user.getName()).isEqualTo(userName);
        assertThat(id).isEqualTo(userRepo.findUserByName(userName).getId());
    }

    @Test
    void createUserReturnUser() {
        User userCr= userRepo.findUserById(userRepo.saveUser(user));
        assertThat(userCr.getName()).isEqualTo(user.getName());
    }

    @Test
    void deleteUserByName() {
        Integer id = userRepo.saveUser(user);
        Collection<?> values = database.getStorageMap().get(StorageType.USER).values();
        assertThat(values).hasSize(1);
        userRepo.deleteUser(id);
        assertThat(values).isEmpty();
    }

    @Test
    void findByName() {
        Collection<?> values = database.getStorageMap().get(StorageType.USER).values();
        assertThat(values).isEmpty();
        userRepo.saveUser(user);
        User userByName = userRepo.findUserByName(userName);
        assertThat(values).hasSize(1);
        assertThat(userByName.getName()).isEqualTo(userName);
        User test = userRepo.findUserByName("test");
        assertThat(test).isNull();
    }

    @Test
    void findById() {
        Collection<?> values = database.getStorageMap().get(StorageType.USER).values();
        assertThat(values).isEmpty();
        Integer id = userRepo.saveUser(user);
        User userById = userRepo.findUserById(id);
        assertThat(values).hasSize(1);
        assertThat(userById.getId()).isEqualTo(id);
        User test = userRepo.findUserById(Integer.MAX_VALUE);
        assertThat(test).isNull();
    }
}