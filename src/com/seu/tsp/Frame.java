package com.seu.tsp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by fangyang on 2018/5/10.
 * 界面实现
 */
@SuppressWarnings("serial")
public class Frame extends JFrame{
    JLabel headLabel;
    JLabel antLabel;
    JLabel itLabel;
    JLabel cityLabel;
    JLabel betterAntLabel;
    JLabel bestAntLabel;
    JTextField antTF;
    JTextField itTF;
    JTextField cityTF;
    JTextArea betterAntTA;
    JTextArea bestAntTA;
    JButton beginBtn;
    JScrollPane betterAntScroll;
    JScrollPane bestAntScroll;

    JLabel descLabel;
    public Frame()
    {
        Init();
    }
    /*
     * 界面初始化函数
     */
    private void Init()
    {
//        String descStr = "<html><font color=green>说明:<br/>本测试程序两两城市间距离为随机生成20.0~100.0之间的随机浮点数<font/><br/><br/><br/><br/><font color=black>&copy;2018 Seu</font></html>";
        headLabel=new JLabel("蚁群算法解决试验路段调度问题演示程序");
        antLabel=new JLabel("请输入蚂蚁数量:");
        itLabel=new JLabel("请输入迭代次数:");
//        cityLabel=new JLabel("请输入城市数量:");
        betterAntLabel=new JLabel("路段图");
        bestAntLabel=new JLabel("最佳路径以及长度");
        antTF=new JTextField("50");
        itTF=new JTextField("200");
//        cityTF=new JTextField("8");
        betterAntTA = new JTextArea();
        bestAntTA=new JTextArea();
        beginBtn=new JButton("开始运行");
        betterAntScroll=new JScrollPane(betterAntTA);
        bestAntScroll=new JScrollPane(bestAntTA);

//        descLabel=new JLabel(descStr);
//        descLabel.setBounds(20,220,150,150);

        setBounds(300, 150, 600, 400);
        setTitle("蚁群算法解决试验路段调度问题演示程序");
        setLayout(null);
        setResizable(false);

        headLabel.setBounds(0, 0, 600, 50);
        headLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headLabel.setFont(new Font("隶书", Font.BOLD, 20));

        antLabel.setBounds(20, 60, 100, 20);
//        cityLabel.setBounds(20, 100, 100, 20);
        itLabel.setBounds(20, 100, 100, 20);

        antTF.setBounds(125, 60, 50, 20);
//        cityTF.setBounds(125, 100, 50, 20);
        itTF.setBounds(125, 100, 50, 20);

        beginBtn.setBounds(40, 180, 110, 25);

        betterAntLabel.setBounds(200, 60, 200, 20);
        betterAntScroll.setBounds(200, 80, 380, 200);

        bestAntLabel.setBounds(20, 280, 200, 20);
        bestAntLabel.setForeground(Color.red);
        bestAntScroll.setBounds(20, 300, 550, 40);
        bestAntTA.setBackground(Color.CYAN);
        bestAntTA.setFont(new Font("微软雅黑", Font.BOLD, 13));

        getContentPane().add(headLabel);
        getContentPane().add(antLabel);
        getContentPane().add(antTF);
        getContentPane().add(itLabel);
        getContentPane().add(itTF);
//        getContentPane().add(cityLabel);
//        getContentPane().add(cityTF);
        getContentPane().add(beginBtn);
        getContentPane().add(betterAntLabel);
        getContentPane().add(betterAntScroll);
        getContentPane().add(bestAntLabel);
        getContentPane().add(bestAntScroll);
//        getContentPane().add(descLabel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        beginBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    Begin();
                } catch (CloneNotSupportedException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
        setVisible(true);
    }

    /*
     * 开始运算
     */
    public void Begin() throws CloneNotSupportedException
    {
        betterAntTA.setText("");
        bestAntTA.setText("");
        String antCountStr=antTF.getText().replaceAll("[^\\d]", "");//去除所有非数字字符
        String itCountStr=itTF.getText().replaceAll("[^\\d]", "");
//        String cityCountStr=cityTF.getText().replaceAll("[^\\d]", "");

        if(!antCountStr.equals("")&&!itCountStr.equals(""))//不为"";
        {
            PublicFun.N_ANT_COUNT=Integer.parseInt(antCountStr);// 转换为数字
            PublicFun.N_IT_COUNT=Integer.parseInt(itCountStr);
//            PublicFun.N_CITY_COUNT=Integer.parseInt(cityCountStr);
        }

        /*
         * 实例化Tsp
         */
        PublicFun.read("D:\\学习资料区\\专利相关\\ACO\\src\\com\\seu\\tsp\\config.txt");
        /*
        * 把图显示在窗口中
         */
        for (int i = 0; i < PublicFun.N_CITY_COUNT; i++) {
            for (int j = 0; j < PublicFun.N_CITY_COUNT; j++) {
                betterAntTA.append(PublicFun.g_Distance[i][j] + "   ");
            }
            betterAntTA.append("\n");
        }
        betterAntTA.append("各路段目标次数：\n");
        for (int i = 0; i < PublicFun.N_CITY_COUNT; i++) {
            betterAntTA.append(PublicFun.g_Times[i] + "   ");
        }
        betterAntTA.setCaretPosition(0);
        Tsp tsp=new Tsp();
        tsp.InitData();//开始执行

//        /*
//         * 把结果显示在窗口中
//         */
//        for (int i = 0; i < tsp.m_betterAnts.length; i++)
//        {
//            betterAntTA.append("("+(i+1)+") 路径:"+tsp.getAntPath(tsp.m_betterAnts[i])+"长度:"+tsp.getAntLength(tsp.m_betterAnts[i])+"路段统计:"+tsp.getAntPaths(tsp.m_betterAnts[i])+"\n");
//        }
//        betterAntTA.setCaretPosition(0);
        bestAntTA.append("路径:"+tsp.getAntPath(tsp.m_bestAnt)+"长度:"+tsp.getAntLength(tsp.m_bestAnt)+"路段统计:"+tsp.getAntPaths(tsp.m_bestAnt));
        bestAntTA.setCaretPosition(0);


    }
}
