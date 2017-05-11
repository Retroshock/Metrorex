package Client.Controller;

import Client.Constants.Strings;
import Client.Model.*;
import Client.View.PaintView;

import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import javax.xml.transform.Result;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Adrian on 19.04.2017.
 */
public class Controller {
    private static ArrayList<Abonament> abonamente= new ArrayList <>();
    private static ArrayList<CartelaConsum> cartele= new ArrayList <>();
    private static PaintView view;
    private ActionListeners actionListeners;
    private static Socket socket;
    private static ObjectInputStream objectInputStream;
    private static ObjectOutputStream objectOutputStream;

    public Controller() {

        //thread pentru partea view
        new Thread(new Runnable() {
            @Override
            public void run() {
                view = new PaintView();

                JButton buttonMonth = view.getClientInterface().getBuyMenu().getBuyMonthBtn();
                JButton buttonDay = view.getClientInterface().getBuyMenu().getBuyDayBtn();
                JButton buttonTen= view.getClientInterface().getBuyMenu().getBuyTenBtn();
                JButton buttonTwo = view.getClientInterface().getBuyMenu().getBuyTwoBtn();

                actionListeners = new ActionListeners(buttonMonth,buttonDay,buttonTen,buttonTwo);
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {

                updateContCurentFromDB();

                //closeConnectionToServer();
            }
        }).start();






    }

    private static void updateContCurentFromDB() {
        sendRequestToServer(Strings.showCardsForUserRequest);
    }

    private void closeConnectionToServer() {
        try {
            socket.close();
            objectInputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
            System.out.println("Eroare la Inchidere Socket!");
            e.printStackTrace();
        }
    }





    public static void sendRequestToServer (Cartela cartela){
        try {
            socket = new Socket("localhost", 9002);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(cartela);
            objectOutputStream.flush();

            objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object ob = objectInputStream.readObject();
            objectInputStream.close();
            objectOutputStream.close();
            socket.close();
            processResponse(ob);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void sendExitRequestToServer(String exit){
        try {
            socket = new Socket("localhost", 9002);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(exit);
            objectOutputStream.flush();
            objectOutputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequestToServer(String string){
        try {
            socket = new Socket("localhost", 9002);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(string);
            objectOutputStream.flush();

            objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object ob = objectInputStream.readObject();
            objectInputStream.close();
            objectOutputStream.close();
            socket.close();
            processResponse(ob);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    private static void processResponse(Object ob) {
        //TODO Proceseaza response de la server
        if (ob instanceof Cartela){

        }
        if (ob instanceof String){

        }
    }





    public synchronized static void addAbonamentToInterface (Abonament abonament){
        abonamente.add(abonament);
        SimpleDateFormat sdf = new SimpleDateFormat(Strings.dateFormatString);
        view.getClientInterface().getContCurent().updateEntries(new Object[] {abonament.getId(), "Abonament",
                sdf.format(abonament.getValabilitate().getEnd())
                , ""
                , sdf.format(abonament.getValabilitate().getStart())});

    }

    public synchronized static void addCartelaToInterface (CartelaConsum cartelaConsum){
        cartele.add(cartelaConsum);
        view.getClientInterface().getContCurent().updateEntries(new Object[] {cartelaConsum.getId(), "Cartela"
                , "" +
                cartelaConsum.getNrCalatorii()
                , "" });
    }


    public static void main(String[] args) {
        Controller c = new Controller();
    }

}
