package surajkumar.fortnite;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class FortniteLookup {
    private static final String API_URL = "https://api.fortnitetracker.com/v1/profile";

    public static FortnitePlayer lookup(String playerName, GamePlatform platform, String apiKey) throws Exception {
        String searchTerm = playerName.replaceAll(" ", "%20");
        String api = "%s/%s/%s".formatted(API_URL, platform, searchTerm);
        JsonObject result = post(api, apiKey).getAsJsonObject();
        if(result.has("error")) {
            String error = result.get("error").getAsString();
            throw new LookupException(error);
        }
        MatchStats solo = new MatchStats(
                getWins(result, "p2"),
                getKills(result, "p2"),
                getKDR(result, "p2"),
                getMatches(result, "p2"));

        MatchStats duo = new MatchStats(
                getWins(result, "p10"),
                getKills(result, "p10"),
                getKDR(result, "p10"),
                getMatches(result, "p10"));

        MatchStats squad = new MatchStats(
                getWins(result, "p9"),
                getKills(result, "p9"),
                getKDR(result, "p9"),
                getMatches(result, "p9"));

        return new FortnitePlayer(playerName, platform, solo, duo, squad);
    }

    private static int getWins(JsonObject result, String segment) {
        return result.get("stats")
                .getAsJsonObject().get(segment)
                .getAsJsonObject().get("top1")
                .getAsJsonObject().get("value")
                .getAsInt();
    }

    private static int getKills(JsonObject result, String segment) {
        return result.get("stats")
                .getAsJsonObject().get(segment)
                .getAsJsonObject().get("kills")
                .getAsJsonObject().get("value")
                .getAsInt();
    }

    private static double getKDR(JsonObject result, String segment) {
        return result.get("stats")
                .getAsJsonObject().get(segment)
                .getAsJsonObject().get("kd")
                .getAsJsonObject().get("value")
                .getAsDouble();
    }

    private static int getMatches(JsonObject result, String segment) {
        return result.get("stats")
                .getAsJsonObject().get(segment)
                .getAsJsonObject().get("matches")
                .getAsJsonObject().get("value")
                .getAsInt();
    }

    private static JsonElement post(String apiURL, String apiKey) throws IOException {
        URL url = new URL(apiURL);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("GET");
        http.setRequestProperty("User-Agent", "Mozilla/5.0");
        http.setRequestProperty("TRN-Api-Key", apiKey);
        http.setDoOutput(true);
        Reader reader = new BufferedReader(new InputStreamReader(http.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder response = new StringBuilder();
        int read;
        while ((read = reader.read()) >= 0) {
            response.append((char) read);
        }
        return new JsonParser().parse(response.toString());
    }
}