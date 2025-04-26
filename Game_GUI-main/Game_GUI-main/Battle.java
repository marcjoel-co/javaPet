import java.util.Random;

public class Battle {
    public void fight(Player player, int enemyStrength) {
        player.setHp(player.getHp() - enemyStrength);
        player.setExperience(player.getExperience() + 100);
    }

    public boolean escape() {
        Random rand = new Random();
        return rand.nextInt(100) < 50;
    }
}
