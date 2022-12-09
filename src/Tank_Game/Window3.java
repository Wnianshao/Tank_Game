package Tank_Game;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

/**
 * @author wkn
 * @version 1.0
 **/
public class Window3 extends JFrame{
    //定义MyPannel
    MyPannel mp =null;
    static Scanner sc = new Scanner(System.in);

    public Window3(){
        System.out.println("请输入你的选择");
        System.out.println("1:新游戏  2:继续上局游戏");
        String key = sc.next();
        mp = new MyPannel(key);
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);//把面板（游戏绘图区域）
        this.setSize(1350,1000);
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //在JFrame 增加相应关闭窗口处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recoder.keepRecord();
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        new Window3();
    }

}
