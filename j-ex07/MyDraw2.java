import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class MyDraw2 extends JPanel implements ActionListener, MouseListener {
    private enum EnumButton {
        BLACK, RED, GREEN, LINE, RECT, OVAL, CLEAR, QUIT
    }

    private enum EnumDrawMode {
        LINE, RECT, OVAL, CLEAR, NONE
    }

    private String button_name[] = {"Black", "Red", "Green", "Line", "Rect", "Oval", "Clear", "Quit"};
    private int button_num = button_name.length;
    private Container c;
    private JButton button[] = new JButton[button_num];
    private JPanel panel;
    private Color selected_color = Color.BLACK;
    private int start_x, start_y, end_x, end_y;
    private EnumDrawMode draw_mode = EnumDrawMode.NONE;
    private boolean is_select_start = false, is_select_end = false;

    public MyDraw2(JFrame frame) {
        c = frame.getContentPane();
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (int i = 0; i < button_num; i++) {
            button[i] = new JButton(button_name[i]);
        }
        button[EnumButton.BLACK.ordinal()].setForeground(Color.BLACK);
        button[EnumButton.RED.ordinal()].setForeground(Color.RED);
        button[EnumButton.GREEN.ordinal()].setForeground(Color.GREEN);
        for (int i = 0; i < button_num; i++) {
            panel.add(button[i], new BorderLayout());
            button[i].addActionListener(this);
        }
        c.addMouseListener(this);
        c.add(panel);
        frame.pack();
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == button[EnumButton.BLACK.ordinal()]) {
            selected_color = Color.BLACK;
            System.out.println("Black");
        } else if (ae.getSource() == button[EnumButton.RED.ordinal()]) {
            selected_color = Color.RED;
        } else if (ae.getSource() == button[EnumButton.GREEN.ordinal()]) {
            selected_color = Color.GREEN;
        }
        if (ae.getSource() == button[EnumButton.LINE.ordinal()]) {
            is_select_start = true;
            draw_mode = EnumDrawMode.LINE;
            System.out.println("Line");
        } else if (ae.getSource() == button[EnumButton.RECT.ordinal()]) {
            is_select_start = true;
            draw_mode = EnumDrawMode.RECT;
        } else if (ae.getSource() == button[EnumButton.OVAL.ordinal()]) {
            is_select_start = true;
            draw_mode = EnumDrawMode.OVAL;
        }
        if (ae.getSource() == button[EnumButton.CLEAR.ordinal()]) {
// draw_mode = EnumDrawMode.CLEAR;
// super.paintComponent(getGraphics());
            c.repaint();
            System.out.println("Clear");
        }
        if (ae.getSource() == button[EnumButton.QUIT.ordinal()]) {
            System.exit(0);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        System.out.println(g.getClip());
        int width = Math.abs(start_x - end_x);
        int height = Math.abs(start_y - end_y);
        g.setColor(selected_color);
        switch (draw_mode) {
            case LINE:
                g.drawLine(start_x, start_y, end_x, end_y);
                System.out.println("draw line");
                break;
            case RECT:
                if ((end_x - start_x) > 0) {
                    g.drawRect(start_x, start_y, width, height);
                } else {
                    g.drawRect(end_x, end_y, width, height);
                }
                System.out.println("draw rect");
                break;
            case OVAL:
                if ((end_x - start_x) > 0) {
                    g.drawOval(start_x, start_y, width, height);
                } else {
                    g.drawOval(end_x, end_y, width, height);
                }
                System.out.println("draw oval");
// case CLEAR:
// super.paintComponents(g);
// System.out.println("Clear");
// break;
            default:
                break;
        }
        System.out.println("start x: " + start_x + "\tstart y: " + start_y + "\tend x: " + end_x + "\tend y: " + end_y + "\twidth: " + width + "\theight: " + height);

    }

    public void mousePressed(MouseEvent e) {
        if (is_select_start) {
            start_x = e.getX();
            start_y = e.getY();
            is_select_start = false;
            is_select_end = true;
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (is_select_end) {
            end_x = e.getX();
            end_y = e.getY();
            is_select_end = false;
            is_select_start = true;
//repaint(80, 5, 215, 220);
            repaint();
        }
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MyDraw2");
        frame.setPreferredSize(new Dimension(300, 250));
        MyDraw2 md = new MyDraw2(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(md);
        frame.setVisible(true);
    }

}