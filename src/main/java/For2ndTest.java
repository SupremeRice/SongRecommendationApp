import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class For2ndTest {

    private String accessToken;

    public For2ndTest(String accessToken) {
        this.accessToken = accessToken;
    }

    public String fetchRecommendations() throws Exception {
        String endpoint = "https://api.spotify.com/v1/recommendations?limit=6&market=US&seed_genres=hip-hop,indie-pop,rock,sad,work-out";

        // Create the URL object
        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set up the request properties
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + accessToken);

        // Check the response code
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString(); // Return the response as a string
        } else {
            throw new Exception("Error: " + responseCode);
        }
    }
}
