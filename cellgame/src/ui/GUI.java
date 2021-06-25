package ui;
import javax.swing.*;

import cell.Cell;

import java.awt.*;
import java.awt.event.*;
public class GUI extends JFrame implements ActionListener {
    private static GUI frame;
    private Cell cell;
    public static int maxLength=30; //���Ϳ�
    public static int maxWidth=20;
    private JButton[][] nGrid; //һ����ť��ʾһ��ϸ��
    private boolean[][] isSelected;  //��ť����ϸ�����Ƿ�ѡ��
    private JButton jbNowGeneration, randomInit, clearGeneration; //��ǰ��������������
    private JButton clearCell, nextGeneration, start, stop; //��һ������ʼ���ܣ���ͣ���˳�
    private boolean isRunning;
    private Thread thread;
    private boolean isDead;
    JLabel jWidth, jLength, jNowGeneration;
   

    public static void main(String arg[]) {
        frame = new GUI("������Ϸ");
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public void initGUI() {
       
            //maxWidth = 20;
           // maxLength = 30;
       

        cell = new Cell(maxWidth, maxLength);

        JPanel backPanel, centerPanel, bottomPanel;
        //JLabel jNowGeneration;
        //JLabel jWidth, jLength, jNowGeneration;
        backPanel = new JPanel(new BorderLayout());
        centerPanel = new JPanel(new GridLayout(maxWidth, maxLength));
        bottomPanel = new JPanel();
        this.setContentPane(backPanel);
        backPanel.add(centerPanel, "Center");
        backPanel.add(bottomPanel, "South");

        nGrid = new JButton[maxWidth][maxLength];
        isSelected = new boolean[maxWidth][maxLength];
        for (int i = 0; i < maxWidth; i++) {
            for (int j = 0; j < maxLength; j++) {
                nGrid[i][j] = new JButton(""); //��ť�����ÿ��Ա�ʾϸ��
                nGrid[i][j].setBackground(Color.WHITE); //��ʼʱ����ϸ����Ϊ��
                centerPanel.add(nGrid[i][j]);
            }
        }
//        for (int i = 3; i <= 100; i++)
//            lengthList.addItem(String.valueOf(i));
//        for (int i = 3; i <= 100; i++)
//            widthList.addItem(String.valueOf(i));
        jNowGeneration = new JLabel(" ��ǰ������");
        jbNowGeneration = new JButton(""+cell.getNowGeneration());//Buttom����ֱ�����int���ʲ��ô˷�ʽ
        jbNowGeneration.setEnabled(false);
        clearGeneration = new JButton("��������");
        randomInit = new JButton("�����ʼ��");
        clearCell = new JButton("ϸ������");
        start = new JButton("\u81EA\u52A8\u7E41\u884D");
        nextGeneration = new JButton("��һ��");
        stop = new JButton("��ͣ");
        bottomPanel.add(jNowGeneration);
        bottomPanel.add(jbNowGeneration);
        bottomPanel.add(clearGeneration);
        bottomPanel.add(randomInit);
        bottomPanel.add(clearCell);
        bottomPanel.add(start);
        bottomPanel.add(nextGeneration);
        bottomPanel.add(stop);


        // ���ô���
        this.setSize(800, 650);
        this.setResizable(true);
        this.setLocationRelativeTo(null); // �ô�������Ļ����
        this.setVisible(true);// ����������Ϊ�ɼ���

        // ע�������
        this.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });
        clearGeneration.addActionListener(this);
        randomInit.addActionListener(this);
        clearCell.addActionListener(this);
        nextGeneration.addActionListener(this);
        start.addActionListener(this);
        stop.addActionListener(this);
        for (int i = 0; i < maxWidth; i++) {
            for (int j = 0; j < maxLength; j++) {
                nGrid[i][j].addActionListener(this);
            }
        }
    }

    public GUI(String name) {
        super(name);
        initGUI();
    }

    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == ok) { //ȷ��
//            frame.setMaxLength(lengthList.getSelectedIndex() + 3);//���õĳ�ʼ��СֵΪ3���ʼ���3����ͬ
//            frame.setMaxWidth(widthList.getSelectedIndex() + 3);
//            initGUI();
//
//            cell = new Cell(getMaxWidth(), getMaxLength());
//
//        }
        if(e.getSource() == clearGeneration){ //��������
            cell.setNowGeneration(0);
            jbNowGeneration.setText(""+cell.getNowGeneration());//ˢ�µ�ǰ����
            isRunning = false;
            thread = null;
        } else if(e.getSource() == randomInit){ //�����ʼ��
            cell.randomCell();
            showCell();
            isRunning = false;
            thread = null;
        } else if(e.getSource() == clearCell){ //ϸ������
            cell.deleteAllCell();
            showCell();
            isRunning = false;
            thread = null;
        } else if (e.getSource() == start) { //��ʼ
            isRunning = true;
            thread = new Thread(new Runnable() {
                public void run() {
                    while (isRunning) {
                        makeNextGeneration();
                        try {
                            Thread.sleep(1200);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        isDead = true;
                        for(int row = 1; row <= maxWidth; row++) {
                            for (int col = 1; col <= maxLength; col++) {
                                if (cell.getGrid()[row][col] != 0) {
                                    isDead = false;
                                    break;
                                }
                            }
                            if (!isDead) {
                                break;
                            }
                        }
                        if (isDead) {
                            JOptionPane.showMessageDialog(null, "����ϸ��������");
                            isRunning = false;
                            thread = null;
                        }
                    }
                }
            });
            thread.start();
        } else if (e.getSource() == nextGeneration) { //��һ��
            makeNextGeneration();
            isRunning = false;
            thread = null;
        } else if (e.getSource() == stop) { //��ͣ
            isRunning = false;
            thread = null;
        } else {
            int[][] grid = cell.getGrid();
            for (int i = 0; i < maxWidth; i++) {
                for (int j = 0; j < maxLength; j++) {
                    if (e.getSource() == nGrid[i][j]) {
                        isSelected[i][j] = !isSelected[i][j];
                        if (isSelected[i][j]) {
                            nGrid[i][j].setBackground(Color.BLACK);
                            grid[i + 1][j + 1] = 1;
                        } else {
                            nGrid[i][j].setBackground(Color.WHITE);
                            grid[i + 1][j + 1] = 0;
                        }
                        break;
                    }
                }
            }
            cell.setGrid(grid);
        }
    }

    private void makeNextGeneration() {
        cell.update();
        showCell();
        jbNowGeneration.setText(""+cell.getNowGeneration());//ˢ�µ�ǰ����
    }

    public void showCell(){
        int[][] grid = cell.getGrid();
        for (int i = 0; i < maxWidth; i++) {
            for (int j = 0; j < maxLength; j++) {
                if (grid[i + 1][j + 1] == 1) {
                    nGrid[i][j].setBackground(Color.BLACK); //����ʾ��ɫ
                } else {
                    nGrid[i][j].setBackground(Color.WHITE); //������ʾ��ɫ
                }
            }
        }
    }

}

