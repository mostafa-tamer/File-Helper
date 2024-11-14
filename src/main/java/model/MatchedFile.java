package model;


import java.nio.file.Path;

public record MatchedFile(String name, String path) {

    public static MatchedFile getMatchedFile(Path path) {
        return new MatchedFile(
                path.getFileName().toString(),
                path.toAbsolutePath().toString()
        );
    }
}
