
import java.io.IOException;
import java.util.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SpotifyAPI {
    //Keys that show the program is allowed to talk to Spotify
    private static final String CLIENT_ID = "dd6b2daa511b43089bf71651eaf0ca74";
    private static final String CLIENT_SECRET = "4bfb2902292045f7815ef909cab7984d";

    //The address to ask for a "magic key"(TOKEN) from Spotify
    private static final String TOKEN_URL = "https://accounts.spotify.com/api/token";

    //The Address to get information about a song
    private static final String TRACK_URL = "https://api.spotify.com/v1/tracks/4cOdK2wGLETKBW3PvgPWqT";

    public static void main(String[] args) {
        try {
            //Tries to get a "magic key" called an access token using getAccessToken()
            String accessToken = getAccessToken();

            //If it gets a token successfully, it uses getTrackInfo(accessToken) to ask Spotify about a song.
            if (accessToken != null) {
                getTrackInfo(accessToken);
            }

            //If something goes wrong, it prints out an error message.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getAccessToken() throws IOException {
        //This part creates a helper(client) to talk to Spotify.
        //It sends a request to the TOKEN_URL to get the "magic key"
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(TOKEN_URL);

            //It combines the keys(CLIENT_ID and CLIENT_SECRET) into a code
            //It then scrambles it (like a secret message) using Base64 encoding.
            String credentials = CLIENT_ID + ":" + CLIENT_SECRET;
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

            //It then sets up the request with headers (extra information), telling spotify that it has the right secret keys.
            post.setHeader("Authorization", "Basic " + encodedCredentials);
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");

            //The program says, "I want to use these credentials" using the special phrase "grant_type=client_credentials"
            StringEntity entity = new StringEntity("grant_type=client_credentials");
            post.setEntity(entity);

            //The program send the request(post) to Spotify and waits for a response.
            try (CloseableHttpResponse response = client.execute(post)) {

                //If spotify says everything is okay (status code 200)
                //it reads the response and pulls out the token using extractAccessToken()
                if (response.getStatusLine().getStatusCode() == 200) {
                    String responseBody = EntityUtils.toString(response.getEntity());
                    return extractAccessToken(responseBody);

                //If it doesn't work, it returns nothing(null)
                } else {
                    System.out.println("Error: " + response.getStatusLine().getStatusCode());
                }
            }
        }
        return null;
    }
    //This part reads the response (written in JSON, like a special code language for computers)
    //It finds the part called "access_token" and returns it as plain text (the magic key)
    private static String extractAccessToken(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);
        return node.get("access_token").asText();
    }
    //This part creates another helper(client) and sets up a request to get song information using the track's URL
    //It adds the access token (magic key) as a header called "Bearer", like a special badge that says, "I have permission"
    private static void getTrackInfo(String accessToken) throws IOException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet get = new HttpGet(TRACK_URL);
            get.setHeader("Authorization", "Bearer " + accessToken);

            //It sends the request to Spotify
            //So, the code is like a robot that gets a special key (access token) and then uses that key to ask Spotify form song details
            try (CloseableHttpResponse response = client.execute(get)) {
                if (response.getStatusLine().getStatusCode() == 200) {
                    String responseBody = EntityUtils.toString(response.getEntity());
                    System.out.println("Track Info: " + responseBody);
                } else {
                    System.out.println("Error: " + response.getStatusLine().getStatusCode());
                }
            }
        }
    }
}
