This program leverages the API provided by https://tracker.gg/ to look up user stats for Fortnite.
An API key is required for this tool to work. Keys are free and obtainable via the tracker.gg website.

Useage:

FortnitePlayer player = FortniteLookup.lookup("Ninja", GamePlatform.KEYBOARD_AND_MOUSE, "API_KEY");
...
int fortniteSoloWins = player.solo().wins();
....
