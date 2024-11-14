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

public class FindFilePerNameParallelWithCompletableFuture extends AbstractFileSearch implements Utils {

    private final String desiredFileName;

    public FindFilePerNameParallelWithCompletableFuture(
            String desiredPathString,
            String desiredFileName) {
        super(desiredPathString);
        this.desiredFileName = desiredFileName;
    }

    public List<MatchedFile> getMatchedFiles() {
        Path desiredPath = Paths.get(desiredPathString);

        return find(desiredPath).stream()
                .map(CompletableFuture::join)
                .toList();
    }

    protected List<CompletableFuture<MatchedFile>> find(Path directory) {
        try (Stream<Path> stream = Files.walk(directory)) {
            return stream.parallel()
                    .map(path -> CompletableFuture.supplyAsync(() ->
                            isAppropriateFile(path) && matchFileNameWithText(desiredFileName, path) ?
                                    MatchedFile.getMatchedFile(path) : null))
                    .toList();
        } catch (IOException e) {
            Logger.getGlobal().info(e.getMessage());
        }
        return new ArrayList<>();
    }
}
