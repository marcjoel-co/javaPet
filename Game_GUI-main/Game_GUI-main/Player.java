import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int hp;
    private int mp;
    private int experience;
    private int attackPower;
    private String weapon;
    private String gender;
    private List<String> items; 
    
    public Player(String name, int hp, int mp, int attackPower) {
        this.name = name;
        this.hp = hp;
        this.mp = mp;
        this.experience = 0;
        this.attackPower = attackPower;
        this.items = new ArrayList<>(); 
    }


    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience += experience;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getWeapon() {
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public void addItem(String item) {
        items.add(item);
    }

    public List<String> getItems() {
        return items;
    }

    public void showItems() {
        if (items.isEmpty()) {
            System.out.println("所持しているアイテムはありません。");
        } else {
            System.out.println("所持アイテム:");
            for (String item : items) {
                System.out.println("- " + item);
            }
        }
    }
}
