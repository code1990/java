import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author 911
 * @date 2020-10-30 15:22
 */
public class SpringLayoutDemo {
    private JFrame mainFrame;
//    private JLabel headerLabel;
//    private JLabel statusLabel;
//    private JPanel controlPanel;

    public SpringLayoutDemo() {
        prepareGUI();
    }

    public static void main(String[] args) {
        SpringLayoutDemo swingLayoutDemo = new SpringLayoutDemo();
        swingLayoutDemo.showSpringLayoutDemo();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Java SWING SpringLayoutDemo(yiibai.com)");
        mainFrame.setSize(2000, 2000);
        mainFrame.setLayout(new GridLayout(3, 1));

//        headerLabel = new JLabel("1", JLabel.CENTER);
//        statusLabel = new JLabel("2", JLabel.CENTER);
//        statusLabel.setSize(350, 100);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
//        controlPanel = new JPanel();
//        controlPanel.setLayout(new FlowLayout());

//        mainFrame.add(headerLabel);
//        mainFrame.add(controlPanel);
//        mainFrame.add(statusLabel);
        mainFrame.setVisible(true);
    }

    private void showSpringLayoutDemo() {
//        headerLabel.setText("Layout in action: SpringLayout");
        SpringLayout layout = new SpringLayout();

        JPanel panel = new JPanel();
        panel.setLayout(layout);
//        JLabel label = new JLabel("输入姓名: ");
        JTextField textField = new JTextField("", 15);
//        panel.add(label);
        panel.add(textField);

//        layout.putConstraint(SpringLayout.WEST, label, 50, SpringLayout.WEST, controlPanel);
//        layout.putConstraint(SpringLayout.NORTH, label, 50, SpringLayout.NORTH, controlPanel);
//        layout.putConstraint(SpringLayout.WEST, textField, 50, SpringLayout.EAST, label);
//        layout.putConstraint(SpringLayout.NORTH, textField, 50, SpringLayout.NORTH, controlPanel);
//将组件c1的边缘e1链接到组件c2的边缘e2，边缘之间具有固定的距离
        layout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, textField);
        layout.putConstraint(SpringLayout.NORTH, panel, 100, SpringLayout.NORTH, textField);
        mainFrame.add(panel);
        mainFrame.setVisible(true);
    }
}
