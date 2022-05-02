package surajkumar.fortnite;

public enum GamePlatform {
    KEYBOARD_AND_MOUSE("kbm"),
    GAMEPAD("gamepad"),
    TOUCH("touch");

    private String platform;
    GamePlatform(String platform) {
        this.platform = platform;
    }

    @Override
    public String toString() {
        return platform;
    }
}
