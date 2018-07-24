package com.seu.tsp;

import java.io.*;

/**
 * Created by fangyang on 2018/5/10.
 * 公共函数以及变量
 */
public class PublicFun {
    public static final double ALPHA=1.0;//信息启发式因子，信息素的重要程度
    public static final double BETA=2.0;//期望启发式因子, 城市间距离的重要程度
    public static final double ROU=0.5;//信息素残留系数

    public static  int N_ANT_COUNT=50;//蚂蚁数量
    public static  int N_IT_COUNT=200;//迭代次数
    public static  int N_CITY_COUNT=8;//城市数量

    public static final double DBQ=100.0;//总信息素
    public static final double DB_MAX=Math.pow(10,9);//一个标志数,用来初始化一个比较大的最优路径

    //两两城市间的信息量
    public static double[][] g_Trial;

    //两两城市间的距离//初始化图（不可通行置-1）
//    public static double[][] g_Distance = {
//            {-1,29,13,23,78,90,70,10,80,12,32,98,10,12,10,85,100},
//            {-1,-1,13,23,78,20,70,10,80,12,32,98,10,12,10,85,100},
//            {-1,-1,-1,23,78,20,70,10,80,12,32,98,10,12,10,85,100},
//            {-1,-1,-1,-1,78,20,70,10,80,12,32,98,10,12,10,85,100},
//            {-1,-1,-1,-1,-1,20,70,10,80,12,32,98,10,12,10,85,100},
//            {-1,-1,-1,-1,-1,-1,70,10,80,12,32,98,10,12,10,85,100},
//            {-1,-1,-1,-1,-1,-1,-1,10,80,12,32,98,10,12,10,85,100},
//            {-1,-1,-1,-1,-1,-1,-1,-1,80,12,32,98,10,12,10,85,100},
//            {-1,-1,-1,-1,-1,-1,-1,-1,-1,12,32,98,10,12,10,85,100},
//            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,32,98,10,12,10,85,100},
//            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,98,10,12,10,85,100},
//            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,10,12,10,85,100},
//            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,12,10,85,100},
//            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,10,85,100},
//            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,85,100},
//            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,100},
//            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}
//    };
//    public static double[][] g_Distance = {
//            {-1,29,-1,-1,-1,-1,-1,-1},
//            {10,-1,13,23,-1,-1,10,10},
//            {-1,-1,-1,23,54,-1,-1,-1},
//            {-1,-1,-1,-1,51,-1,-1,-1},
//            {81,80,-1,-1,-1,20,-1,-1},
//            {-1,32,-1,-1,-1,-1,-1,-1},
//            {-1,-1,-1,-1,66,-1,-1,-1},
//            {-1,-1,-1,-1,22,-1,-1,-1}
//    };
    public static double[][] g_Distance;
    //设置路段测试次数
    public static int[] g_Times;
//    public static int[] g_Times = {0,0,0,0,0,0,0,0};
    //返回指定范围内的随机整数
    public static int roadTimes()
    {
        int roadTimes = 0;
        for (int i = 0; i < g_Times.length; i++) {
            roadTimes += g_Times[i];
        }
        return roadTimes;
    }

    //返回指定范围内的随机整数
    public static int rnd(int nLow,int nUpper)
    {
        return (int) (Math.random()*(nUpper-nLow)+nLow);
    }
    //返回指定范围内的随机浮点数
    public static double rnd(double dbLow,double dbUpper)
    {
        return Math.random()*(dbUpper-dbLow)+dbLow;
    }
    //读取配置文件中的图信息与调度信息
    public static void read(String filePath){
        try{
            String encoding = "GBK";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                if ((lineTxt = bufferedReader.readLine()) != null) {
                    String[] arr=lineTxt.split(",");
                    for (int j = 0; j < arr.length; j++) {
                        N_CITY_COUNT = Integer.parseInt(arr[j]);
                        System.out.println(N_CITY_COUNT);
                    }

                }
                g_Distance = new double[N_CITY_COUNT][N_CITY_COUNT];
                for (int i =0;(lineTxt = bufferedReader.readLine()) != null&&i < N_CITY_COUNT;i ++) {
                    String[] arr=lineTxt.split(",");
                    for (int j = 0; j < arr.length; j++) {
                        g_Distance[i][j] = Double.parseDouble(arr[j]);
                        System.out.print(Double.parseDouble(arr[j]) + " ");
                    }
                    System.out.println("");
                }
                g_Times = new int[N_CITY_COUNT];
                if ((lineTxt = bufferedReader.readLine()) != null) {
                    System.out.println("g_times");
                    String[] arr=lineTxt.split(",");
                    for (int j = 0; j < arr.length; j++) {
                        g_Times[j] = Integer.parseInt(arr[j]);
                        System.out.print(g_Times[j] + " ");
                    }

                }
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
    }
}
}
