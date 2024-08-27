import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class musteriekle extends JFrame {
    private JPanel panel2;
    private JLabel label1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton buttonkaydet;
    private JButton buttonexit;

    public musteriekle() {
        add(panel2);
        setSize(400, 400);
        setTitle("MÜŞTERİ GİRİŞ EKRANI");

        buttonkaydet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    try (Connection baglanti1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/rentacar?useSSL=false&serverTimezone=UTC", "root", "root")) {
                        String sql = "INSERT INTO Musteriler (Tc_No, Ad_Soyad, Telefon_No, Email, Adres) VALUES (?, ?, ?, ?, ?)";
                        PreparedStatement pt = baglanti1.prepareStatement(sql);
                        pt.setString(1, textField1.getText());
                        pt.setString(2, textField2.getText());
                        pt.setString(3, textField3.getText());
                        pt.setString(4, textField4.getText());
                        pt.setString(5, textField5.getText());
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

        buttonexit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
                form1 f1=new form1();
                f1.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        musteriekle frame = new musteriekle();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
