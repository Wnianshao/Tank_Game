package Tank_Game;

/**
 * @author wkn
 * @version 1.0
 **/
public class Shot implements Runnable{
    private int x;
    private int y;
    private int direction;
    private int speed = 5;
    private boolean isalive = true;

    public Shot(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch (direction){
                case 0:
                    y -= speed;
                    break;
                case 1:
                    x -= speed;
                    break;
                case 2:
                    y += speed;
                    break;
                case 3:
                    x += speed;
                    break;
            }
            System.out.println("x:"+x+"  y:"+y);
            if(!(x >= 0 && x<= 1000 && y >=0 && y <= 1000 && isalive)){
                isalive = false;
                break;
            }
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setIsalive(boolean isalive) {
        this.isalive = isalive;
    }

    public int getY() {
        return y;
    }


    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean getIsalive() {
        return isalive;
    }


}
