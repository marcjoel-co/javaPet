public class GameManager {
    private static GameManager instance;
    private Player player;
    private Weapon shovel;
 

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
        return shovel;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setWeapon(Weapon weapon) {
        this.shovel = weapon;
    }
}
