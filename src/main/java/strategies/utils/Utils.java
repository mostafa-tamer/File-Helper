package strategies.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

public interface Utils {
    default boolean isValidContent(Path path, String desiredWord) {
        try (Stream<String> lines = Files.lines(path)) {
            return lines.flatMap(line -> Arrays.stream(line.split("\\s+")))
                    .filter(word -> !word.isEmpty())
                    .anyMatch(s -> s.equalsIgnoreCase(desiredWord));
        } catch (Exception e) {
//            System.err.println("Error reading file: " + e.getMessage());
        }
        return false;
    }

    default boolean isValidContentParallel(Path path, String desiredWord) {
        try (Stream<String> lines = Files.lines(path)) {
            return lines.parallel()
                    .flatMap(line -> Arrays.stream(line.split("\\s+")))
                    .filter(word -> !word.isEmpty())
                    .anyMatch(s -> s.equalsIgnoreCase(desiredWord));
        } catch (Exception e) {
//            System.err.println("Error reading file: " + e.getMessage());
        }
        return false;
    }

    default boolean isAppropriateFile(Path path) {
        try {
            return Files.isRegularFile(path) &&
                    Files.isReadable(path) &&
                    !Files.isHidden(path);
        } catch (Exception e) {
//            Logger.getGlobal().info(e.getMessage());
            return false;
        }
    }

    default boolean matchFileNameWithText(String desiredFileName, Path path) {
        return path.getFileName()
                .toString()
                .equalsIgnoreCase(desiredFileName);
    }
}

