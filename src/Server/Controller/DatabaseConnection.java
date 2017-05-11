package Server.Controller;

import Client.Model.Abonament;
import Client.Model.Valabilitate;

import java.sql.*;

/**
 * Created by Adrian on 11.05.2017.
 */
public class DatabaseConnection {
    private static Connection myConn ;
    private static String username = "root";
    private static String password = "root";


    public static void openDatabase (){
        // TODO fac query la tabela cu cartelele utilizatorului si le afisez in meniu
        try{
            Class.forName("com.mysql.jdbc.Driver");
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cartele", username , password);
//            Statement statement = myConn.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM cartele");
//            while (resultSet.next()){
//                if (resultSet.getString("Tip").equals("Abonament")){
//
//
//                    Abonament abonament = new Abonament();
//                    abonament.manualId(resultSet.getLong("ID"));
//                    abonament.setValabilitate(
//                            new Valabilitate(resultSet.getDate("StartDate"), resultSet.getDate("EndDate"))
//                    );
//                    abonament.setTip();
//
//                    //addAbonamentToInterface(abonament);
//                }
//            }

//            addCurrentIDs(statement, resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public static Connection getMyConn() {
        return myConn;
    }
}
