import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Satis extends JFrame {
    private JTable table1;
    private JPanel panel1;
    private JButton çıkışButton;
    DefaultTableModel modelim = new DefaultTableModel();
    Object[] kolonlar = { "Tc_No", "Ad_Soyad", "Telefon_No", "Plaka", "Kira_Ucret", "Tutar", "Gun_Sayisi"};
    Object[] satirlar = new Object[7];
    public Satis(){
        add(panel1);
        setTitle("SATIŞ");
        setSize(600, 600);
        satisbastir();

        çıkışButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                form1 f1=new form1();
                f1.setVisible(true);
            }
        });
    }

    public void satisbastir() {
        baglanti.baglan();
        String sorgu = "Select * from Satis";
        modelim.setColumnCount(0);
        modelim.setRowCount(0);
        modelim.setColumnIdentifiers(kolonlar);
        ResultSet rs = baglanti.listele(sorgu);

        try {
            while (rs.next()) {
                satirlar[0] = rs.getString("Tc_No");
                satirlar[1] = rs.getString("Ad_Soyad");
                satirlar[2] = rs.getString("Telefon_No");
                satirlar[3] = rs.getString("Plaka");
                satirlar[4] = rs.getString("Kira_Ucret");
                satirlar[5] = rs.getString("Tutar");
                satirlar[6] = rs.getString("Gun_Sayisi");

                modelim.addRow(satirlar);
            }
            table1.setModel(modelim);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
