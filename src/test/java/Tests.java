import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tests {
    @Test
    public void testFirsGenre() throws IOException {
       SpotifyAPIParser parser = new SpotifyAPIParser();
        // Reads "test.JSON" text file
        InputStream testDataStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("test.JSON");
        String FiveGenre = parser.parse(testDataStream);
        assertEquals("rock", FiveGenre);
    }
}
