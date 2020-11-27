/**
 * @author 911
 * @date 2020-10-20 09:18
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class calculator
{
    Frame f;
    Panel p1;
    Panel p2;
    String[] name = {"1","2","3","4","5"};
    CardLayout c;

    public void init()
    {
        f = new Frame("yz");
        p1 = new Panel();
        p2 = new Panel();
        c = new CardLayout();

        p1.setLayout(c);
        for(int i=0;i<name.length;i++)
        {
            p1.add(name[i],new Button(name[i]));
        }

        //控制显示上一张的按钮
        Button previous = new Button("上一张");
        previous.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                c.previous(p1);
            }
        });
        //控制显示下一张的按钮
        Button next = new Button("下一张");
        next.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                c.next(p1);
            }
        });
        //控制显示第一张的按钮
        Button first = new Button("第一张");
        first.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                c.first(p1);
            }
        });
        //控制显示最后一张的按钮
        Button last = new Button("最后一张");
        last.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                c.last(p1);
            }
        });
        //根据card名显示的按钮
        Button third = new Button("第三张");
        third.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                c.show(p1,"3");
            }
        });
        p2.add(previous);
        p2.add(next);
        p2.add(first);
        p2.add(last);
        p2.add(third);
        f.add(p1);//默认添加到中间
        f.add(p2,BorderLayout.SOUTH);
        f.pack();
        f.setVisible(true);
    }
    public static void main(String[] args)
    {
        new calculator().init();
    }
}
