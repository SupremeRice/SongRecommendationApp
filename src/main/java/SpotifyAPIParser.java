import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;

public class SpotifyAPIParser {
    public String parse(InputStream testDataStream) throws IOException {
        JSONArray result= JsonPath.read(testDataStream,"$..genres[52,59,99,103,124]");
        int[] indices = {0, 1, 2, 3, 4};
        StringBuilder sb = new StringBuilder();
        for (int index : indices) {
            if (index < result.size()) {
                sb.append(result.get(index).toString()).append(", ");
            }
        }

        // Remove the trailing comma and space
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        }


        return sb.toString();


    }




}
