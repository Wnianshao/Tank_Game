package Tank_Game;

import java.util.Vector;

/**
 * @author wkn
 * @version 1.0
 **/
public class EnemyTank extends Tank implements Runnable{
    private Vector<Shot> shots = new Vector<>();
    private Vector<EnemyTank> enemyTanks = new Vector<>();
    private boolean isLive = true;
    public EnemyTank(int x, int y) {
        super(x, y);
    }

    @Override
    public void run() {
        while (isLive){

            //如果没子弹了，则创建一颗子弹
            if(isLive && shots.size() <= 10){
                //判断方向，创建子弹
                Shot s= null;
                switch (getDirection()){
                    case 0:
                        s = new Shot(getX()+20, getY(),0);
                        break;
                    case 1:
                        s = new Shot(getX(),getY()+20,1);
                        break;
                    case 2:
                        s = new Shot(getX()+20,getY()+60,2);
                        break;
                    case 3:
                        s = new Shot(getX() +60 ,getY()+20,3);
                        break;
                }
                shots.add(s);
                new Thread(s).start();
            }
            //根据坦克的方向来继续移动
            switch (getDirection()){
                case 0:
                    // 让坦克保持一个方向走30步
                    for(int i = 0;i < 30;i++){
                        if(getY() >= 10 && !isTouchEnemyTank()){
                            moveUp();
                        }
                        //休眠50ms
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1:
                    for(int i = 0;i < 30;i++){
                        if(getX() >= 10 && !isTouchEnemyTank()) {
                            moveLeft();
                        }
                        //休眠50ms
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2:
                    for(int i = 0;i < 30;i++){
                        if(getY()+70 <= 1000 && !isTouchEnemyTank()) {
                            moveDown();
                        }
                        //休眠50ms
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 3:
                    for(int i = 0;i < 30;i++){
                        if((getX() + 70) <= 1000 && !isTouchEnemyTank()) {
                            moveRight();
                        }
                        //休眠50ms
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }

            //然后随机改变坦克方向
            setDirection((int)(Math.random() * 4));
            //写并发程序，一定考虑清楚该线程什么时候结束
            if(!isLive){
                break;
            }
        }

    }

    public Vector<Shot> getShots() {
        return shots;
    }

    public void setShots(Vector<Shot> shots) {
        this.shots = shots;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public Vector<EnemyTank> getEnemyTanks() {
        return enemyTanks;
    }

    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    public boolean isTouchEnemyTank(){
        switch (this.getDirection()){
            case 0://上
                for(int i = 0;i < enemyTanks.size();i++){
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if(enemyTank != this){
                        //如果敌人坦克是上下 x[enemyTank.getX(),enemyTank.getX()+40]
                        //                y[enmeyTank.getY(),enemyTank.getY()+60]
                        if(enemyTank.getDirection() == 0 || enemyTank.getDirection() == 2){
                            //当前坦克左上角坐标[this.getX(),this.getY()]
                            if(this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX()+40
                                    && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY()+60){
                                return true;
                            }
                            //当前坦克右上角的坐标[this.getX()+40,this.getY()]
                            if(this.getX()+40 >= enemyTank.getX() && this.getX()+40 <= enemyTank.getX()+40
                                    && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY()+60){
                                return true;
                            }
                        }
                        //如果敌人坦克是左右 x[enemyTank.getX(),enemyTank.getX()+60]
                        //                y[enmeyTank.getY(),enemyTank.getY()+40]
                        if(enemyTank.getDirection() == 1 || enemyTank.getDirection() == 3){
                            //当前坦克左上角坐标[this.getX(),this.getY()]
                            if(this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX()+60
                                    && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY()+40){
                                return true;
                            }
                            //当前坦克右上角的坐标[this.getX()+40,this.getY()]
                            if(this.getX()+40 >= enemyTank.getX() && this.getX()+40 <= enemyTank.getX()+60
                                    && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY()+40){
                                return true;
                            }
                        }
                    }
                }
                break;
            case 1://左
                for(int i = 0;i < enemyTanks.size();i++){
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if(enemyTank != this){
                        //如果敌人坦克是上下 x[enemyTank.getX(),enemyTank.getX()+40]
                        //                y[enmeyTank.getY(),enemyTank.getY()+60]
                        if(enemyTank.getDirection() == 0 || enemyTank.getDirection() == 2){
                            //当前坦克左上角坐标[this.getX(),this.getY()]
                            if(this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX()+40
                                    && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY()+60){
                                return true;
                            }
                            //当前坦克左下角的坐标[this.getX(),this.getY()+40]
                            if(this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX()+40
                                    && this.getY()+40 >= enemyTank.getY() && this.getY()+40 <= enemyTank.getY()+60){
                                return true;
                            }
                        }
                        //如果敌人坦克是左右 x[enemyTank.getX(),enemyTank.getX()+60]
                        //                y[enmeyTank.getY(),enemyTank.getY()+40]
                        if(enemyTank.getDirection() == 1 || enemyTank.getDirection() == 3){
                            //当前坦克左上角坐标[this.getX(),this.getY()]
                            if(this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX()+60
                                    && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY()+40){
                                return true;
                            }
                            //当前坦克左下角的坐标[this.getX(),this.getY()+40]
                            if(this.getX()  >= enemyTank.getX() && this.getX() <= enemyTank.getX()+60
                                    && this.getY()+40 >= enemyTank.getY() && this.getY()+40 <= enemyTank.getY()+40){
                                return true;
                            }
                        }
                    }
                }
                break;
            case 2://下
                for(int i = 0;i < enemyTanks.size();i++){
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if(enemyTank != this){
                        //如果敌人坦克是上下 x[enemyTank.getX(),enemyTank.getX()+40]
                        //                y[enmeyTank.getY(),enemyTank.getY()+60]
                        if(enemyTank.getDirection() == 0 || enemyTank.getDirection() == 2){
                            //当前坦克作下角坐标[this.getX(),this.getY()+60]
                            if(this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX()+40
                                    && this.getY()+60 >= enemyTank.getY() && this.getY()+60 <= enemyTank.getY()+60){
                                return true;
                            }
                            //当前坦克右下角的坐标[this.getX()+40,this.getY()+60]
                            if(this.getX()+40 >= enemyTank.getX() && this.getX()+40 <= enemyTank.getX()+40
                                    && this.getY()+60 >= enemyTank.getY() && this.getY()+60 <= enemyTank.getY()+60){
                                return true;
                            }
                        }
                        //如果敌人坦克是左右 x[enemyTank.getX(),enemyTank.getX()+60]
                        //                y[enmeyTank.getY(),enemyTank.getY()+40]
                        if(enemyTank.getDirection() == 1 || enemyTank.getDirection() == 3){
                            //当前坦克右上角坐标[this.getX()+60,this.getY()]
                            if(this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX()+60
                                    && this.getY()+60 >= enemyTank.getY() && this.getY()+60 <= enemyTank.getY()+40){
                                return true;
                            }
                            //当前坦克右下角的坐标[this.getX()+40,this.getY()+60]
                            if(this.getX()+40 >= enemyTank.getX() && this.getX()+40 <= enemyTank.getX()+60
                                    && this.getY()+60 >= enemyTank.getY() && this.getY()+60 <= enemyTank.getY()+40){
                                return true;
                            }
                        }
                    }
                }
                break;
            case 3://右
                for(int i = 0;i < enemyTanks.size();i++){
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if(enemyTank != this){
                        //如果敌人坦克是上下 x[enemyTank.getX(),enemyTank.getX()+40]
                        //                y[enmeyTank.getY(),enemyTank.getY()+60]
                        if(enemyTank.getDirection() == 0 || enemyTank.getDirection() == 2){
                            //当前坦克右上角坐标[this.getX()+60,this.getY()]
                            if(this.getX() +60 >= enemyTank.getX() && this.getX()+60 <= enemyTank.getX()+40
                                    && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY()+60){
                                return true;
                            }
                            //当前坦克右下角的坐标[this.getX()+60,this.getY()+40]
                            if(this.getX()+60 >= enemyTank.getX() && this.getX()+60 <= enemyTank.getX()+40
                                    && this.getY()+40 >= enemyTank.getY() && this.getY()+40 <= enemyTank.getY()+60){
                                return true;
                            }
                        }
                        //如果敌人坦克是左右 x[enemyTank.getX(),enemyTank.getX()+60]
                        //                y[enmeyTank.getY(),enemyTank.getY()+40]
                        if(enemyTank.getDirection() == 1 || enemyTank.getDirection() == 3){
                            //当前坦克右上角坐标[this.getX()+60,this.getY()]
                            if(this.getX()+60 >= enemyTank.getX() && this.getX()+60 <= enemyTank.getX()+60
                                    && this.getY() >= enemyTank.getY() && this.getY() <= enemyTank.getY()+40){
                                return true;
                            }
                            //当前坦克右下角的坐标[this.getX()+60,this.getY()+40]
                            if(this.getX()+60 >= enemyTank.getX() && this.getX()+60 <= enemyTank.getX()+60
                                    && this.getY()+40 >= enemyTank.getY() && this.getY()+40 <= enemyTank.getY()+40){
                                return true;
                            }
                        }
                    }
                }
                break;
        }
        return false;
    }
}

