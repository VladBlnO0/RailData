package dtbase.client;

import dtbase.MyJDBC;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class ClientGetForm extends JFrame {
    private JPanel panel1;
    private JTable table1;
    private JButton getButton;

    public ClientGetForm(String ClientCompany) {
        setContentPane(panel1);
        setTitle("Client Contracts");
        setSize(1000,300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        getButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table1.setModel(new DefaultTableModel());
                try {
                    Connection con = MyJDBC.getConnection();
                    PreparedStatement rpst = con.prepareStatement("select idContracts, typeCargo, weightCargo, contractDate, contractStatus, price, statusInsurance, nameClientCompany, typeTrain, " +
                            "(select nameStation from stations where contractstations.dispatchStation=stations.idstations), " +
                            "(select nameStation from stations where contractstations.arrivalStation=stations.idstations) " +
                            "from contracts " +
                            "inner join payments on contracts.idpayment = payments.idpayment " +
                            "inner join insurance on contracts.idinsurance = insurance.idinsurance " +
                            "inner join clients on contracts.idclients = clients.idclients " +
                            "inner join trains on contracts.idtrains = trains.idtrains " +
                            "inner join contractstations on contracts.contractstations = contractstations.idcontractstations " +
                            "where nameClientCompany ='"+ClientCompany+"'");
                    ResultSet resultSet = rpst.executeQuery();
                    ResultSetMetaData rsmd = resultSet.getMetaData();
                    DefaultTableModel model = (DefaultTableModel) table1.getModel();
                    int cols = rsmd.getColumnCount();
                    String[] colName = new String[cols];
                    String[] colStringNames = {"ID", "Cargo", "Weight", "Date", "Status", "Price", "Insurance", "Company", "Train", "Dispatch", "Arrival"};
                    for (int i = 0; i < cols; i++) {
                        colName[i] = colStringNames[i];
                    }
                    model.setColumnIdentifiers(colName);
                    String idContracts, Cargo, Weight, Date, Status, Price, Insurance, Company, Train, Dispatch, Arrival;
                    while (resultSet.next()) {
                        idContracts = resultSet.getString(1);
                        Cargo = resultSet.getString(2);
                        Weight = resultSet.getString(3);
                        Date = resultSet.getString(4);
                        Status = resultSet.getString(5);
                        Price = resultSet.getString(6);
                        Insurance = resultSet.getString(7);
                        Company = resultSet.getString(8);
                        Train = resultSet.getString(9);
                        Dispatch = resultSet.getString(10);
                        Arrival = resultSet.getString(11);
                        String[] row = {idContracts, Cargo, Weight, Date, Status, Price, Insurance, Company, Train, Dispatch, Arrival};
                        model.addRow(row);
                    }
                } catch (Exception ex) {
                    System.out.println("" + ex);
                }
            }
        });
    }
}
