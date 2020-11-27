import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
/**
 * @author 911
 * @date 2020-10-30 15:19
 */
public class GridBagLayoutDemo {
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private JLabel msglabel;

    public GridBagLayoutDemo(){
        prepareGUI();
    }
    public static void main(String[] args){
        GridBagLayoutDemo swingLayoutDemo = new GridBagLayoutDemo();
        swingLayoutDemo.showGridBagLayoutDemo();
    }
    private void prepareGUI(){
        mainFrame = new JFrame("Java SWING GridBagLayoutDemo(yiibai.com)");
        mainFrame.setSize(400,400);
        mainFrame.setLayout(new GridLayout(3, 1));

        headerLabel = new JLabel("",JLabel.CENTER );
        statusLabel = new JLabel("",JLabel.CENTER);
        statusLabel.setSize(350,100);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(statusLabel);
        mainFrame.setVisible(true);
    }
    private void showGridBagLayoutDemo(){
        headerLabel.setText("Layout in action: GridBagLayout");

        JPanel panel = new JPanel();
        panel.setBackground(Color.darkGray);
        panel.setSize(300,300);
        GridBagLayout layout = new GridBagLayout();

        panel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

//        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1100;
        gbc.gridy = 1100;
        panel.add(new JButton("Button - A"),gbc);

//        gbc.gridx = 1;
//        gbc.gridy = 0;
//        panel.add(new JButton("Button - B"),gbc);
//
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.ipady = 20;
//        gbc.gridx = 0;
//        gbc.gridy = 1;
//        panel.add(new JButton("Button - C"),gbc);
//
//        gbc.gridx = 1;
//        gbc.gridy = 1;
//        panel.add(new JButton("Button - D"),gbc);
//
//        gbc.gridx = 100;
//        gbc.gridy = 200;
////        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridwidth = 2;
//        panel.add(new JButton("Button - E"),gbc);

        controlPanel.add(panel);
        mainFrame.setVisible(true);
    }
}
