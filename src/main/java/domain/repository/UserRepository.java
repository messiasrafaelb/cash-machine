package domain.repository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import domain.interfaces.RepositoryCrud;
import domain.interfaces.RepositorySequence;
import domain.model.User;
import domain.repository.sequence.UserSequenceRepository;

import static common.CsvPaths.*;
import static common.Messages.MSG_CSV_READ_EXCEPTION;
import static common.Messages.MSG_CSV_SAVE_EXCEPTION;
import static common.Messages.MSG_USER_NOT_FOUND_EXCEPTION;
import static common.Numbers.ZERO;

public class UserRepository implements RepositoryCrud<User> {

    private final RepositorySequence sequenceRepository = new UserSequenceRepository();

    @Override
    public User findById(Integer id) {
        return getAllUsers().stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(User user) {
        var users = getAllUsers();
        var id = sequenceRepository.nextId(USER_SEQUENCE_PATH);

        user.setId(id);
        users.add(user);

        saveAllUsers(users);
    }

    @Override
    public void update(Integer id, User user) {
        var users = getAllUsers();
        boolean found = false;

        for (int i = ZERO; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                users.set(i, user);
                found = true;
                break;
            }
        }

        if (found) {
            saveAllUsers(users);
        } else {
            throw new IllegalArgumentException(
                    String.format(MSG_USER_NOT_FOUND_EXCEPTION, id));
        }
    }

    private List<User> getAllUsers() {
        if (!Files.exists(Paths.get(USER_PATH))) {
            return new ArrayList<>();
        }

        try (var reader = new FileReader(USER_PATH)) {
            return new CsvToBeanBuilder<User>(reader)
                    .withType(User.class)
                    .withThrowExceptions(false)
                    .build()
                    .parse();
        } catch (IOException ex) {
            throw new RuntimeException(MSG_CSV_READ_EXCEPTION, ex);
        }
    }

    private void saveAllUsers(List<User> users) {
        try (var writer = new FileWriter(USER_PATH)) {
            var beanToCsv = new StatefulBeanToCsvBuilder<User>(writer)
                    .withApplyQuotesToAll(false)
                    .build();

            beanToCsv.write(users);
        } catch (Exception e) {
            throw new RuntimeException(MSG_CSV_SAVE_EXCEPTION, e);
        }
    }

}
