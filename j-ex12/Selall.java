package com.company;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.table.*;
import java.util.Vector;

class Selall extends JFrame {
    private DefaultTableModel tm;
    private JTable tb;
    private JScrollPane sp;
    private Connection con;
    private Statement st;
    private ResultSet rs;
    final static int STUDENT_TABLE_ROW_NUM = 4;


    Selall(String db_name, String sql) {
        getContentPane().setLayout(new FlowLayout());
        String[][] rowData = {};
        String[] colNames = {"A", "B", "C"};
        tm = new DefaultTableModel(rowData, colNames);
        tb = new JTable(tm);
        sp = new JScrollPane(tb);
        sp.setPreferredSize(new Dimension(300, 300));
        getContentPane().add(sp);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("TableModelTest");
        setSize(250, 120);
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(db_name);
            st = con.createStatement();
            rs = st.executeQuery(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void close() {
        try {
            st.close();
            con.close();
        } catch (SQLException sqle) {
            System.out.println("SQL error");
        }
    }

    public void add(Vector<String> row) {
        tm.addRow(row);
    }

    public void getTableLineContent(String row_name[], Vector<String> content[]) {
        try {
            while (rs.next()) {
                if (row_name.length != content.length) {
                    break;
                }
                for (int i = 0; i < row_name.length; i++) {
                    content[i].add(rs.getString(row_name[i]));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        String db_name = "jdbc:sqlite:/Users/takumi/Downloads/stock3.s3db";
        Selall obj = new Selall(db_name, "SELECT * FROM Student");
        Vector<String>[] student_table = new Vector[STUDENT_TABLE_ROW_NUM];
        String row_name[] = {"sno", "sname", "address", "age"};

        for (int i = 0; i < STUDENT_TABLE_ROW_NUM; i++) { student_table[i] = new Vector<String>(); }
        obj.getTableLineContent(row_name, student_table);
        for (int i = 0; i < STUDENT_TABLE_ROW_NUM; i++) { obj.add(student_table[i]); }
        obj.close(); //最後にcloseしないとデータベースのデータが保存されない
        obj.setVisible(true);
    }
}
