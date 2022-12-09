package Tank_Game;

/**
 * @author wkn
 * @version 1.0
 **/
public class Bomb {
    private int x;
    private int y;
    int life = 9;//生命周期
    boolean isLive = true;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //减少生命值
    public void lifeDown(){
        if(life > 0){
            life--;
        }else{
            isLive = false;
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }
}
