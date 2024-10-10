import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;

public class SpotifyAPIParser {
    public String parse(InputStream testDataStream) throws IOException {
        JSONArray result= (JSONArray) JsonPath.read(testDataStream,"$..genres[52,59,99,103,124]");
        String string = result.get(0, 1, 2, 3, 4).toString();
        return string;

    }
}
