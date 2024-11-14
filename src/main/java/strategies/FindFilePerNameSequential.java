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
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class FindFilePerNameSequential extends AbstractFileSearch implements Utils {
    private final String desiredFileName;

    public FindFilePerNameSequential(
            String desiredPathString,
            String desiredFileName) {
        super(desiredPathString);

        this.desiredFileName = desiredFileName;
    }

    public List<MatchedFile> getMatchedFiles() {
        Path desiredPath = Paths.get(desiredPathString);

        return find(desiredPath);
    }

    protected List<MatchedFile> find(Path directory) {
        try (Stream<Path> stream = Files.walk(directory)) {
            return stream
                    .filter(path -> isAppropriateFile(path) && matchFileNameWithText(desiredFileName, path))
                    .map(MatchedFile::getMatchedFile)
                    .toList();
        } catch (IOException e) {
            Logger.getGlobal().info(e.getMessage());
        }
        return new ArrayList<>();
    }
}
