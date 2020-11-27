/**
 * @author 911
 * @date 2020-10-30 15:36
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GroupLayoutDemo {
    private JFrame mainFrame;
//    private JLabel headerLabel;
//    private JLabel statusLabel;
    private JPanel controlPanel;
//    private JLabel msglabel;

    public GroupLayoutDemo(){
        prepareGUI();
    }
    public static void main(String[] args){
        GroupLayoutDemo swingLayoutDemo = new GroupLayoutDemo();
        swingLayoutDemo.showGroupLayoutDemo();
    }
    private void prepareGUI(){
        mainFrame = new JFrame("Java SWING GroupLayoutDemo(yiibai.com)");
        mainFrame.setSize(400,400);
        mainFrame.setLayout(new GridLayout(3, 1));

//        headerLabel = new JLabel("",JLabel.CENTER );
//        statusLabel = new JLabel("",JLabel.CENTER);
//        statusLabel.setSize(350,100);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

//        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
//        mainFrame.add(statusLabel);
        mainFrame.setVisible(true);
    }
    private void showGroupLayoutDemo(){
//        headerLabel.setText("Layout in action: GroupLayout");
        JPanel panel = new JPanel();

        // panel.setBackground(Color.darkGray);
        panel.setSize(200,200);
        GroupLayout layout = new GroupLayout(panel);
//        layout.setAutoCreateGaps(true);
//        layout.setAutoCreateContainerGaps(true);

//        JLabel btn1 = new JLabel("Button - A");
//        JLabel btn2 = new JLabel("Button - B");
        JLabel btn3 = new JLabel("Button - C");
//        panel.set
//        btn3
//        btn3.setHorizontalAlignment(SwingConstants.LEFT);
        layout.setHorizontalGroup(layout.createSequentialGroup()
//                .addComponent(btn1)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(
                                GroupLayout.Alignment.TRAILING)
//                                .addComponent(btn2)
                                .addComponent(btn3))));

        layout.setVerticalGroup(layout.createSequentialGroup()
//                .addComponent(btn1)
//                .addComponent(btn2)
                .addComponent(btn3));

        panel.setLayout(layout);
        controlPanel.add(panel);
        mainFrame.setVisible(true);
    }
}
