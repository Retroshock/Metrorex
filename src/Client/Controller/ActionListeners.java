package Client.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Adrian on 19.04.2017.
 */
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

            }
        });
    }

    public static void setDaily (JButton button){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void setTen (JButton button){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void setTwo (JButton button){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }



}
