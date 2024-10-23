import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ArtistByGenre {

    private final RequestToken requestToken;

    public ArtistByGenre(RequestToken requestToken) {
        this.requestToken = requestToken;
    }

    public void getArtistByGenre(String genre) throws Exception {
        String accessToken = requestToken.getAccessToken();
        String url = "https://api.spotify.com/v1/recommendations?limit=5&seed_genres=" + genre;
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + accessToken);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray tracks = jsonResponse.getJSONArray("tracks");

                if (tracks.length() > 0) {
                    System.out.println("Artists:");
                    for (int i = 0; i < tracks.length(); i++) {
                        JSONArray artists = tracks.getJSONObject(i).getJSONArray("artists");
                        for (int j = 0; j < artists.length(); j++) {
                            String artistName = artists.getJSONObject(j).getString("name");
                            System.out.println(artistName);
                        }
                    }
                } else {
                    System.out.println("No tracks found for this genre.");
                }
            }
        } else {
            System.out.println("GET request failed: " + connection.getResponseMessage());
        }
    }
}