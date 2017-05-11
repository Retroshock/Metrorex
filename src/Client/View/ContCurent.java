package Client.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Created by Adrian on 18.04.2017.
 */
public class ContCurent extends JPanel {

    public DefaultTableModel columnModel;
    private JLabel title;
    public JTable table;
    private JScrollPane scrollPane;

    public ContCurent(){
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();


        title = new JLabel("Cont Curent:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        this.add(title,constraints);

        columnModel = new DefaultTableModel(new Object[] {"ID", "Tip", "Valabilitate", "Numar Calatorii", "Data Achizitiei" }
                , 1 );
        //Exemplu de adaugare in tabel
        //TODO Make it so it shows the entries from database in controller

        table = new JTable(columnModel);
        table.sizeColumnsToFit(JTable.AUTO_RESIZE_ALL_COLUMNS);


        scrollPane = new JScrollPane();

        scrollPane.setViewportView(table) ;
        constraints.gridy = 1;
        constraints.weighty = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.ipadx = 20;
        constraints.ipady = 20;
        scrollPane.setVisible(true);
        this.add(scrollPane, constraints);

        updateEntries(new Object[] {10, "Cartela", "null", 2, "20/10/2014"});
        updateEntries(new Object[] {10, "Abonament", "19/11/2014", "null", "20/10/2014"});
        updateEntries(new Object[] {10, "Cartela", "null", 2, "20/10/2014"});

    }

    public void updateEntries(Object[] obj){
        columnModel.addRow(obj);
        table = new JTable(columnModel);
        table.sizeColumnsToFit(JTable.AUTO_RESIZE_ALL_COLUMNS);
        //scrollPane.removeAll();
        scrollPane.setViewportView(table);
    }

}
