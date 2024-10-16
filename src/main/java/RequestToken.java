
import java.io.*;
import java.net.*;
import java.util.Base64;


public class RequestToken {

    public static void main(String[] args) {
        try {
            String accessToken = getAccessToken();
            System.out.println("Access Token: " + accessToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getAccessToken() throws IOException {
        String clientID = "dd6b2daa511b43089bf71651eaf0ca74";
        String clientSecret = "4bfb2902292045f7815ef909cab7984d";
        String auth = clientID + ":" + clientSecret;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        URL url = new URL("https://accounts.spotify.com/api/token");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Basic " + encodedAuth);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        String body = "grant_type=client_credentials";
        try (OutputStream os = conn.getOutputStream()) {
            os.write(body.getBytes());
            os.flush();
        }

        int responseCode = conn.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String responseLine;
                StringBuilder response = new StringBuilder();
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine);
                }
                System.out.println("Response: " + response);
            }
        } else {
            System.out.println("Error: " + conn.getResponseMessage());
            return null;
        }
        return clientID;
    }
}