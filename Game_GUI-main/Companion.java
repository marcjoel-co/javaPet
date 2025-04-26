public class Companion {
    private String name;
    private String skill;
    private int hp;

    public Companion(String name, String skill, int hp) {
        this.name = name;
        this.skill = skill;
        this.hp = hp;
    }

    public String getName() {
        return name;
    }

    public String getSkill() {
        return skill;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
