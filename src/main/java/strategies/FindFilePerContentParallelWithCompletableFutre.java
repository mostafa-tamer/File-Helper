package strategies;

import concurency.ForkJoinTask;
import model.MatchedFile;
import strategies.abstracts.AbstractFileSearch;
import strategies.utils.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class FindFilePerContentParallelWithCompletableFutre extends AbstractFileSearch implements Utils {

    String desiredWord;

    public FindFilePerContentParallelWithCompletableFutre(String desiredPathString, String desiredWord) {
        super(desiredPathString);
        this.desiredWord = desiredWord;
    }

    public List<MatchedFile> getMatchedFiles() {
        Path desiredPath = Paths.get(desiredPathString);
        List<Path> paths = findAsync(desiredPath);

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask task = new ForkJoinTask(paths, desiredWord);
        forkJoinPool.invoke(task);

        return ForkJoinTask.result;
    }

    private List<Path> findAsync(Path directory) {
        try (Stream<Path> stream = Files.walk(directory)) {
            return stream.toList();
        } catch (IOException e) {
            Logger.getGlobal().info(e.getMessage());
        }

        return new ArrayList<>();
    }
}
