package domain.repository.sequence;

import java.io.FileReader;
import com.opencsv.CSVReader;

import domain.interfaces.RepositorySequence;

import static common.Numbers.*;
import static common.Messages.*;

public class UserSequenceRepository implements RepositorySequence {

    @Override
    public Integer nextId(String filePath) {
        try (var reader = new CSVReader(new FileReader(filePath))) {
            var lines = reader.readAll();
            var lastLine = lines.get(ONE);

            return Integer.parseInt(lastLine[ZERO]) + ONE;
        } catch (Exception ex) {
            throw new RuntimeException(
                String.format(MSG_ID_EXCEPTION, UserSequenceRepository.class.getName()), ex);
        }
    }
}
