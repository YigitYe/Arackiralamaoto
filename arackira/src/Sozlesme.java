import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;

public class Sozlesme extends JFrame {
    private JPanel panel5;
    private JTextField txtTcara;
    private JTextField txtTc;
    private JTextField txtAdSoyad;
    private JTextField txtTel;
    private JTextField txtEhliyetNO;
    private JTextField txtEhliyettarihi;
    private JTextField txtMarka;
    private JTextField txtSeri;
    private JTextField txtModel;
    private JTextField txtRenk;
    private JButton hesaplaButton;
    private JButton ekleButton;
    private JTextField kira_ucret;
    private JTextField Gun_sayisi;
    private JTextField tutar;
    private JTable table1;
    private JComboBox comboBox1;
    private JButton araçTeslimButton;
    private JButton çıkışButton;

    DefaultTableModel modelim = new DefaultTableModel();
    Object[] kolonlar = {"Plaka", "Marka", "Seri", "Model", "Renk", "Kilometre", "Yakit", "Kira ücreti", "Durumu"};
    Object[] satirlar = new Object[9];

    public Sozlesme() {
        add(panel5);
        setTitle("SÖZLEŞME");
        setSize(1000, 1000);
        arabalaribastir();
        araclistele();

        hesaplaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int gunSayisi = Integer.parseInt(Gun_sayisi.getText());
                    int kiraUcreti = Integer.parseInt(kira_ucret.getText());
                    int sonuc = gunSayisi * kiraUcreti;
                    tutar.setText(String.valueOf(sonuc));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Lütfen geçerli bir gün sayısı ve kira ücreti girin.");
                }
            }
        });


        comboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String plaka = comboBox1.getSelectedItem().toString();
                    String sorgu = "Select * from Araba where Plaka = ?";
                    try {
                        PreparedStatement preparedStatement = baglanti.conn.prepareStatement(sorgu);
                        preparedStatement.setString(1, plaka);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            txtMarka.setText(resultSet.getString("Marka"));
                            txtSeri.setText(resultSet.getString("Seri"));
                            txtModel.setText(resultSet.getString("Model"));
                            txtRenk.setText(resultSet.getString("Renk"));
                            kira_ucret.setText(resultSet.getString("Kira_Ucreti"));
                        }
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });
        ekleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (  txtTc.getText().isEmpty() || txtAdSoyad.getText().isEmpty() ||
                        txtTel.getText().isEmpty() || txtEhliyetNO.getText().isEmpty() || txtEhliyettarihi.getText().isEmpty() ||
                        comboBox1.getSelectedItem() == null || txtMarka.getText().isEmpty() || txtSeri.getText().isEmpty() ||
                        txtModel.getText().isEmpty() || txtRenk.getText().isEmpty() || kira_ucret.getText().isEmpty() ||
                        Gun_sayisi.getText().isEmpty() || tutar.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Lütfen tüm alanları doldurun.");
                    return;
                }


                String sorgu = "INSERT INTO Sozlesme (TC_No, Ad_Soyad, Telefon, Ehliyet_No, Ehliyet_Tarihi, Plaka, Marka, Seri, Model, Renk, Kira_Ucreti, Kiralandigi_Gun_Sayisi, Tutar) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = baglanti.conn.prepareStatement(sorgu)) {
                    preparedStatement.setString(1, txtTc.getText());
                    preparedStatement.setString(2, txtAdSoyad.getText());
                    preparedStatement.setString(3, txtTel.getText());
                    preparedStatement.setString(4, txtEhliyetNO.getText());
                    preparedStatement.setString(5, txtEhliyettarihi.getText());
                    preparedStatement.setString(6, comboBox1.getSelectedItem().toString());
                    preparedStatement.setString(7, txtMarka.getText());
                    preparedStatement.setString(8, txtSeri.getText());
                    preparedStatement.setString(9, txtModel.getText());
                    preparedStatement.setString(10, txtRenk.getText());
                    preparedStatement.setInt(11, Integer.parseInt(kira_ucret.getText()));
                    preparedStatement.setInt(12, Integer.parseInt(Gun_sayisi.getText()));
                    preparedStatement.setInt(13, Integer.parseInt(tutar.getText()));
                    preparedStatement.executeUpdate();
                    String updateSorgu = "UPDATE Araba SET Durumu = 'Dolu' WHERE Plaka = ?";
                    try (PreparedStatement updatePreparedStatement = baglanti.conn.prepareStatement(updateSorgu)) {
                        updatePreparedStatement.setString(1, comboBox1.getSelectedItem().toString());
                        updatePreparedStatement.executeUpdate();
                    }

                    JOptionPane.showMessageDialog(null, "Sözleşme başarıyla eklendi.");
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });

        txtTcara.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                baglanti.baglan();
                String komut="Select * from Musteriler where Tc_No like'"+txtTcara.getText()+"'";
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
                        txtTc.setText(resultSet.getString("Tc_No"));
                        txtAdSoyad.setText(resultSet.getString("Ad_Soyad"));
                        txtTel.setText(resultSet.getString("Telefon_No"));
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        araçTeslimButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRowIndex = table1.getSelectedRow();
                if (selectedRowIndex >= 0) {
                    String plaka = table1.getValueAt(selectedRowIndex, 0).toString();

                    try {
                        String sorgu = "INSERT INTO Satis (Tc_No, Ad_Soyad, Telefon_No, Plaka, Kira_Ucret, Tutar, Gun_Sayisi) VALUES (?, ?, ?, ?, ?, ?, ?)";
                        try (PreparedStatement preparedStatement = baglanti.conn.prepareStatement(sorgu)) {
                            preparedStatement.setString(1, txtTc.getText());
                            preparedStatement.setString(2, txtAdSoyad.getText());
                            preparedStatement.setString(3, txtTel.getText());
                            preparedStatement.setString(4, plaka);
                            preparedStatement.setInt(5, Integer.parseInt(kira_ucret.getText()));
                            preparedStatement.setInt(6, Integer.parseInt(tutar.getText()));
                            preparedStatement.setInt(7, Integer.parseInt(Gun_sayisi.getText()));
                            preparedStatement.executeUpdate();

                            String updateSorgu = "UPDATE Araba SET Durumu = 'Boş' WHERE Plaka = ?";
                            try (PreparedStatement updatePreparedStatement = baglanti.conn.prepareStatement(updateSorgu)) {
                                updatePreparedStatement.setString(1, plaka);
                                updatePreparedStatement.executeUpdate();
                            }

                            JOptionPane.showMessageDialog(null, "Araç teslim edildi.");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Lütfen bir araç seçin.");
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
    }

    public void arabalaribastir() {
        baglanti.baglan();
        String sorgu = "Select * from Araba";
        modelim.setColumnCount(0);
        modelim.setRowCount(0);
        modelim.setColumnIdentifiers(kolonlar);
        ResultSet rs = baglanti.listele(sorgu);

        try {
            while (rs.next()) {
                satirlar[0] = rs.getString("Plaka");
                satirlar[1] = rs.getString("Marka");
                satirlar[2] = rs.getString("Seri");
                satirlar[3] = rs.getString("Model");
                satirlar[4] = rs.getString("Renk");
                satirlar[5] = rs.getString("Kilometre");
                satirlar[6] = rs.getString("Yakit");
                satirlar[7] = rs.getString("Kira_Ucreti");
                satirlar[8] = rs.getString("Durumu");
                modelim.addRow(satirlar);
            }
            table1.setModel(modelim);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void araclistele() {
        baglanti.baglan();
        String sorgu = "Select * from Araba where Durumu='Boş'";
        ResultSet read = baglanti.listele(sorgu);
        try {
            while (read.next()) {
                comboBox1.addItem(read.getString("Plaka"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public static void main(String[] args) {
        JFrame frame = new Sozlesme();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
