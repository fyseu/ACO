package com.seu.tsp;

import java.util.ArrayList;

/**
 * Created by fangyang on 2018/5/10.
 * 蚂蚁类
 * 使用对象的复制,必须实现Cloneable接口,然后重写Object中clone()方法
 */
public class Ant implements Cloneable{
    public ArrayList<Integer> m_nPath;//蚂蚁走过的路径
    public double m_dbPathLength;//蚂蚁走过的路径长度

//    public int[] m_nAllowedCity=new int[PublicFun.N_CITY_COUNT];//蚂蚁没有去过的城市
    public int[] m_nCityNeedToGo=new int[PublicFun.N_CITY_COUNT];//蚂蚁没有去过的城市
    public int[] m_nCityHadBeen=new int[PublicFun.N_CITY_COUNT];//蚂蚁没有去过的城市
    public int m_nCurCityNo;//当前所在城市的编号
    public int m_nMovedCityCount;//已经去过的城市数量





    /*
     * 初始化函数,蚂蚁搜索前调用该方法
     */
    public void Init()
    {
        for (int i = 0; i < PublicFun.N_CITY_COUNT; i++)
        {
            m_nCityNeedToGo[i] = PublicFun.g_Times[i];
            m_nCityHadBeen[i] = 0;
        }
//        m_nCityNeedToGo = PublicFun.g_Times;//复制地址，相当于引用

        m_dbPathLength=0.0; //蚂蚁走过的路径长度设置为0

//        m_nCurCityNo=PublicFun.rnd(0,PublicFun.N_CITY_COUNT);//随机选择一个出发城市
        m_nCurCityNo=0;//选择0为出发城市
        m_nPath = new ArrayList<Integer>();
        m_nPath.add(m_nCurCityNo);//把出发城市保存的路径数组中

//        m_nAllowedCity[m_nCurCityNo]=0;//标识出发城市已经去过了

        m_nMovedCityCount=1;//已经去过的城市设置为1;
        m_nCityHadBeen[0] = 1;
    }

    /*
     * 覆盖Object中的clone()方法
     * 实现对象的复制
     */
    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        Ant a = (Ant)super.clone();
        int[] m_nCityNeedToGoofA=new int[m_nCityNeedToGo.length];
        for (int i = 0; i < m_nCityNeedToGo.length; i++) {
            m_nCityNeedToGoofA[i] = m_nCityNeedToGo[i];
        }
        a.m_nCityNeedToGo = m_nCityNeedToGoofA;
        int[] m_nCityHadBeenofA=new int[m_nCityHadBeen.length];
        for (int i = 0; i < m_nCityHadBeen.length; i++) {
            m_nCityHadBeenofA[i] = m_nCityHadBeen[i];
        }
        a.m_nCityHadBeen = m_nCityHadBeenofA;
        ArrayList<Integer> m_nPathofA = new ArrayList<Integer>();
        for (int i = 0; i < m_nPath.size(); i++) {
            m_nPathofA.add(m_nPath.get(i));
        }
        a.m_nPath = m_nPathofA;
        return a;
    }
    /*
     * 选择下一个城市
     * 返回值为城市编号
     */
    public int ChooseNextCity()
    {
        int nSelectedCity=-1;//返回结果,初始化为-1

        //计算当前城市和没去过城市的信息素的总和
        double dbTotal=0.0;
        double[] prob=new double[PublicFun.N_CITY_COUNT];//用来保存各个城市被选中的概率

        for (int i = 0; i < PublicFun.N_CITY_COUNT; i++)
        {
                //该城市和当前城市的信息素
                if(PublicFun.g_Distance[m_nCurCityNo][i] == -1){
                    prob[i] = 0.0;
                }else{
                    if(m_nCityNeedToGo[i] > 0){
                        prob[i]=m_nCityNeedToGo[i]*Math.pow(PublicFun.g_Trial[m_nCurCityNo][i], PublicFun.ALPHA)*Math.pow(1.0/PublicFun.g_Distance[m_nCurCityNo][i],PublicFun.BETA-1);
                    }else{
                        prob[i]=Math.pow(PublicFun.g_Trial[m_nCurCityNo][i], PublicFun.ALPHA)*Math.pow(1.0/PublicFun.g_Distance[m_nCurCityNo][i],PublicFun.BETA+1);
                    }
                }
                dbTotal=dbTotal+prob[i];//累加信息素
        }


            //进行轮盘选择

            double dbTemp=0.0;
            if(dbTotal>0.0)//如果总的信息素大于0
            {
                dbTemp=PublicFun.rnd(0.0, dbTotal);//取一个随机数

                for (int i = 0; i < PublicFun.N_CITY_COUNT; i++)
                {

                    dbTemp=dbTemp-prob[i];//相当于轮盘

                    if(dbTemp<=0.0)//轮盘停止转动,记下城市编号,跳出循环
                    {
                        nSelectedCity=i;
                        break;
                    }


                }
//            if(dbTemp == prob[PublicFun.N_CITY_COUNT-1]){
//                nSelectedCity=prob.length-1;
//            }
            }

        /*
         * 如果城市间的信息素非常小 ( 小到比double能够表示的最小的数字还要小 )
         * 那么由于浮点运算的误差原因，上面计算的概率总和可能为0
         * 会出现经过上述操作，没有城市被选择出来
         * 出现这种情况，就把第一个没去过的城市作为返回结果
         */
            if(nSelectedCity==-1)
            {
                for (int i = 0; i < PublicFun.N_CITY_COUNT; i++)
                {
                    if(PublicFun.g_Distance[m_nCurCityNo][i] != -1){
                        nSelectedCity=i;
                        break;
                    }
                }
            }


        return nSelectedCity;
    }

    /*
     * 蚂蚁在城市间移动
     */
    public void Move()
    {
        int nCityNo=ChooseNextCity();//选择下一个城市
//        System.out.println(nCityNo + "+" + m_nCityNeedToGo[0]+"+" + m_nCityNeedToGo[1]+"+" + m_nCityNeedToGo[2]+"+" + m_nCityNeedToGo[3]+"+" + m_nCityNeedToGo[4]+"+" + m_nCityNeedToGo[5]+"+" + m_nCityNeedToGo[6]+"+" + m_nCityNeedToGo[7]);
        m_nPath.add(nCityNo);//保存蚂蚁走过的路径
        if(m_nCityNeedToGo[nCityNo] > 0){
            m_nCityNeedToGo[nCityNo]=m_nCityNeedToGo[nCityNo]-1;//将该路段需要经过次数减1
        }
        m_nCityHadBeen[nCityNo] = m_nCityHadBeen[nCityNo] + 1;
//        m_nAllowedCity[nCityNo]=0;//把这个城市设置为已经去过了
        m_nCurCityNo=nCityNo;//改变当前城市为选择的城市
        m_nMovedCityCount++;//已经去过的城市加1
//        System.out.println(m_nMovedCityCount);
    }

    /*
     * 蚂蚁进行一次搜索
     */
    public void Search()
    {
        Init();//蚂蚁搜索前,进行初始化

        //如果蚂蚁去过的城市数量小于城市数量,就继续移动
//        while (m_nMovedCityCount<PublicFun.N_CITY_COUNT)
        //如果蚂蚁未完成所有路段,或未回到出发点，就继续移动
        boolean finished = false;//标志是否所有访问任务完成

        while (!(finished && m_nCurCityNo == 0))
        {
            Move();//移动
            finished = true;
            for (int i = 0; i < m_nCityNeedToGo.length; i++) {

                if(m_nCityNeedToGo[i] > 0){
                    finished = false;
                    break;
                }
            }
        }
        //完成搜索后计算走过的路径长度
        CalPathLength();
    }

    /*
     * 计算蚂蚁走过的路径长度
     */
    public void CalPathLength()
    {
        m_dbPathLength=0.0;//先把路径长度置0
        int m=0;
        int n=0;

        for(int i=1;i<m_nMovedCityCount;i++)
        {
            m=m_nPath.get(i);
            n=m_nPath.get(i-1);
            m_dbPathLength=m_dbPathLength+PublicFun.g_Distance[n][m];
        }
        //加上从最后城市返回出发城市的距离
//        n=m_nPath.get(0);
//        m_dbPathLength=m_dbPathLength+PublicFun.g_Distance[n][m];
        m_dbPathLength=(Math.round(m_dbPathLength*100))/100.0;
//        System.out.println("length:" + m_dbPathLength);
    }
}
