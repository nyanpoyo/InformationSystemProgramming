import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SearchView extends JFrame implements ActionListener {
    private SearchCtrl ctrl;
    private Container container;
    private JLabel label;
    private JTextField text_search;
    private JButton btn_select, btn_quit, btn_clear;
    private DefaultTableModel tm;
    private JTable tb;
    private JScrollPane sp;
    private Vector<String>[] column_data;
    private int db_data_num = 0;

    private final String[] column_name = {"sno", "sname", "address", "age"};

    public SearchView() {
        ctrl = new SearchCtrl("jdbc:sqlite:/Users/takumi/Downloads/stock3.s3db");

        container = getContentPane();
        label = new JLabel("検索条件を入力してください");
        text_search = new JTextField("", 16);
        btn_select = new JButton("search");
        btn_clear = new JButton("clear");
        btn_quit = new JButton("quit");
        ctrl.setColumnName(column_name);
        ctrl.setSearchedTableName("Student");
        db_data_num = ctrl.getDBDataNum();

        column_data = new Vector[db_data_num];
        for (int i = 0; i < db_data_num; i++) {
            column_data[i] = new Vector<String>();
        }

        String[][] rowData = {};
        String[] colNames = {column_name[0], column_name[1], column_name[2], column_name[3]};
        container.setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("SeachDB");
        setSize(400, 450);

        container.add(label);
        container.add(text_search);
        container.add(btn_select);
        container.add(btn_clear);
        container.add(btn_quit);
        tm = new DefaultTableModel(rowData, colNames);
        tb = new JTable(tm);
        sp = new JScrollPane(tb);
        sp.setPreferredSize(new Dimension(300, 300));
        container.add(sp);

        btn_select.addActionListener(this);
        btn_clear.addActionListener(this);
        btn_quit.addActionListener(this);

    }

    public void add(Vector<String> column) {
        tm.addRow(column);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btn_select) {
            String condition = text_search.getText();
            int data_num = 0;
            if (condition.equals("")) {
                ctrl.getAllTuple(column_data);
            } else {
                ctrl.getTupleBySearch(condition, column_data);
            }
            data_num = ctrl.getSearchedDataNum();
            for (int i = 0; i < data_num; i++) {
                this.add(column_data[i]);
                column_data[i] = new Vector<String>();
            }
        } else if (ae.getSource() == btn_clear) {
            tm.setRowCount(0);
            text_search.setText("");
        } else if (ae.getSource() == btn_quit) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SearchView window = new SearchView();
        window.setVisible(true);
    }
}
