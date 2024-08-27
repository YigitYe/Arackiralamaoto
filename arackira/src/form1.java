import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class form1 extends JFrame {
    private JButton satışlarButton;
    private JButton buttonexit;
    private JButton sözleşmeButton;
    private JButton araçListeleButton;
    private JButton buttonmusteri;
    private JButton buttonaracekle;
    private JButton buttonmustlist;
    private JPanel panel1;

    public form1(){
        add(panel1);
        setSize(400,400);
        setTitle("araç kiralama");

        buttonmusteri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                musteriekle m1=new musteriekle();
                m1.setVisible(true);


            }
        });

        buttonaracekle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AracEkle a1=new AracEkle();
                a1.setVisible(true);
            }
        });
        sözleşmeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Sozlesme s1=new Sozlesme();
                s1.setVisible(true);
            }
        });
        satışlarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Satis sa1=new Satis();
                sa1.setVisible(true);
            }
        });
        buttonexit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                System.exit(0);
            }
        });
        araçListeleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AracListele a1=new AracListele();
                a1.setVisible(true);

            }
        });
    }


}
