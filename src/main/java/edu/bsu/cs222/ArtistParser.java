package edu.bsu.cs222;

import org.json.JSONArray;
import org.json.JSONObject;

public class ArtistParser {
    public void printArtists(String jsonResponse) {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray tracks = jsonObject.getJSONArray("tracks");
        if (tracks.length() > 0) {
            int artistCount = 0;
            System.out.println("Artists:");
            for (int i = 0; i < tracks.length() && artistCount < 5; i++) {
                JSONArray artists = tracks.getJSONObject(i).getJSONArray("artists");
                for (int j = 0; j < artists.length() && artistCount < 5; j++) {
                    String artistName = artists.getJSONObject(j).getString("name");
                    System.out.println(artistName);
                    artistCount++;
                }
            }
        } else {
            System.out.println("No tracks found for this genre.");
        }
    }


}
