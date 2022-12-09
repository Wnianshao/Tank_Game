package Tank_Game;

import java.io.*;
import java.util.Vector;

/**
 * @author wkn
 * @version 1.0
 **/
public class Recoder {
    private static int allEnemyTankNum = 0;
    private static int nowEnemyTankNun = 0;
    //定义IO对象
    private static FileWriter fw = null;
    private static BufferedReader br = null;
    private static BufferedWriter bw = null;
    private static String recordFile = "src\\Tank_Game\\myRecord.txt";
    private static Vector<EnemyTank> enemyTanks = null;
    private static Vector<Node> nodes = new Vector<>();

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recoder.enemyTanks = enemyTanks;
    }

    public static Vector<Node> getNodesAndEnemyTankRec(){
        try {
            br = new BufferedReader(new FileReader(recordFile));
            allEnemyTankNum = Integer.parseInt(br.readLine());
            String line;
            while((line = br.readLine()) !=null){
                String[] xyd = line.split(" ");
                Node node = new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]), Integer.parseInt(xyd[2]));
                nodes.add(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return nodes;
    }


    public static void keepRecord(){

        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(allEnemyTankNum + "\r\n");
            for(int i=0;i< enemyTanks.size();i++){
                EnemyTank enemyTank = enemyTanks.get(i);
                if(enemyTank.isLive()){
                    String record = enemyTank.getX()+" "+enemyTank.getY()+" "+ enemyTank.getDirection();
                    bw.write(record + "\r\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bw != null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recoder.allEnemyTankNum = allEnemyTankNum;
    }

    public static void addAllEnemyTankNum(){
        Recoder.allEnemyTankNum++;
    }

    public static String getRecordFile() {
        return recordFile;
    }

    public static int getNowEnemyTankNun() {
        return nowEnemyTankNun;
    }

    public static void addnowEnemyTankNum(){
        Recoder.nowEnemyTankNun++;
    }
}
