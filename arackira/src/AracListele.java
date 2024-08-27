import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AracListele extends JFrame {
    private JPanel panel1;
    private JButton guncelleButton;
    private JButton silButton;
    private JTextField txtplaka;
    private JTextField txtmarka;
    private JTextField txtseri;
    private JTextField txtmodel;
    private JTextField txtrenk;
    private JTextField txtkm;
    private JComboBox<String> cbxyakit;
    private JComboBox<String> cbxdurum;
    private JTextField txtkiraucret;
    private JButton çıkışButton;


    public AracListele() {


        add(panel1);
        setTitle("ARAÇ LİSTE");
        setSize(600, 600);

        guncelleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtplaka.getText().isEmpty() || txtmarka.getText().isEmpty() || txtseri.getText().isEmpty() ||
                        txtmodel.getText().isEmpty() || txtrenk.getText().isEmpty() || txtkm.getText().isEmpty() ||
                        cbxyakit.getSelectedItem() == null || txtkiraucret.getText().isEmpty() || cbxdurum.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Lütfen tüm alanları doldurun.");
                    return;
                }

                baglanti.baglan();

                String sorgu = "UPDATE Araba SET Plaka=?, Marka=?, Seri=?, Model=?, Renk=?, Kilometre=?, Yakit=?, Kira_Ucreti=?, Durumu=? WHERE Plaka=?";
                try (PreparedStatement preparedStatement = baglanti.conn.prepareStatement(sorgu)) {
                    preparedStatement.setString(1, txtplaka.getText());
                    preparedStatement.setString(2, txtmarka.getText());
                    preparedStatement.setString(3, txtseri.getText());
                    preparedStatement.setString(4, txtmodel.getText());
                    preparedStatement.setString(5, txtrenk.getText());
                    preparedStatement.setString(6, txtkm.getText());
                    preparedStatement.setString(7, cbxyakit.getSelectedItem().toString());
                    preparedStatement.setString(8, txtkiraucret.getText());
                    preparedStatement.setString(9, cbxdurum.getSelectedItem().toString());
                    preparedStatement.setString(10, txtplaka.getText()); // Set the value for the WHERE clause

                    int rowsUpdated = preparedStatement.executeUpdate();

                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(null, "Güncellendi.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Güncelleme başarısız. Belirtilen plakaya sahip araç bulunamadı.");
                    }
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });

        çıkışButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               dispose();
               form1 f1=new form1();
               f1.setVisible(true);
            }
        });
        txtplaka.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                baglanti.baglan();
                String komut="Select * from Araba where Plaka like'"+txtplaka.getText()+"'";
                PreparedStatement preparedStatement = null;
                try {
                    preparedStatement = baglanti.conn.prepareStatement(komut);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                ResultSet resultSet = null;
                try {
                    resultSet = preparedStatement.executeQuery();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    if (resultSet.next()) {
                        txtmarka.setText(resultSet.getString("Marka"));
                        txtseri.setText(resultSet.getString("Seri"));
                        txtmodel.setText(resultSet.getString("Model"));
                        txtrenk.setText(resultSet.getString("Renk"));
                        txtkm.setText(resultSet.getString("Kilometre"));
                        cbxyakit.setSelectedItem(resultSet.getString("Yakit"));
                        txtkiraucret.setText(resultSet.getString("Kira_Ucreti"));
                        cbxdurum.setSelectedItem(resultSet.getString("Durumu"));

                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        silButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtplaka.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Lütfen plaka numarasını girin.");
                    return;
                }

                int dialogResult = JOptionPane.showConfirmDialog(null, "Silmek istediğinizden emin misiniz?", "Uyarı", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    baglanti.baglan();

                    String sorgu = "DELETE FROM Araba WHERE Plaka=?";
                    try (PreparedStatement preparedStatement = baglanti.conn.prepareStatement(sorgu)) {
                        preparedStatement.setString(1, txtplaka.getText());

                        int rowsDeleted = preparedStatement.executeUpdate();

                        if (rowsDeleted > 0) {
                            JOptionPane.showMessageDialog(null, "Araç başarıyla silindi.");
                            txtmarka.setText("");
                            txtseri.setText("");
                            txtmodel.setText("");
                            txtrenk.setText("");
                            txtkm.setText("");
                            cbxyakit.setSelectedItem(null);
                            txtkiraucret.setText("");
                            cbxdurum.setSelectedItem(null);
                        } else {
                            System.out.println("Yazdığınız plakada bir araba bulunamadı.");
                        }
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });

    }



    public static void main(String[] args) {

    }
}
