import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class BookView extends JFrame implements ActionListener {
    private BookCtrl ctrl;
    private Container container;
    private JLabel label_book_info[];
    private JButton btn_serch, btn_clear;
    private JTextField text_serch[];
    private ArrayList<BookConfig> searched_list;
    private final String label_text[] = {"name", "author name", "publisher", "ISBN"};

    public BookView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setTitle("BookView");
        ctrl = new BookCtrl();
        searched_list = new ArrayList<>();
        ctrl.makeBookList("/Users/takumi/IdeaProjects/j-ex08/src/book.txt");
        container = getContentPane();
        label_book_info = new JLabel[4];
        text_serch = new JTextField[4];
        btn_serch = new JButton("serch");
        btn_clear = new JButton(("clear"));
        container.setLayout(new FlowLayout());

        for (int i = 0; i < 4; i++) {
            label_book_info[i] = new JLabel(label_text[i]);
            text_serch[i] = new JTextField("", 16);
            container.add(label_book_info[i]);
            container.add(text_serch[i]);
        }
        container.add(btn_serch);
        container.add(btn_clear);
        btn_clear.addActionListener(this);
        btn_serch.addActionListener(this);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btn_serch) {
            ctrl.search(text_serch[0].getText(), text_serch[1].getText(), text_serch[2].getText(), text_serch[3].getText());
            searched_list = ctrl.getSearchedBookList();
            for (int i = 0; i < searched_list.size(); i++) {
                System.out.println("name: " + searched_list.get(i).name + "\nauthor name: " + searched_list.get(i).author_name + "\npublisher: " + searched_list.get(i).publisher + "\nISBN: " + searched_list.get(i).ISBN);
                System.out.println("------------------------");
            }
        } else if (ae.getSource() == btn_clear)

        {
            for (int i = 0; i < 4; i++) {
                text_serch[i].setText("");
            }
        }
    }

    public static void main(String[] args) {
        BookView window = new BookView();
        window.setVisible(true);
    }
}
