
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class baglanti {
    static String url="jdbc:mysql://localhost:3306/rentacar?useSSL=false&serverTimezone=UTC";
    static Connection conn=null;
    static void baglan(){
try{
    conn=DriverManager.getConnection(url,"root","root");
    System.out.println("Bağlantı Gerçekleşti.");
}catch (SQLException e){
    e.printStackTrace();
}

    }

   static ResultSet listele(String sorgu){
     try{
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery(sorgu);
        return rs;
     }catch (SQLException e){
    e.printStackTrace();
  return null;
     }
   }



    public static void main(String[] args) {



    }
}