import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try {
            RequestToken tokenRequest = new RequestToken();
            SpotifyAPI spotifyAPI = new SpotifyAPI(tokenManager);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter a genre: ");
            String genre = scanner.nextLine();

            spotifyAPI.getArtistsByGenre(genre);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
