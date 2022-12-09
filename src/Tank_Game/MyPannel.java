package Tank_Game;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

/**
 * @author wkn
 * @version 1.0
 **/
//为了监听键盘事件，实现KeyListener
//为了让Panel不停地重绘子弹，需要将MyPannel实现Runnable接口
public class MyPannel extends JPanel implements KeyListener,Runnable {
    //定义我的坦克
    MyTank myTank = null;
    //定义敌人坦克，放入到集合<Vector>中
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //定义一个存放Node对象的Vector，用于恢复敌人坦克的坐标和方向
    Vector<Node>nodes = new Vector<>();
    //定义敌方坦克被击中时爆炸
    Vector<Bomb> bombs = new Vector<>();
    //定义敌方坦克个数
    int enemyTanks_Size = 6;
    //定义三张图片，用于显示爆炸
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    //初始化面板时所执行的代码
    public MyPannel(String key){
        File file = new File(Recoder.getRecordFile());
        if(file.exists()) {
            nodes = Recoder.getNodesAndEnemyTankRec();
        }else{
            System.out.println("没有上局");
            key = "1";
        }
        Recoder.setEnemyTanks(enemyTanks);

        switch (key){
            case "1":
                myTank = new MyTank(480,880);
                myTank.setSpeed(20);
                //初始化图片对象
                image1 = Toolkit.getDefaultToolkit().getImage(MyPannel.class.getResource("boom1.png"));
                image2 = Toolkit.getDefaultToolkit().getImage(MyPannel.class.getResource("boom2.png"));
                image3 = Toolkit.getDefaultToolkit().getImage(MyPannel.class.getResource("boom3.png"));
                //初始化敌人坦克
                for(int i=0;i<enemyTanks_Size;i++){
                    EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
                    enemyTank.setEnemyTanks(enemyTanks);
                    //设置方向
                    enemyTank.setDirection(2);
                    //启动敌人坦克线程
                    Thread thread = new Thread(enemyTank);
                    thread.start();
                    //创建一颗子弹
                    Shot shot = new Shot(enemyTank.getX() +20, enemyTank.getY()+60, enemyTank.getDirection());
                    //加入enemyTank的Vector 成员
                    enemyTank.getShots().add(shot);
                    new Thread(shot).start();
                    // 坦克子弹成为一个对象一起加进去
                    enemyTanks.add(enemyTank);
                }
                break;
            case "2":
                myTank = new MyTank(480,880);
                myTank.setSpeed(20);
                //初始化图片对象
                image1 = Toolkit.getDefaultToolkit().getImage(MyPannel.class.getResource("boom1.png"));
                image2 = Toolkit.getDefaultToolkit().getImage(MyPannel.class.getResource("boom2.png"));
                image3 = Toolkit.getDefaultToolkit().getImage(MyPannel.class.getResource("boom3.png"));
                //初始化敌人坦克
                for(int i=0;i<nodes.size();i++){
                    Node node = nodes.get(i);
                    EnemyTank enemyTank = new EnemyTank(node.getX(),node.getY());
                    enemyTank.setEnemyTanks(enemyTanks);
                    //设置方向
                    enemyTank.setDirection(node.getDirection());
                    //启动敌人坦克线程
                    Thread thread = new Thread(enemyTank);
                    thread.start();
                    //创建一颗子弹
                    Shot shot = new Shot(enemyTank.getX() +20, enemyTank.getY()+60, enemyTank.getDirection());
                    //加入enemyTank的Vector 成员
                    enemyTank.getShots().add(shot);
                    new Thread(shot).start();
                    // 坦克子弹成为一个对象一起加进去
                    enemyTanks.add(enemyTank);
                }
                break;
            default:
                System.out.println("输入有误");
        }

       new AePlayWave("D:\\WorkSpace\\JavaProject\\Tank_Game\\src\\Tank_Game\\bgm.wav").start();
    }
    public void showInfo(Graphics g){
        //画出玩家总成绩
        g.setColor(Color.BLACK);
        Font font = new Font("宋体",Font.BOLD,25);
        g.setFont(font);

        g.drawString("你已经累计干掉敌方坦克",1001,30);
        g.drawString("本局干掉对方坦克",1001,200);
        Draw_Tank(1010,60,g,0,1);
        Draw_Tank(1010,230,g,0,1);
        g.setColor(Color.BLACK);
        g.drawString(Recoder.getAllEnemyTankNum() + "",1080,120);
        g.drawString(Recoder.getNowEnemyTankNun() + "",1080,290);
    }
    //初始化面板时所显示的东西
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0,0,1000,1000);
        showInfo(g);
        //我方坦克中枪后消失
        if(myTank.isLive() &&  myTank != null) {
            //画出坦克-封装方法
            Draw_Tank(myTank.getX(), myTank.getY(), g, myTank.getDirection(), 0);
        }
//        //画出坦克射击的子弹
//        if(myTank.getShot() != null && myTank.getShot().getIsalive() != false){
//            g.fillOval(myTank.getShot().getX(),myTank.getShot().getY(),5,5);
//        }
        //将myTank子弹集合遍历绘制
        for(int i = 0;i< myTank.getShots().size();i++){
            Shot shot = myTank.getShots().get(i);
            if(shot != null && shot.getIsalive() == true){
                g.fillOval(shot.getX(),shot.getY(),5,5);
            }else{
                myTank.getShots().remove(i);
            }
        }
        //画出爆炸效果
        for(int i=0;i<bombs.size();i++){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Bomb bomb = bombs.get(i);
            //根据当前这个bomb对象的life值去画对应的图片
            if(bomb.life > 6){
                g.drawImage(image3,bomb.getX(),bomb.getY(),70,70,this);
            }else if(bomb.life > 3){
                g.drawImage(image2,bomb.getX(),bomb.getY(),70,70,this);
            }else{
                g.drawImage(image1,bomb.getX(),bomb.getY(),70,70,this);
            }
            bomb.lifeDown();
            if(bomb.life == 0){
                bombs.remove(bomb);
            }
        }
        //画出敌人的坦克
        for(int i = 0;i< enemyTanks.size();i++){
            EnemyTank enemyTank = enemyTanks.get(i);
            if(enemyTank.isLive()) {
                Draw_Tank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirection(), 1);
                //画出所有与子弹
                for (int j = 0; j < enemyTank.getShots().size(); j++) {
                    Shot shot = enemyTank.getShots().get(j);
                    if (shot.getIsalive()) {
                        g.fillOval(shot.getX(), shot.getY(), 5, 5);
                    } else {
                        //坦克子弹被判断消失后移除坦克子弹
                        enemyTank.getShots().remove(shot);
                    }
                }
            } else{
                enemyTanks.remove(i);
            }
        }
    }

    //如果我们的坦克可以发射多个子弹,在判断我方子弹是否击中敌人坦克时,就需要把我们的子弹集合中所有的子弹，都取出和敌人的所有坦克,进行判断
    public void hitEnemyTank(){
        //遍历子弹
        for(int j=0;j<myTank.getShots().size();j++) {
            Shot shot = myTank.getShots().get(j);
            //判断是否击中敌人坦克
            if (shot != null && shot.getIsalive()) {
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    hitTank(shot, enemyTank);
                }
            }
        }
    }

    //编写方法，判断敌人坦克是否击中我防坦克
    public void hitMe(){
        //遍历所有敌人坦克
        for(int i=0;i<enemyTanks.size();i++){
            EnemyTank enemyTank = enemyTanks.get(i);
            //遍历enemyTank 对象所有子弹
            for(int j=0;j<enemyTank.getShots().size();j++){
                //取出子弹
                Shot shot = enemyTank.getShots().get(j);
                //判断shot是否击中我的坦克
                if(myTank.isLive() && shot.getIsalive()){
                    hitTank(shot,myTank);
                }
            }
        }
    }

    //编写方法，判断我方子弹击中坦克
    public  void hitTank(Shot s,Tank enemyTank){
        switch (enemyTank.getDirection()){
            case 0:
            case 2:
                if(s.getX() >= enemyTank.getX() && s.getX() <= enemyTank.getX()+40
                && s.getY() >= enemyTank.getY() && s.getY() <= enemyTank.getY()+60){
                    s.setIsalive(false);
                    enemyTank.setLive(false);
                    //创建Bomb对象，加入到bombs集合
                    //敌方坦克被击毁，count++
                    if(enemyTank instanceof EnemyTank){
                        Recoder.addAllEnemyTankNum();
                        Recoder.addnowEnemyTankNum();
                    }
                    enemyTanks.remove(enemyTank);
                    Bomb bomb = new Bomb(enemyTank.getX(),enemyTank.getY());
                    bombs.add(bomb);
                }
                break;
            case 1:
            case 3:
                if(s.getX() > enemyTank.getX() && s.getX() < enemyTank.getX()+60
                        && s.getY() > enemyTank.getY() && s.getY() < enemyTank.getY()+40){
                    s.setIsalive(false);
                    enemyTank.setLive(false);
                    if(enemyTank instanceof EnemyTank){
                        Recoder.addAllEnemyTankNum();
                        Recoder.addnowEnemyTankNum();
                    }
                    enemyTanks.remove(enemyTank);
                    Bomb bomb = new Bomb(enemyTank.getX(),enemyTank.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }


    //处理wasd键按下的情况
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {//按下W键
            //改变坦克方向
            myTank.setDirection(0);
            //修改坦克的坐标
            if(myTank.getY() > 10) {
                myTank.moveUp();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {//按下A键
            myTank.setDirection(1);
            if(myTank.getX() > 10) {
                myTank.moveLeft();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {//按下S键
            myTank.setDirection(2);
            if(myTank.getY()+70 < 1000) {
                myTank.moveDown();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) {//按下D键
            myTank.setDirection(3);
            if(myTank.getX()+70 < 1000) {
                myTank.moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_J){
            myTank.shotEnemyTank();
        }
        this.repaint();
    }

    //执行面板线程时所运行的代码
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            hitEnemyTank();
            hitMe();
            this.repaint();
        }
    }

    //编写方法，画出坦克
    /**
     *
     * @param x //坦克左上角横坐标
     * @param y //坦克左上角纵坐标
     * @param g //画笔
     * @param direction //坦克方向
     * @param type //坦克类型
     */
    public void Draw_Tank(int x,int y,Graphics g,int direction,int type){
        //根据不同的坦克类型给它设置颜色
        switch (type){
            case 0://我们的坦克
                g.setColor(Color.cyan);
                break;
            case 1://敌人的坦克
                g.setColor(Color.yellow);
                break;
        }
        //根据坦克方向来绘制坦克
        switch (direction){
            case 0://坦克朝上
                g.fill3DRect(x,y,10,60,false);//坦克左轮
                g.fill3DRect(x+30,y,10,60,false);//坦克右轮
                g.fill3DRect(x+10,y+10,20,40,false);//坦克中舱
                g.fillOval(x+10,y+20,20,20);//坦克圆盖
                g.drawLine(x+20,y+40,x+20,y-10);//坦克炮筒
                break;
            case 1://坦克朝左
                g.fill3DRect(x,y,60,10,false);//坦克右轮
                g.fill3DRect(x,y+30,60,10,false);//坦克左轮
                g.fill3DRect(x+10,y+10,40,20,false);//坦克中仓
                g.fillOval(x+20,y+10,20,20);//坦克圆盖
                g.drawLine(x+30,y+20,x-10,y+20);//坦克炮筒
                break;
            case 2://坦克朝下
                g.fill3DRect(x,y,10,60,false);//坦克左轮
                g.fill3DRect(x+30,y,10,60,false);//坦克右轮
                g.fill3DRect(x+10,y+10,20,40,false);//坦克中舱
                g.fillOval(x+10,y+20,20,20);//坦克圆盖
                g.drawLine(x+20,y+40,x+20,y+70);//坦克炮筒
                break;
            case 3://坦克朝右
                g.fill3DRect(x,y,60,10,false);//坦克右轮
                g.fill3DRect(x,y+30,60,10,false);//坦克左轮
                g.fill3DRect(x+10,y+10,40,20,false);//坦克中仓
                g.fillOval(x+20,y+10,20,20);//坦克圆盖
                g.drawLine(x+30,y+20,x+70,y+20);//坦克炮筒
                break;
            default:
                System.out.println("暂时没有处理");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
