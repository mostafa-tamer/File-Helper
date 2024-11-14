package strategies;

import model.MatchedFile;
import strategies.abstracts.AbstractFileSearch;
import strategies.utils.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;


public class FindFilePerContentSequential extends AbstractFileSearch implements Utils {

    String desiredWord;

    public FindFilePerContentSequential(String desiredPathString, String desiredWord) {
        super(desiredPathString);
        this.desiredWord = desiredWord;
    }

    public List<MatchedFile> getMatchedFiles() {
        Path desiredPath = Paths.get(desiredPathString);
        return findAsync(desiredPath);
    }

    private List<MatchedFile> findAsync(Path directory) {
        try (Stream<Path> stream = Files.walk(directory)) {
            return stream
                    .filter(path -> isAppropriateFile(path) && isValidContent(path, desiredWord))
                    .map(MatchedFile::getMatchedFile)
                    .toList();
        } catch (IOException e) {
            Logger.getGlobal().info(e.getMessage());
        }

        return new ArrayList<>();
    }
}