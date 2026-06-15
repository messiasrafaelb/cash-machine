package domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import static domain.helper.CsvPaths.USER_SEQUENCE;

public class UserSequenceRepositoryTest {

    @Test
    void nextId_shouldReturnIdIncrementedOfCsv() {
        var repository = new UserSequenceRepository();

        var id = repository.nextId(USER_SEQUENCE);

        assertNotNull(id);
        assertEquals(2, id);
    }
}
