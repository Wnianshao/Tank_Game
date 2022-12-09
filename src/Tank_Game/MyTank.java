package Tank_Game;

import java.util.Vector;

/**
 * @author wkn
 * @version 1.0
 **/
public class MyTank extends Tank {

    private Shot shot = null;

    //可以发射多颗子弹
    private Vector<Shot> shots = new Vector<>();
    public MyTank(int x,int y){
        super(x,y);
    }

    public void shotEnemyTank(){
        switch (getDirection()){
            case 0://上
                shot = new Shot(getX() +20 , getY() ,0);
                break;
            case 1://左
                shot = new Shot(getX() , getY() + 20 , 1);
                break;
            case 2://下
                shot = new Shot(getX() + 20, getY() + 60,2);
                break;
            case 3://右
                shot = new Shot(getX() + 60, getY() +20, 3);
                break;
        }
        //把新创建的shot放入到shots种
        shots.add(shot);
        new Thread(shot).start();
    }

    public Shot getShot() {
        return shot;
    }

    public void setShot(Shot shot) {
        this.shot = shot;
    }

    public Vector<Shot> getShots() {
        return shots;
    }

    public void setShots(Vector<Shot> shots) {
        this.shots = shots;
    }
}
