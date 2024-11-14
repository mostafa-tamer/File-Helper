package concurency;

import model.MatchedFile;
import strategies.utils.Utils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTask extends RecursiveTask<Void> implements Utils {
    final List<Path> data;
    String desiredWord;
    int start;
    int end;
    private final long threshold;
    public static List<MatchedFile> result = new ArrayList<>();

    public ForkJoinTask(List<Path> data, String desiredWord) {
        this(data, desiredWord, 0, data.size(),
                data.size() / (Runtime.getRuntime().availableProcessors() * 10L));
        result.clear();
    }

    private ForkJoinTask(List<Path> data, String desiredWord, int start, int end, long threshold) {
        this.data = data;
        this.start = start;
        this.end = end;
        this.threshold = threshold;
        this.desiredWord = desiredWord;
    }

    @Override
    protected Void compute() {
        int length = end - start;

        if (length <= threshold)
            return computeSequentially();

        int mid = start + length / 2;

        ForkJoinTask left = new ForkJoinTask(data, desiredWord, start, mid, threshold);
        ForkJoinTask right = new ForkJoinTask(data, desiredWord, mid, end, threshold);

        left.fork();
        right.compute();
        left.join();

        return null;
    }

    protected Void computeSequentially() {
        for (int i = start; i < end; i++) {
            Path path = data.get(i);
            if (isAppropriateFile(path) && isValidContent(path, desiredWord)) {
                result.add(MatchedFile.getMatchedFile(path));
            }
        }
        return null;
    }
}