package Client.Controller;

import Client.Constants.Strings;
import Client.Controller.*;
import Client.Model.Abonament;
import Client.Model.Cartela;
import Client.Model.CartelaConsum;
import Client.Model.Valabilitate;
import Client.View.BuyMenu;
import Client.View.ContCurent;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ActionListeners {

    private static Cartela validationCard;
    private static int selectedRow;

    public ActionListeners() {

    }

    public ActionListeners(BuyMenu buyMenu, ContCurent contCurent) {
        setDaily(buyMenu.getBuyDayBtn());
        setMonthly(buyMenu.getBuyMonthBtn());
        setTen(buyMenu.getBuyTenBtn());
        setTwo(buyMenu.getBuyTwoBtn());
        setValidation(buyMenu, contCurent);
        getTableSelectionAndAdd(contCurent, buyMenu);
    }

    public static void setMonthly(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                cal.add(Calendar.DATE, 30);
                Controller.sendRequestToServer(Strings.refreshIDs);
                Abonament abonament = new Abonament(new Date(), cal.getTime());
                //TODO SEND REQUEST TO DB
                abonament.setMessage(Strings.addToDatabaseRequest);
                Controller.sendRequestToServer(abonament);

                Controller.addAbonamentToInterface(abonament);
                //Controller.addAbonamentToDB ( abonament );
                //System.out.println(abonament);
            }
        });
    }

    public static void setDaily(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                cal.add(Calendar.DATE, 1);
                Controller.sendRequestToServer(Strings.refreshIDs);
                Abonament abonament = new Abonament(new Date(), cal.getTime());
                abonament.setMessage(Strings.addToDatabaseRequest);
                Controller.addAbonamentToInterface(abonament);
                Controller.sendRequestToServer(abonament);
                //Controller.addAbonamentToDB(abonament);
            }
        });
    }

    public static void setTen(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.sendRequestToServer(Strings.refreshIDs);
                CartelaConsum cartela = new CartelaConsum(10);
                cartela.setMessage(Strings.addToDatabaseRequest);
                Controller.addCartelaToInterface(cartela);
                Controller.sendRequestToServer(cartela);
            }
        });
    }

    public static void setTwo(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.sendRequestToServer(Strings.refreshIDs);
                CartelaConsum cartela = new CartelaConsum(2);
                cartela.setMessage(Strings.addToDatabaseRequest);
                Controller.addCartelaToInterface(cartela);
                Controller.sendRequestToServer(cartela);
            }
        });
    }

    public static void getTableSelectionAndAdd(ContCurent contCurent, BuyMenu buyMenu) {
        JTable table = contCurent.getTable();
        MouseListener tableMouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedRow = table.rowAtPoint(e.getPoint());
                buyMenu.getValidationBtn().setEnabled(true);
            }
        };

        table.addMouseListener(tableMouseListener);

       /* table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                // aici trebuie sa fac vizibil butonul de validare
                // trebuie sa returnez cartela selectata

                cartela.manualId((Long)table.getValueAt(table.getSelectedRow(), 0));
                if (cartela.getId() != 0){
                    cartela.setMessage(Strings.validateCardRequest);
                    setValidation(buyMenu.getValidationBtn(), cartela);
                }
                System.out.println(table.getValueAt(table.getSelectedRow(), 0).toString());
            }
        });*/

    }


    public static void setValidation(BuyMenu buyMenu, ContCurent contCurent) {

        buyMenu.getValidationBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CartelaConsum cartela = new CartelaConsum();
                cartela.manualId((Long) contCurent.getTable().getValueAt(selectedRow, 0));
                if (cartela.getId() != 0) {
                    Integer nrCalatorii;
                    if ((nrCalatorii = (Integer) contCurent.getTable().getValueAt(selectedRow, 3)) != null){
                        if (nrCalatorii > 0){
                            cartela.setNrCalatorii(nrCalatorii);
                            cartela.useOne();
                            contCurent.getColumnModel().setValueAt(cartela.getNrCalatorii(),selectedRow,3);
                        }
                    }
                    cartela.setMessage(Strings.validateCardRequest);
                    Controller.sendRequestToServer(cartela);
                    contCurent.getTable().clearSelection();
                    buyMenu.getValidationBtn().setEnabled(false);
                }
            }
        });
    }
}