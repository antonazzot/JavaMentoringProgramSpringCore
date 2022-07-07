package application.repository;

import application.config.StorageType;
import application.databaseamulation.Database;
import application.model.ModelEntity;
import application.model.User;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
@RequiredArgsConstructor
public class UserRepo {
    private final Database database;

    public Integer saveUser (User user) {
        return database.addEntity(user);
    }

    public User findUserById (Integer id) {
        return (User) database.getEntityById(StorageType.USER, id);
    }

    public User deleteUser (Integer id) {
        return (User) database.deleteEntityById(StorageType.USER, id);
    }

    public Collection<User> getAllUsers () {
        Collection<ModelEntity> values = database.findAll(StorageType.USER).values();
        return values.stream().map(User.class::cast).toList();
    }

    public User findUserByName(String name) {
        return getAllUsers().stream().filter(user -> user.getName().equals(name)).findFirst().orElse(null);
    }

}
