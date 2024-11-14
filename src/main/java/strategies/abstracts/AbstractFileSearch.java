package strategies.abstracts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

public abstract class AbstractFileSearch {


    protected final String desiredPathString;

    protected AbstractFileSearch(String desiredPathString) {
        this.desiredPathString = desiredPathString;
    }

}
