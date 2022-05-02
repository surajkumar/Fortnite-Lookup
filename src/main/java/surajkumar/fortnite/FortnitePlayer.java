package surajkumar.fortnite;

public record FortnitePlayer(String username,
                             GamePlatform platform,
                             MatchStats solo,
                             MatchStats duo,
                             MatchStats squad) {
}