import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class UpdateView extends JFrame implements ActionListener, MouseListener {
    private UpdateCtrl ctrl;
    private Container container;
    private JLabel label_insert, label_delete, label_correct;
    private JTextField text_insert, text_correct;
    private JButton btn_insert, btn_delete, btn_correct, btn_return;
    private DefaultTableModel tm;
    private JTable tb;
    private JScrollPane sp;
    private Vector<String>[] table_column_data, column_data;
    private String[] insert_data;
    private JLabel[] labels;
    private JButton[] buttons;
    private JTextField[] texts;
    private String correct_data;
    private int db_data_num = 0;
    private int row = 0;
    private int column = 0;
    private final int LABEL_NUM = 3;
    private final int TEXT_NUM = 2;
    private final int BUTTON_NUM = 4;
    private static int ins_count = 0;
    private static int cor_count = 0;


    private final String[] column_name = {"sno", "sname", "address", "age"};

    private enum Mode {
        NONE, INSERT, DELETE, CORRECT, RETURN
    }

    private Mode mode;

    public UpdateView() {
        ctrl = new UpdateCtrl("jdbc:sqlite:/Users/takumi/Downloads/stock3.s3db");

        container = getContentPane();
        label_insert = new JLabel("sno, sname, address, ageの要素を入力してください");
        label_delete = new JLabel("削除したい行の要素を選択してください");
        label_correct = new JLabel("修正したい行の要素を選択してください");
        text_insert = new JTextField("", 20);
        text_correct = new JTextField("", 20);
        btn_insert = new JButton("挿入");
        btn_delete = new JButton("削除");
        btn_correct = new JButton("修正");
        btn_return = new JButton("戻る");
        mode = Mode.NONE;
        ctrl.setColumnName(column_name);
        ctrl.setSearchedTableName("Student");
        db_data_num = ctrl.getDBDataNum();
        table_column_data = new Vector[db_data_num];
        buttons = new JButton[BUTTON_NUM];
        for (int i = 0; i < BUTTON_NUM; i++) {
            buttons[i] = new JButton();
        }
        buttons[0] = btn_insert;
        buttons[1] = btn_delete;
        buttons[2] = btn_correct;
        buttons[3] = btn_return;
        labels = new JLabel[LABEL_NUM];
        for (int i = 0; i < LABEL_NUM; i++) {
            labels[i] = new JLabel();
        }
        labels[0] = label_insert;
        labels[1] = label_delete;
        labels[2] = label_correct;
        texts = new JTextField[TEXT_NUM];
        for (int i = 0; i < TEXT_NUM; i++) {
            texts[i] = new JTextField();
        }
        texts[0] = text_insert;
        texts[1] = text_correct;

        column_data = new Vector[db_data_num];
        for (int i = 0; i < db_data_num; i++) {
            table_column_data[i] = new Vector<String>();
            column_data[i] = new Vector<String>();
        }
        String[][] rowData = {};
        String[] colNames = {column_name[0], column_name[1], column_name[2], column_name[3]};
        container.setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("j-ex13-select");
        setSize(340, 450);

        invisibleAll();
        btn_correct.setVisible(true);
        btn_insert.setVisible(true);
        btn_delete.setVisible(true);

        tm = new DefaultTableModel(rowData, colNames);
        tb = new JTable(tm);
        sp = new JScrollPane(tb);
        sp.setPreferredSize(new Dimension(300, 300));
        container.add(sp);
        container.add(btn_insert);
        container.add(label_insert);
        container.add(label_correct);
        container.add(btn_correct);
        container.add(label_delete);
        container.add(btn_delete);
        container.add(text_insert);
        container.add(text_correct);
        container.add(btn_return);

        btn_insert.addActionListener(this);
        btn_delete.addActionListener(this);
        btn_correct.addActionListener(this);
        btn_return.addActionListener(this);
        tb.addMouseListener(this);

        ctrl.getAllTuple(table_column_data);
        ctrl.getAllTuple(column_data);
        int data_num = ctrl.getSearchedDataNum();
        for (int i = 0; i < data_num; i++) {
            this.add(table_column_data[i]);
            table_column_data[i] = new Vector<String>();
        }
    }

    public void add(Vector<String> column) {
        tm.addRow(column);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btn_insert) {
            mode = Mode.INSERT;
            invisibleAll();
            label_insert.setVisible(true);
            text_insert.setVisible(true);
            btn_insert.setVisible(true);
            btn_return.setVisible(true);
            if (++ins_count % 2 == 0) {
                db_data_num++;
                insert_data = ctrl.readTextField(text_insert);
                ctrl.insertIntoDB(insert_data);
                this.updateTable();
            }
        } else if (ae.getSource() == btn_delete) {
            mode = Mode.DELETE;
            invisibleAll();
            label_delete.setVisible(true);
            btn_return.setVisible(true);
        } else if (ae.getSource() == btn_correct) {
            mode = Mode.CORRECT;
            invisibleAll();
            label_correct.setVisible(true);
            text_correct.setVisible(true);
            btn_correct.setVisible(true);
            btn_return.setVisible(true);
            if (++cor_count % 2 == 0) {
                System.out.println(column_name[column]);
                ctrl.correctDBData(text_correct.getText(), column_data[row].get(column), column_name[column]);
                this.updateTable();
            }
        } else if (ae.getSource() == btn_return) {
            mode = Mode.RETURN;
            invisibleAll();
            btn_insert.setVisible(true);
            btn_delete.setVisible(true);
            btn_correct.setVisible(true);
        }
    }

    public void mousePressed(MouseEvent me) {
        row = tb.getSelectedRow();
        column = tb.getSelectedColumn();
        switch (mode) {
            case DELETE:
                db_data_num--;
                System.out.println("delete mode");
                ctrl.deleteFromDB(column_data[row]);
                ctrl.getAllTuple(table_column_data);
                ctrl.getAllTuple(column_data);
                this.updateTable();
                break;
            case CORRECT:
                System.out.println("correct mode");
                text_correct.setText(column_data[row].get(column));
                break;
            default:
                System.out.println("mode error");
                break;
        }
    }

    public void mouseReleased(MouseEvent me) {

    }

    public void mouseExited(MouseEvent me) {
    }

    public void mouseClicked(MouseEvent me) {

    }

    public void mouseEntered(MouseEvent me) {
    }

    public void invisibleAll() {
        for (int i = 0; i < BUTTON_NUM; i++) {
            buttons[i].setVisible(false);
        }
        for (int i = 0; i < LABEL_NUM; i++) {
            labels[i].setVisible(false);
        }
        for (int i = 0; i < TEXT_NUM; i++) {
            texts[i].setVisible(false);
        }
    }

    public void updateTable() {
        table_column_data = new Vector[db_data_num];
        column_data = new Vector[db_data_num];
        for (int i = 0; i < table_column_data.length; i++) {
            table_column_data[i] = new Vector<String>();
            column_data[i] = new Vector<String>();
        }
        tm.setRowCount(0);
        ctrl.getAllTuple(table_column_data);
        ctrl.getAllTuple(column_data);
        for (int i = 0; i < db_data_num; i++) {
            this.add(table_column_data[i]);
            table_column_data[i] = new Vector<String>();
        }
    }

    public static void main(String[] args) {
        UpdateView window = new UpdateView();
        window.setVisible(true);
    }
}