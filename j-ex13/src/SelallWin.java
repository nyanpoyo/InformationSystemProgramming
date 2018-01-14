import java.awt.*;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.*;

public class SelallWin extends JFrame {
    private SelallCtrl ctrl;
    private DefaultTableModel tm;
    private JTable tb;
    private JScrollPane sp;
    private final String column_name[] = {"sno", "sname", "address", "age"};
    private Vector<String>[] table_data;

    public SelallWin() {
        ctrl = new SelallCtrl();
        table_data = new Vector[column_name.length];
        getContentPane().setLayout(new FlowLayout());
        String[][] rowData = {};
        String[] colNames = {column_name[0], column_name[1], column_name[2], column_name[3]};
        tm = new DefaultTableModel(rowData, colNames);
        tb = new JTable(tm);
        sp = new JScrollPane(tb);
        sp.setPreferredSize(new Dimension(300, 300));
        getContentPane().add(sp);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("TableModelTest");
        setSize(300, 300);
    }

    public void addDataToTable() {
        for (int i = 0; i < column_name.length; i++) {
            tm.addRow(table_data[i]);
        }
    }

    public void setDataBase(String db_name) {
        ctrl.setDataBase(db_name);
    }

    public void getDataFromTable() {
        table_data = new Vector[column_name.length];
        for (int i = 0; i < column_name.length; i++) {
            table_data[i] = new Vector<String>();
        }
        ctrl.getDataFromTable(column_name, table_data);
    }

    public void executeQuery(String sql) {
        ctrl.executeQuery(sql);
    }

    public static void main(String[] args) {
        String db_name = "jdbc:sqlite:/Users/takumi/Downloads/stock3.s3db";
        SelallWin window = new SelallWin();
        window.setDataBase(db_name);
        window.executeQuery("SELECT * FROM Student");
        window.getDataFromTable();
        window.addDataToTable();
        window.setVisible(true);
    }
}
