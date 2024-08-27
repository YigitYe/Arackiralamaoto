using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SqlClient;

namespace AracKiralama
{
    public partial class MusteriListele : Form
    {
        public MusteriListele()
        {
            InitializeComponent();
        }
        private string baglantiCumlesi = @"Data Source=localhost;Initial Catalog=OtoKiralama;Integrated Security=True";
        private void button3_Click(object sender, EventArgs e)
        {
            this.Close();
        }
        public void Musteri_Listele()
        {
            SqlConnection baglanti = new SqlConnection(baglantiCumlesi);
            baglanti.Open();

            String komutCumlesi = "Select Tc_No,Ad_Soyad,Telefon,Mail,Adres from Musteriler";
            SqlCommand komut = new SqlCommand(komutCumlesi, baglanti);
            SqlDataAdapter da = new SqlDataAdapter(komut);
            DataTable dt = new DataTable();
            da.Fill(dt);
            dataGridView1.DataSource = dt;
            baglanti.Close();
        }
        public void Musteri_Guncelle()
        {
            SqlConnection baglanti = new SqlConnection(baglantiCumlesi);
            baglanti.Open();

            string komutCumlesi = "Update Musteriler set Ad_Soyad=@adsoyad,Telefon=@telefon,Mail=@mail,Adres=@adres where Tc_No=@tc";
            SqlCommand komut = new SqlCommand(komutCumlesi, baglanti);
            komut.Parameters.AddWithValue("@tc", txtTc.Text);
            komut.Parameters.AddWithValue("@adsoyad", txtAdSoyad.Text);
            komut.Parameters.AddWithValue("@telefon", txtTel.Text);
            komut.Parameters.AddWithValue("@mail", txtMail.Text);
            komut.Parameters.AddWithValue("@adres", txtAdres.Text);
            komut.ExecuteNonQuery();
            baglanti.Close();
            Musteri_Listele();
            
        }
        public void Musteri_Sil()
        {
            SqlConnection baglanti = new SqlConnection(baglantiCumlesi);
            baglanti.Open();

            string komutCumlesi = "Delete from Musteriler where Tc_No='" + dataGridView1.CurrentRow.Cells["Tc_No"].Value.ToString() + "'";
            SqlCommand komut = new SqlCommand(komutCumlesi, baglanti);
            
            komut.ExecuteNonQuery();
            baglanti.Close();
            Musteri_Listele();
        }
        private void MusteriListele_Load(object sender, EventArgs e)
        {
            Musteri_Listele();
           
        }

        private void dataGridView1_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            txtTc.Text = dataGridView1.CurrentRow.Cells[0].Value.ToString();
            txtAdSoyad.Text = dataGridView1.CurrentRow.Cells[1].Value.ToString();
            txtMail.Text = dataGridView1.CurrentRow.Cells[2].Value.ToString();
            txtTel.Text = dataGridView1.CurrentRow.Cells[3].Value.ToString();
            txtAdres.Text = dataGridView1.CurrentRow.Cells[4].Value.ToString();
            
        }
        
        private void btnGüncelle_Click(object sender, EventArgs e)
        {
            Musteri_Guncelle();
        }

        private void btnSil_Click(object sender, EventArgs e)
        {
            Musteri_Sil();
        }
    }
}
