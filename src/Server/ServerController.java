package Server;

import Client.Constants.Numbers;
import Client.Constants.Strings;
import Client.Model.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

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
        try {
            DatabaseConnection.openDatabase();


            serverSocket = new ServerSocket(9002);


            while (true) {

                socket = serverSocket.accept();
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                Object input = null;
                try {
                    input = objectInputStream.readObject();
                    if (input instanceof String) {
                        if (((String) input).equals(Strings.exitRequest)) {
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

    private static ArrayList<Long> addCurrentIDs(Connection myConn) {
        ArrayList<Long> IDs = new ArrayList<>(4);
        String getMonthID = "select max(ID) ID from cartele where Tip = 'Abonament' and datediff(EndDate, StartDate) > 29;";
        String getDayID = "select max(ID) ID from cartele where Tip = 'Abonament' and datediff(EndDate, StartDate) = 1;";
        String getTenID = "select max(ID) ID from cartele where Tip = 'Cartela' and ID < " + IDCounters.TEN_MAX + ";";
        String getTwoID = "select max(ID) ID from cartele where Tip = 'Cartela' and ID > " + IDCounters.TEN_MAX + ";";
        try {
            Statement statement = myConn.createStatement();
            ResultSet resultSet;
            //result pentru month
            resultSet = statement.executeQuery(getMonthID);
            if (resultSet.next()) {
                long temp = resultSet.getInt("ID");

                IDs.add(temp > IDCounters.monthId ? temp : IDCounters.monthId);
            }
            statement.close();

            statement = myConn.createStatement();
            //result pentru day
            resultSet = statement.executeQuery(getDayID);
            if (resultSet.next()) {
                long temp = resultSet.getInt("ID");
                IDs.add(temp > IDCounters.dayId ? temp : IDCounters.dayId);
            }
            statement.close();

            statement = myConn.createStatement();
            //result pentru ten
            resultSet = statement.executeQuery(getTenID);
            if (resultSet.next()) {
                long temp = resultSet.getInt("ID");
                IDs.add(temp > IDCounters.tenId ? temp : IDCounters.tenId);
            }
            statement.close();

            statement = myConn.createStatement();
            //result pentru two
            resultSet = statement.executeQuery(getTwoID);
            if (resultSet.next()) {
                long temp = resultSet.getInt("ID");
                IDs.add(temp > IDCounters.twoId ? temp : IDCounters.twoId);
            }
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return IDs;
    }

    private static Object processRequest(Object input) {
        Cartela cartela;
        String message;
        if (input instanceof Cartela) {
            cartela = (Cartela) input;
            message = cartela.getMessage();
            // verifica mesajul primit si proceseaza requestul in functie de tipul cartelei
            switch (message) {
                case Strings.addToDatabaseRequest: {
                    if (cartela instanceof Abonament) {
                        Abonament tempAb = (Abonament) cartela;
                        addAbonamentToDB(tempAb);
                    }
                    if (cartela instanceof CartelaConsum) {
                        CartelaConsum tempCart = (CartelaConsum) cartela;
                        addCartelaConsumToDB(tempCart);
                    }
                    break;
                }
                case Strings.validateCardRequest: {
                    if ((cartela = existsInDB(cartela,dbConnection.getMyConn()))!= null){
                        if (cartela instanceof Abonament) {

                            Date plus15 = new Date();
                            if (plus15.before(((Abonament) cartela).getValabilitate().getEnd())) {
                                plus15.setTime(plus15.getTime() + Numbers.ONE_MINUTE_IN_MILLIS * 15);
                                AbonamentsTimer.refreshEntries();
                                if (AbonamentsTimer.checkAbonament((Abonament) cartela) == -1) {
                                    ValidationPair vp = new ValidationPair((Abonament) cartela, plus15);
                                    AbonamentsTimer.addValidationPair(vp);
                                    return Strings.cardValidatedString;
                                } else {
                                    return Strings.cannotValidateForNowString;
                                }
                            } else {
                                removeCardFromDB(cartela);
                                return Strings.expiredCardString;
                            }
                        }
                        if (cartela instanceof CartelaConsum) {
                            cartela = existsInDB(cartela, dbConnection.getMyConn());
                        }
                    }
                    else{
                        return Strings.cardDoesNotExistInDB;
                        //return carteleFromDB(dbConnection.getMyConn());
                    }

                    break;
                }

            }
        } else if (input instanceof String) {
            message = (String) input;
            switch (message) {
                case Strings.refreshIDs: {

                    return addCurrentIDs(DatabaseConnection.getMyConn());
                }
                case Strings.showCardsForUserRequest: {
                    // trimite cartelele spre user (intr-un arrayList)
                    return carteleFromDB(DatabaseConnection.getMyConn());

                }
            }
        }
        return input;

    }

    private static void subtractOneFromCardInDB(CartelaConsum cartela, Connection myConn) {
        try{
            String query = "SELECT * FROM cartele WHERE ID = ?";
            PreparedStatement prepSt = myConn.prepareStatement(query);
            prepSt.setLong(1, cartela.getId());
            ResultSet rs = prepSt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Cartela existsInDB(Cartela cartela, Connection myConn) {
        try {
            String query = "SELECT * FROM cartele WHERE ID = ?";
            PreparedStatement prepSt = myConn.prepareStatement(query);
            prepSt.setLong(1, cartela.getId());
            ResultSet rs = prepSt.executeQuery();
            if (rs.next()) {
                if (rs.getString("Tip").equals("Abonament")) {
                    Abonament ab = extractAbonamentFromResultSet(rs);
                    prepSt.close();
                    rs.close();
                    return ab;
                } else {
                    CartelaConsum cc = extractCartelaFromResultSet(rs);
                    prepSt.close();
                    rs.close();
                    return cc;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Abonament extractAbonamentFromResultSet(ResultSet rs) throws SQLException {
        Abonament abonament = new Abonament();
        SimpleDateFormat sdf = new SimpleDateFormat(Strings.dateFormatString);
        Date startDate, endDate;
        abonament.manualId(rs.getLong("ID"));
        startDate = rs.getDate("StartDate");
        endDate = rs.getDate("EndDate");
        abonament.setValabilitate(new Valabilitate(startDate, endDate));
        abonament.setTip();
        return abonament;
    }

    private static CartelaConsum extractCartelaFromResultSet(ResultSet rs) throws SQLException {
        CartelaConsum cc = new CartelaConsum();
        cc.setNrCalatorii(rs.getInt("NrCalatorii"));
        cc.manualId(rs.getLong("ID"));
        cc.setTip();
        return cc;
    }


    private static LinkedList<Cartela> carteleFromDB(Connection myConn) {
        LinkedList<Cartela> cartele = new LinkedList<>();
        String query = "select * from cartele;";
        try {
            Statement statement = myConn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                if (resultSet.getString("Tip").equals("Abonament")) {
                    Abonament ab = extractAbonamentFromResultSet(resultSet);
                    cartele.add(ab);
                } else {
                    CartelaConsum cc = extractCartelaFromResultSet(resultSet);
                    cartele.add(cc);
                }
            }
            resultSet.close();
            statement.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartele;
    }


    public synchronized static void addAbonamentToDB(Abonament abonament) {

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

    public synchronized static void addCartelaConsumToDB(CartelaConsum cartelaConsum) {
        try {

            Connection myConn = DatabaseConnection.getMyConn();
            Statement statement = myConn.createStatement();


            String st = "insert into cartele (ID, Tip, NrCalatorii) values ( " +
                    cartelaConsum.getId() + ", " + "'Cartela', " +
                    cartelaConsum.getNrCalatorii() +
                    " );";

            statement.executeUpdate(st);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void removeCardFromDB(Cartela cartela){
        try {
            Statement statement = dbConnection.getMyConn().createStatement();
            String query = "DELETE FROM cartele WHERE ID = " + cartela.getId();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
