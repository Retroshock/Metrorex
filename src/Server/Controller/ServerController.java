package Server.Controller;

import Client.Constants.Strings;
import Client.Model.Abonament;
import Client.Model.Cartela;
import Client.Model.CartelaConsum;
import Client.Model.IDCounters;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.text.SimpleDateFormat;

/**
 * Created by Adrian on 10.05.2017.
 */
public class ServerController {

    private static ServerSocket serverSocket;
    private static Socket socket;
    private static ObjectInputStream objectInputStream;
    private static ObjectOutputStream objectOutputStream;
    private static DatabaseConnection dbConnection;

    public static void main(String[] args) {
        try{
            dbConnection.openDatabase();
            serverSocket = new ServerSocket(9002);



            while (true){
                socket = serverSocket.accept();
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                Object input = null;
                try {
                    input = objectInputStream.readObject();
                    if (input instanceof String){
                        if(((String)input).equals(Strings.exitRequest)){
                            objectInputStream.close();
                            socket.close();
                            break;
                        }
                    }
                    Object output = processRequest(input);
                    objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(output);
                    objectInputStream.close();
                    objectOutputStream.close();
                    socket.close();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void addCurrentIDs (Statement statement, ResultSet resultSet){
        String getMonthID = "select max(ID) from cartele where Tip = 'Abonament' and datediff(EndDate, StartDate) > 29;";
        String getDayID = "select max(ID) from cartele where Tip = 'Abonament' and datediff(EndDate, StartDate) = 1;";
        String getTenID = "select max(ID) from cartele where Tip = 'Cartela' and ID < " + IDCounters.TEN_MAX + ";";
        String getTwoID = "select max(ID) from cartele where Tip = 'Cartela' and ID > " + IDCounters.TEN_MAX + ";";

        try {
            //result pentru month
            resultSet =statement.executeQuery(getMonthID);
            if (resultSet.next()){
                long temp = resultSet.getInt("ID");
                IDCounters.monthId = temp>0? temp : IDCounters.monthId;
            }
            //result pentru day
            resultSet =statement.executeQuery(getDayID);
            if (resultSet.next()){
                long temp = resultSet.getInt("ID");
                IDCounters.dayId = temp>0? temp : IDCounters.dayId;
            }
            //result pentru ten
            resultSet =statement.executeQuery(getTenID);
            if (resultSet.next()){
                long temp = resultSet.getInt("ID");
                IDCounters.tenId = temp>0? temp : IDCounters.tenId;
            }
            //result pentru two
            resultSet =statement.executeQuery(getTwoID);
            if (resultSet.next()){
                long temp = resultSet.getInt("ID");
                IDCounters.tenId = temp>0? temp : IDCounters.tenId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Object processRequest(Object input) {
        Cartela cartela;
        String message;
        if (input instanceof Cartela){
            cartela =(Cartela) input;
            message = cartela.getMessage();
            // verifica mesajul primit si proceseaza requestul in functie de tipul cartelei
            switch (message){
                case Strings.addToDatabaseRequest:{
                    if (cartela instanceof Abonament) {
                        Abonament tempAb = (Abonament)cartela;
                        addAbonamentToDB(tempAb);
                    }
                    if (cartela instanceof CartelaConsum){
                        CartelaConsum tempCart = (CartelaConsum)cartela;
                        addCartelaConsumToDB(tempCart);
                    }
                    break;
                }
                case Strings.validateCardRequest: {
                    if (cartela instanceof Abonament) {
                        //TODO VERIFICA PENTRU ABONAMENT
                    }
                    if (cartela instanceof CartelaConsum){
                        //TODO VERIFICA PENTRU CARTELA
                    }
                    break;
                }

            }
        }
        return input;

    }


    private static Object processRequest(String message) {
        switch (message){
            //TODO PROCESEAZA REQUESTURI DE AFISARE A TUTUROR CARTELELOR

        }
        return null;
    }

    public synchronized static void addAbonamentToDB (Abonament abonament){

        try {

            //TODO Trebuie sa pun conexiunea intr-un modul
            //TODO Conexiuni, resultseturi si statementuri trebuie inchise!!

            Connection myConn = DatabaseConnection.getMyConn();
            Statement statement = myConn.createStatement();

            SimpleDateFormat sdf = new SimpleDateFormat(Strings.dateFormatString);
            String startDate = sdf.format(abonament.getValabilitate().getStart());
            String endDate = sdf.format(abonament.getValabilitate().getEnd());

            String st = "insert into cartele (ID, Tip, StartDate, EndDate) values ( " +
                    abonament.getId() + ", " + "'Abonament', '" +
                    startDate + "' , '" +
                    endDate +
                    "' );";

            statement.executeUpdate(st);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void addCartelaConsumToDB (CartelaConsum cartelaConsum){
        try {

            Connection myConn = DatabaseConnection.getMyConn();
            Statement statement = myConn.createStatement();

            //TODO TREBUIE SA ADAUG IN BD SI CARTELE DE CONSUM
//            String st = "insert into cartele (ID, Tip, StartDate, EndDate) values ( " +
//                    cartelaConsum.getId() + ", " + "'Cartela', '" +
//
//                    endDate +
//                    "' );";
//
//            statement.executeUpdate(st);

            myConn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
