package dtbase.RequestsForms;

import dtbase.MyJDBC;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import static dtbase.statement.*;

public class WithoutParameter extends JFrame{

    private JPanel sortPane;
    private JTable table1;
    private JButton showButton;
    private void SortPayments() {
        try {
            Connection con = MyJDBC.getConnection();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sort_payments);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            DefaultTableModel model = (DefaultTableModel) table1.getModel();
            int cols = rsmd.getColumnCount();
            String[] colName = new String[cols];
            String[] colStringNames = {"ID", "Price", "Date", "Tariff"};
            for (int i = 0; i < cols; i++) {
                colName[i] = colStringNames[i];
            }
            model.setColumnIdentifiers(colName);
            String idpayment, price, datePayment, idtariffs;
            while (resultSet.next()) {
                idpayment = resultSet.getString(1);
                price = resultSet.getString(2);
                datePayment = resultSet.getString(3);
                idtariffs = resultSet.getString(4);
                String[] row = {idpayment, price, datePayment, idtariffs};
                model.addRow(row);
            }

        } catch (Exception ex) {
            System.out.println("" + ex);
        }
    }
    private void ContractsMonth() {
        try {
            Connection con = MyJDBC.getConnection();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(contract_month);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            DefaultTableModel model = (DefaultTableModel) table1.getModel();
            int cols = rsmd.getColumnCount();
            String[] colName = new String[cols];
            colName[0] = "Number of Contracts";
            model.setColumnIdentifiers(colName);
            String numContracts;
            while (resultSet.next()) {
                numContracts = resultSet.getString(1);
                String[] row = {numContracts};
                model.addRow(row);
            }
        } catch (Exception ex) {
            System.out.println("" + ex);
        }
    }
    private void CountContracts() {
        try {
            Connection con = MyJDBC.getConnection();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(count_contract);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            DefaultTableModel model = (DefaultTableModel) table1.getModel();
            int cols = rsmd.getColumnCount();
            String[] colName = new String[cols];
            colName[0] = "Company";
            colName[1] = "Number of Contracts";
            model.setColumnIdentifiers(colName);
            String nameClientCompany, numContracts;
            while (resultSet.next()) {
                nameClientCompany = resultSet.getString(1);
                numContracts = resultSet.getString(2);
                String[] row = {nameClientCompany, numContracts};
                model.addRow(row);
            }
        } catch (Exception ex) {
            System.out.println("" + ex);
        }
    }
    public WithoutParameter(int ch) {
        setContentPane(sortPane);
        setTitle("Get Info");
        setSize(400,400);
        setLocationRelativeTo(null);
        setVisible(true);
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table1.setModel(new DefaultTableModel());
                switch (ch) {
                    case 0:
                        SortPayments();
                        break;
                    case 3:
                        ContractsMonth();
                        break;
                    case 4:
                        CountContracts();
                        break;
                    case 6:
                        try {
                            Connection con = MyJDBC.getConnection();
                            Statement statement = con.createStatement();
                            ResultSet resultSet = statement.executeQuery(companies_contr);
                            ResultSetMetaData rsmd = resultSet.getMetaData();
                            DefaultTableModel model = (DefaultTableModel) table1.getModel();
                            int cols = rsmd.getColumnCount();
                            String[] colName = new String[cols];
                            colName[0] = "Company";
                            colName[1] = "Costly payment";
                            model.setColumnIdentifiers(colName);
                            String nameClientCompany,price;
                            while (resultSet.next()) {
                                nameClientCompany = resultSet.getString(1);
                                price = resultSet.getString(2);
                                String[] row = {nameClientCompany,price};
                                model.addRow(row);
                            }

                        } catch (Exception ex) {
                            System.out.println("" + ex);
                        }
                        break;
                    case 7:
                        try {
                            Connection con = MyJDBC.getConnection();
                            Statement statement = con.createStatement();
                            ResultSet resultSet = statement.executeQuery(trains_not_active);
                            ResultSetMetaData rsmd = resultSet.getMetaData();
                            DefaultTableModel model = (DefaultTableModel) table1.getModel();
                            int cols = rsmd.getColumnCount();
                            String[] colName = new String[cols];
                            colName[0] = "Trains not Active";
                            model.setColumnIdentifiers(colName);
                            String typeTrain;
                            while (resultSet.next()) {
                                typeTrain = resultSet.getString(1);
                                String[] row = {typeTrain};
                                model.addRow(row);
                            }

                        } catch (Exception ex) {
                            System.out.println("" + ex);
                        }
                        break;
                    case 8:
                        try {
                            Connection con = MyJDBC.getConnection();
                            Statement statement = con.createStatement();
                            ResultSet resultSet = statement.executeQuery(union_weight);
                            ResultSetMetaData rsmd = resultSet.getMetaData();
                            DefaultTableModel model = (DefaultTableModel) table1.getModel();
                            int cols = rsmd.getColumnCount();
                            String[] colName = new String[cols];
                            String[] colStringNames = {"ID", "Cargo", "Weight"};
                            for (int i = 0; i < cols; i++) {
                                colName[i] = colStringNames[i];
                            }
                            model.setColumnIdentifiers(colName);
                            String idContracts, typeCargo, weightCargo;
                            while (resultSet.next()) {
                                idContracts = resultSet.getString(1);
                                typeCargo = resultSet.getString(2);
                                weightCargo = resultSet.getString(3);
                                String[] row = {idContracts, typeCargo, weightCargo};
                                model.addRow(row);
                            }

                        } catch (Exception ex) {
                            System.out.println("" + ex);
                        }
                        break;
                }
            }
        });
    }
}
