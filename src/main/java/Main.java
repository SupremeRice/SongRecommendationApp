import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try {
            RequestToken tokenRequest = new RequestToken();
            ArtistByGenre artistByGenre = new ArtistByGenre(tokenRequest);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter a genre: ");
            String genre = scanner.nextLine();

            artistByGenre.getArtistByGenre(genre.toLowerCase());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
