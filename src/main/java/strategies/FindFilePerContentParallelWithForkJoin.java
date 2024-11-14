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

public class FindFilePerContentParallelWithForkJoin extends AbstractFileSearch implements Utils {

    String desiredWord;

    public FindFilePerContentParallelWithForkJoin(String desiredPathString, String desiredWord) {
        super(desiredPathString);
        this.desiredWord = desiredWord;
    }

    public List<MatchedFile> getMatchedFiles() {
        Path desiredPath = Paths.get(desiredPathString);
        List<CompletableFuture<MatchedFile>> completableFutureList = findAsync(desiredPath);

        return completableFutureList.stream()
                .filter(matchedFileCompletableFuture -> matchedFileCompletableFuture.join() != null)
                .map(CompletableFuture::join)
                .toList();
    }

    private List<CompletableFuture<MatchedFile>> findAsync(Path directory) {
        try (Stream<Path> stream = Files.walk(directory)) {
            return stream.parallel()
                    .filter(this::isAppropriateFile)
                    .map(path -> CompletableFuture.supplyAsync(() -> isValidContent(path, desiredWord) ?
                            MatchedFile.getMatchedFile(path) : null))
                    .toList();
        } catch (IOException e) {
            Logger.getGlobal().info(e.getMessage());
        }

        return new ArrayList<>();
    }
}

