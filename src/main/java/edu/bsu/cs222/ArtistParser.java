package edu.bsu.cs222;

import org.json.JSONArray;
import org.json.JSONObject;

public class ArtistParser {
    public void printArtists(String jsonResponse) {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray tracks = jsonObject.getJSONArray("tracks");

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
}
