package edu.bsu.cs222;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class tests {
    @Test
    public void testFiveGenre() throws IOException {
       SpotifyAPIParser parser = new SpotifyAPIParser();

        InputStream testDataStream = getClass().getResourceAsStream("test.JSON");
        String FiveGenre = parser.parse(testDataStream);
        assertEquals("hip-hop, indie-pop, rock, sad, work-out", FiveGenre);
    }
    @Test
    public void testReadAlbumNames() {
        String accessToken = "BQBXPsTYhDrhoFN2Zb58JXln7JedsPO7uLsES3Ym2muyfxfR1knkRPpan-wcC5y-Vwb_tKtfLX29b-pL7c1VBUaf22BPUqRB9qKs4qQYucO6DJ53qHo"; // Replace with your valid access token
        For2ndTest recommendations = new For2ndTest(accessToken);

        try {
            String jsonResponse = recommendations.fetchRecommendations();
            assertNotNull(jsonResponse, "The response should not be null");

            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray tracks = jsonObject.getJSONArray("tracks");

            assertFalse(tracks.isEmpty(), "Tracks array should not be empty");

            for (int i = 0; i < tracks.length(); i++) {
                JSONObject track = tracks.getJSONObject(i);
                JSONObject album = track.getJSONObject("album");
                String albumName = album.getString("name");
                assertNotNull(albumName, "Album name should not be null");
                assertFalse(albumName.isEmpty(), "Album name should not be empty");
                System.out.println("Album Name: " + albumName);
            }
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }
}

