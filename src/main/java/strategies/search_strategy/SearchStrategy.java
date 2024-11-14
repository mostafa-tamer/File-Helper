package strategies.search_strategy;

import strategies.*;

public interface SearchStrategy {

    static FindFilePerContentParallelWithForkJoin newFindFilePerContentParallelWithForkJoin(String desiredPathString, String desiredWord) {
        return new FindFilePerContentParallelWithForkJoin(desiredPathString, desiredWord);
    }

    static FindFilePerContentParallelWithCompletableFutre newFindFilePerContentParallelWithCompletableFutre(String desiredPathString, String desiredWord) {
        return new FindFilePerContentParallelWithCompletableFutre(desiredPathString, desiredWord);
    }

    static FindFilePerContentSequential newFindFilePerContentSequential(String desiredPathString, String desiredWord) {
        return new FindFilePerContentSequential(desiredPathString, desiredWord);
    }



    static FindFilePerNameParallelWithCompletableFuture newFilePerNameParallelWithCompletableFuture(String desiredPathString, String desiredFileName) {
        return new FindFilePerNameParallelWithCompletableFuture(desiredPathString, desiredFileName);
    }

    static FindFilePerNameSequential newFindFilePerNameSequential(String desiredPathString, String desiredFileName) {
        return new FindFilePerNameSequential(desiredPathString, desiredFileName);
    }
}
