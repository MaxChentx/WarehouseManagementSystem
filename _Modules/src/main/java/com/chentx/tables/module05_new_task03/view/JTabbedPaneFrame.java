package com.chentx.tables.module05_new_task03.view;

import com.chentx.tables.module05_new_task03.dao.CargoDao;
import com.chentx.tables.module05_new_task03.entity.Cargo;
import com.chentx.tables.module05_new_task03.entity.Node;
import com.chentx.tables.module05_new_task03.utils.Database;
import com.chentx.tables.module05_new_task03.utils.DbUtil;
import com.chentx.tables.module05_new_task03.utils.Query;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * 界面显示类
 *
 * @author MaxBrooks 15905898514@163.com
 * @version 2023/3/11 11:08
 * @since JDK17
 */

public class JTabbedPaneFrame implements TreeSelectionListener {

    JFrame frame;
    JPanel panel1;
    JPanel panel2;
    JPanel panel3;
//    JLabel jLabel1;
//    JLabel jLabel2;
//    JLabel jLabel3;
    JTabbedPane jTabbedPane;
    ImageIcon imageIcon;
    // 返回全部字段名称
    String [] tableHead;
    // 返回二维数组，即查询的全部记录
    String [][] content;

    JTree tree;
    DefaultMutableTreeNode[] layerNode = new DefaultMutableTreeNode[9];
    String [] tableHead02;
    String [][] content02 = null;
    JTextArea showText;
    JTable table;
    Query findRecord = new Query();

    JTextField[] textField = new JTextField[4];
    JLabel[] jLabels = new JLabel[4];
    JButton button = new JButton("确定");

    CargoDao cargoDao = null;
    JLabel jl1 = new JLabel("货号");
    JLabel jl2 = new JLabel("货名");
    JLabel jl3 = new JLabel("数量");
    JLabel jl4 = new JLabel("单价");

    // 行
    Box boxH;
    // 列
    Box boxVOne;
    Box boxVTwo;

    Cargo cargo = new Cargo();

    DbUtil db = new DbUtil();
    JTabbedPaneFrame(){
        frame = new JFrame("this is a test");
        // 选项卡
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        // 选项卡的内容，之后被组件覆盖
//        jLabel1 = new JLabel("card one");
//        jLabel2 = new JLabel("card two");
        //采用默认的选项卡面板
        jTabbedPane = new JTabbedPane();

        textField = new JTextField[4];
        jLabels = new JLabel[4];
        initPreparedStatement();



        imageIcon = new ImageIcon("_Modules/src/main/resources/login.png");
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(15, 15, 1));

        initTable();
        initTree();
    }


    public void initPreparedStatement(){
        panel3.setLayout(new FlowLayout());

        boxH = Box.createHorizontalBox();
        boxVOne = Box.createVerticalBox();
        boxVTwo = Box.createVerticalBox();

        boxVOne.add(jl1);
        boxVOne.add(jl2);
        boxVOne.add(jl3);
        boxVOne.add(jl4);

        boxVTwo.add(new JTextField(10));
        boxVTwo.add(new JTextField(10));
        boxVTwo.add(new JTextField(10));
        boxVTwo.add(new JTextField(10));

        boxH.add(boxVOne);
        boxH.add(Box.createHorizontalStrut(10));
        boxH.add(boxVTwo);

        panel3.add(boxH);
        panel3.add(button);

        cargo.setCargoId(jl1.getText());
        cargo.setCargoName(jl2.getText());
//        cargo.setCargoNumber(Integer.parseInt(jl3.getText()));
//        cargo.setCargoAvePrice(Double.valueOf(jl4.getText()));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection con = null;

                try {
                    con = db.getConnection();
                    int addNum = cargoDao.add(con, cargo);

                    if (addNum >= 1) {
                        JOptionPane.showMessageDialog(null, "添加成功");
                    }else {
                        JOptionPane.showMessageDialog(null, "插入失败");
                    }
                    con.close();

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }







    /**
     * 初始化树组件
     */
    public void initTree(){
        get();
        creat();
        tree = new JTree(layerNode[0]);
        tree.addTreeSelectionListener(this);
        showText = new JTextArea();
        panel1.setLayout(new GridLayout(1,2));
//        setBounds(80,80,900,500);
//        Font f = new Font("宋体",Font.PLAIN,22);
//        SetFont.setFont(f, showText, tree);
//        panel1.validate();
    }

    /**
     * 从数据库获取数据，方便后面树组件内容的实现
     */
    void get(){
        Database findRecord = new Database();
        findRecord.setDatabaseName("warehousemanagementsystem");
        findRecord.setSQL("select * from clggb");
        content02 = findRecord.getRecord();
        Logger.getGlobal().info(content02[0][1] + content02[0][2] + content02[0][3] + content02[0][4] + content02[0][5]);
        tableHead02 = findRecord.getColumnName();
        Logger.getGlobal().info(tableHead02[0] + tableHead02[1] + tableHead02[2] + tableHead02[3] + tableHead02[4]);
    }

    /**
     * 创建树及其节点
     */
    void creat(){
        layerNode[0] = new DefaultMutableTreeNode(new Node(content02[0]));
        for(int i = 1,n = content02.length; i < n; i++){
            for(int j = 0,m = content02[i].length;j < m;j++){
                content02[i][2] = content02[i][2].trim();
            }
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(new Node(content02[i]));
            String s = content02[i][2];
            int len = s.length();
            int currentLayer = len / 2;
            layerNode[currentLayer-1].add(node);
            layerNode[currentLayer] = node;
        }
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node=(DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if(node == null) {
            return;
        }
        if(node.isLeaf()){
            Node s = (Node)node.getUserObject();
            showText.append(s.toString() + "\n");
        }else{
            showText.setText(null);
        }
    }

    /**
     * 初始化table界面
     */
    public void initTable(){
        findRecord.setSql("SELECT * FROM clggb");
        content = findRecord.getRecord();
        tableHead = findRecord.getColumnName();
        table = new JTable(content,tableHead);
    }

    public void displayWindow(){
        // BorderLayout
        panel1.setLayout(new BorderLayout());
        panel2.setLayout(new BorderLayout());

        ImageIcon imageIcon1 = new ImageIcon("_Modules/src/main/resources/close.png");
        imageIcon1.setImage(imageIcon1.getImage().getScaledInstance(15, 15, 1));
        ImageIcon imageIcon2 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/close.png")));
        imageIcon2.setImage(imageIcon2.getImage().getScaledInstance(15, 15, 2));

//        panel1.add(jLabel1);
//        panel2.add(jLabel2);
        // 添加
        jTabbedPane.addTab("JTree Pane", imageIcon, panel1);
        jTabbedPane.addTab("JTable Pane", imageIcon, panel2);
        jTabbedPane.addTab("JDBC PreparedStatement", imageIcon, panel3);

        panel1.add(new JScrollPane(tree));
        panel2.add(new JScrollPane(table));

        frame.setContentPane(jTabbedPane);
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        new JTabbedPaneFrame().displayWindow();
    }

}


/*
 * 学习并掌握选项卡JTabbedPane（教材p255),树组件与表格组件（p292）
 * mysql的多关联表的存储过程、触发器编程
 * jdbc的预处理编程
 * jdbc的事务处理编程
 * 提交（word文档）：
 * 4项内容的程序及截图
 *


class tab extends JFrame
{
 tab()
 {
  super("选项卡窗格");
  setVisible(true);
  setSize(500,310);
  setLocationRelativeTo(null);
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  JButton btn = new JButton("X");
  JTabbedPane pane = new JTabbedPane();
  JLabel lab = new JLabel("选项卡I");
  pane.addTab("i",lab);
  pane.setTabComponentAt(pane.indexOfComponent(lab),btn);
  add(pane);
 }
 public static void main(String[] args)
 {
  new tab();
 }
}
 *
 *
 *
 */

