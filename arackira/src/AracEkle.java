import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AracEkle extends JFrame {
    private JPanel panel4;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField8;
    private JButton button1;
    private JButton button2;
    private JComboBox comboBox1;
    private JComboBox comboBox2;

    public AracEkle(){
        add(panel4);
        setSize(600,600);
        setTitle("araç Ekleme Listesi");

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    try (Connection baglanti1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/rentacar?useSSL=false&serverTimezone=UTC", "root", "root")) {
                        String sql = "INSERT INTO Araba (Plaka, Marka, Seri, Model, Renk,Kilometre,Yakit,Kira_Ucreti,Durumu) VALUES (?, ?, ?, ?, ?,?,?,?,?)";
                        PreparedStatement pt = baglanti1.prepareStatement(sql);
                        pt.setString(1, textField1.getText());
                        pt.setString(2, textField2.getText());
                        pt.setString(3, textField3.getText());
                        pt.setString(4, textField4.getText());
                        pt.setString(5, textField5.getText());
                        pt.setString(6, textField6.getText());
                        pt.setString(7, (String) comboBox2.getSelectedItem());
                        pt.setString(8, textField8.getText());
                        pt.setString(9, (String) comboBox1.getSelectedItem());
                        pt.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Kayıt başarılı!");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Veritabanına bağlanırken hata oluştu: " + ex.getMessage());
                    }
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "JDBC Driver bulunamadı!");
                }

            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                form1 f1=new form1();
                f1.setVisible(true);
            }
        });

    }

}
