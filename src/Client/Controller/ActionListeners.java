package Client.Controller;

import Client.Constants.Strings;
import Client.Controller.*;
import Client.Model.Abonament;
import Client.Model.Cartela;
import Client.Model.CartelaConsum;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;

public class ActionListeners {

    public ActionListeners (){

    }

    public ActionListeners (JButton monthly, JButton daily, JButton ten, JButton two){
        setDaily(daily);
        setMonthly(monthly);
        setTen(ten);
        setTwo(two);
    }

    public static void setMonthly (JButton button){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                cal.add(Calendar.DATE, 30);
                Abonament abonament = new Abonament(new Date(), cal.getTime());
                //TODO SEND REQUEST TO DB
                abonament.setMessage(Strings.addToDatabaseRequest);
                Controller.sendRequestToServer(abonament);

                Controller.addAbonamentToInterface ( abonament );
                //Controller.addAbonamentToDB ( abonament );
                //System.out.println(abonament);
            }
        });
    }

    public static void setDaily (JButton button){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                cal.add(Calendar.DATE, 2);
                Abonament abonament = new Abonament(new Date(), cal.getTime());
                abonament.setMessage(Strings.addToDatabaseRequest);
                Controller.sendRequestToServer(abonament);
                //Controller.addAbonamentToDB(abonament);
            }
        });
    }

    public static void setTen (JButton button){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CartelaConsum cartela = new CartelaConsum(10);
                cartela.setMessage(Strings.addToDatabaseRequest);
                Controller.addCartelaToInterface(cartela);
                Controller.sendRequestToServer(cartela);
            }
        });
    }

    public static void setTwo (JButton button){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CartelaConsum cartela = new CartelaConsum(2);
                cartela.setMessage(Strings.addToDatabaseRequest);
                Controller.addCartelaToInterface(cartela);
                Controller.sendRequestToServer(cartela);
            }
        });
    }



}