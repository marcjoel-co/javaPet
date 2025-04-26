public class GameManager {
    private static GameManager instance;
    private Player player;
    private Weapon weapon;

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public Player getPlayer() {
        return player;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
}
