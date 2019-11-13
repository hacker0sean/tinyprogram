package client.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import client.gui.util.frameUtil;
import client.service.dao.consumeDao;
import client.service.impl.consumImpl;

import java.awt.*;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.jfree.chart.*;
import org.jfree.data.general.DefaultPieDataset;
/**
 * @author tinysean
 */
public class statistics extends javax.swing.JFrame {

    /**
     * Creates new form statistics
     */
    private String cardNumber;
    private consumeDao consumedao;
    private Object[][] consumetable;
    private Calendar[] startArray;
    private Calendar[] endArray;

    public statistics(String cardNumber) {
        try {
            this.cardNumber = cardNumber;
            this.consumedao = new consumImpl();
            this.consumetable = consumedao.listTable(this.cardNumber);
            this.startArray = new Calendar[consumetable.length];
            this.endArray = new Calendar[consumetable.length];
            for (int i = 0; i < consumetable.length; i++) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String startTime = (String) consumetable[i][0];
                String endTime = (String) consumetable[i][1];
                Date startDate = sdf.parse(startTime.substring(0, startTime.length()-2));
                Date endDate = sdf.parse(endTime.substring(0, endTime.length()-2));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startDate);
                this.startArray[i] = calendar;
                calendar.setTime(endDate);
                this.endArray[i] = calendar;
            }
            frameUtil.setCenter(this);
            initComponents();
        }catch(ParseException e){
            e.printStackTrace();
        }
    }

    public void generatePieChart() {
        DefaultPieDataset dpd = new DefaultPieDataset(); // 建立一个默认的饼图
        int[] count = new int[24];
        for(int i = 0; i < 24; i++)
            count[i] = 0;
        for(int i = 0; i < this.startArray.length; i++){
            int start = startArray[i].get(Calendar.HOUR_OF_DAY);
            int end = endArray[i].get(Calendar.HOUR_OF_DAY);
            if(start <= end) {
                for (int j = start; j <= end; j++) {
                    count[j]++;
                }
            }else{
                for(int j = 0; j <= end; j++)
                    count[j]++;
                for(int j = start; j < 24; j++)
                    count[j]++;
            }
        }
        for(int i = 0; i < 24; i++) {
            if (count[i] > 0) {
                dpd.setValue(Integer.valueOf(i) + "~" + Integer.valueOf(i + 1), count[i]);
            }
        }
        StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
        standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 20));
        standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 15));
        standardChartTheme.setLargeFont(new Font("宋书", Font.PLAIN, 15));
        ChartFactory.setChartTheme(standardChartTheme);
        JFreeChart chart = ChartFactory.createPieChart("上网时间分布饼状图", dpd, true, true, false);
        ChartPanel localChartPanel = new ChartPanel(chart, false);
        this.container.addTab("可视化", localChartPanel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    public int lastMonthTimes() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date monthBefore = calendar.getTime();
        int count = 0;
        for(int i = 0; i < this.startArray.length; i++){
            Date compare = (this.startArray[i]).getTime();
            if (compare.compareTo(monthBefore) > 0){
                count++;
            }
        }
        return count;
    }

    public String lastMonthCost() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date monthBefore = calendar.getTime();
        double cost = 0;
        for(int i = 0; i < this.startArray.length; i++){
            Date compare = (this.startArray[i]).getTime();
            if (compare.compareTo(monthBefore) > 0){
                cost += (Double) this.consumetable[i][2];
            }
        }
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(3);
        nf.setRoundingMode(RoundingMode.UP);
        return nf.format(cost);
    }

    public int lastWeekTimes() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);
        Date monthBefore = calendar.getTime();
        int count = 0;
        for(int i = 0; i < this.startArray.length; i++){
            Date compare = (this.startArray[i]).getTime();
            if (compare.compareTo(monthBefore) > 0){
                count++;
            }
        }
        return count;
    }


    public String lastWeekCost() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);
        Date monthBefore = calendar.getTime();
        double cost = 0;
        for(int i = 0; i < this.startArray.length; i++){
            Date compare = (this.startArray[i]).getTime();
            if (compare.compareTo(monthBefore) > 0){
                cost += (Double) this.consumetable[i][2];
            }
        }
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(3);
        nf.setRoundingMode(RoundingMode.UP);
        return nf.format(cost);
    }

    private void initComponents() {
//        setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
        container = new javax.swing.JTabbedPane();
        visualization = new javax.swing.JPanel();
        form = new javax.swing.JPanel();
        lastWeek = new javax.swing.JLabel();
        lastMonth = new javax.swing.JLabel();
        monthTimeText = new javax.swing.JTextField();
        monthCostText = new javax.swing.JTextField();
        weekTimeText = new javax.swing.JTextField();
        weekCostText = new javax.swing.JTextField();
        monthTimes = new javax.swing.JLabel();
        monthCost = new javax.swing.JLabel();
        weekTimes = new javax.swing.JLabel();
        weekCost = new javax.swing.JLabel();
        consumeTable = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout visualizationLayout = new javax.swing.GroupLayout(visualization);
        visualization.setLayout(visualizationLayout);
        visualizationLayout.setHorizontalGroup(
                visualizationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 805, Short.MAX_VALUE)
        );
        visualizationLayout.setVerticalGroup(
                visualizationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 603, Short.MAX_VALUE)
        );
        generatePieChart();


        lastWeek.setText("最近一周");

        lastMonth.setText("最近一月");


        monthTimeText.setEnabled(false);
        monthTimeText.setText(Integer.toString(lastMonthTimes()));
        monthCostText.setEnabled(false);
        monthCostText.setText(lastMonthCost());
        weekTimeText.setEnabled(false);
        weekTimeText.setText(Integer.toString(lastWeekTimes()));
        weekCostText.setEnabled(false);
        weekCostText.setText(lastWeekCost());

        monthTimes.setText("次数");

        monthCost.setText("花费");

        weekTimes.setText("次数");

        weekCost.setText("花费");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                this.consumetable,
                new String[]{
                        "开始时间", "结束时间", "花费", "状态"
                }
        ) {
            Class[] types = new Class[]{
                    java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        });
        consumeTable.setViewportView(jTable1);

        javax.swing.GroupLayout formLayout = new javax.swing.GroupLayout(form);
        form.setLayout(formLayout);
        formLayout.setHorizontalGroup(
                formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(formLayout.createSequentialGroup()
                                .addGap(164, 164, 164)
                                .addComponent(lastMonth)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lastWeek)
                                .addGap(186, 186, 186))
                        .addGroup(formLayout.createSequentialGroup()
                                .addGap(70, 70, 70)
                                .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(monthCost)
                                        .addComponent(monthTimes))
                                .addGap(27, 27, 27)
                                .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(monthCostText, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(monthTimeText, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(formLayout.createSequentialGroup()
                                                .addComponent(weekCost, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(weekCostText, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(formLayout.createSequentialGroup()
                                                .addComponent(weekTimes)
                                                .addGap(51, 51, 51)
                                                .addComponent(weekTimeText, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(122, 122, 122))
                        .addGroup(formLayout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(consumeTable, javax.swing.GroupLayout.PREFERRED_SIZE, 719, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(41, Short.MAX_VALUE))
        );
        formLayout.setVerticalGroup(
                formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, formLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(consumeTable, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lastMonth)
                                        .addComponent(lastWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(weekTimes)
                                        .addComponent(weekTimeText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(monthTimes)
                                        .addComponent(monthTimeText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(24, 24, 24)
                                .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(monthCost)
                                        .addComponent(weekCost)
                                        .addComponent(monthCostText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(weekCostText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(60, 60, 60))
        );

        container.addTab("表格", form);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(container)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(container, javax.swing.GroupLayout.PREFERRED_SIZE, 649, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(statistics.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(statistics.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(statistics.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(statistics.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new statistics("123").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JScrollPane consumeTable;
    private javax.swing.JTabbedPane container;
    private javax.swing.JPanel form;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lastMonth;
    private javax.swing.JLabel lastWeek;
    private javax.swing.JLabel monthCost;
    private javax.swing.JTextField monthCostText;
    private javax.swing.JTextField monthTimeText;
    private javax.swing.JLabel monthTimes;
    private javax.swing.JPanel visualization;
    private javax.swing.JLabel weekCost;
    private javax.swing.JTextField weekCostText;
    private javax.swing.JTextField weekTimeText;
    private javax.swing.JLabel weekTimes;
    // End of variables declaration
}
