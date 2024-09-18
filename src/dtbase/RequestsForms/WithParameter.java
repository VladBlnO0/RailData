package dtbase.RequestsForms;

import dtbase.MyJDBC;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import static dtbase.statement.*;

public class WithParameter extends JFrame{
    private JPanel panel1;
    private JTable table1;
    private JTextField textField1;
    private JButton button1;
    private JComboBox comboBox1;
    private JComboBox comboBox2;

    public WithParameter(int ch) {
        setContentPane(panel1);
        setTitle("Get Info");
        setSize(600,400);
        setLocationRelativeTo(null);
        setVisible(true);
        switch (ch) {
            case 1, 5:
                comboBox1.setEnabled(false);
                comboBox2.setEnabled(false);
                break;
            case 2:
                textField1.setEnabled(false);
                try {
                    Connection con = MyJDBC.getConnection();
                    PreparedStatement pst = con.prepareStatement("select nameClientCompany from clients");
                    ResultSet resultSet = pst.executeQuery();
                    while (resultSet.next()) {
                        String name = resultSet.getString("nameClientCompany");
                        comboBox1.addItem(name);
                        comboBox2.addItem(name);
                    }
                } catch (Exception ex) {
                    System.out.println("" + ex);
                }
                break;
        }
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table1.setModel(new DefaultTableModel());
                switch (ch) {
                    case 1:
                        String letter = textField1.getText();
                        if (letter.equals("")) {
                            JOptionPane.showMessageDialog(panel1, "Some Field Are Empty!", "Error", 1);
                        } else {
                            try {
                                Connection con = MyJDBC.getConnection();
                                PreparedStatement pst = con.prepareStatement("select * from stations where nameStation like ?");
                                pst.setString(1, letter.substring(0,1) +"%");
                                ResultSet resultSet = pst.executeQuery();
                                ResultSetMetaData rsmd = resultSet.getMetaData();
                                DefaultTableModel model = (DefaultTableModel) table1.getModel();
                                int cols = rsmd.getColumnCount();
                                String[] colName = new String[cols];
                                String[] colStringNames = {"ID", "Station"};
                                for (int i = 0; i < cols; i++) {
                                    colName[i] = colStringNames[i];
                                }
                                model.setColumnIdentifiers(colName);
                                String idstations, nameStation;
                                while (resultSet.next()) {
                                    idstations = resultSet.getString(1);
                                    nameStation = resultSet.getString(2);
                                    String[] row = {idstations, nameStation};
                                    model.addRow(row);
                                }
                            } catch (Exception ex) {
                                System.out.println("" + ex);
                            }
                        }
                        break;
                    case 2:
                        if (((comboBox1.getSelectedItem())==null) || (comboBox2.getSelectedItem()==null)) {
                            JOptionPane.showMessageDialog(panel1, "Some Field Are Empty!", "Error", 1);
                        } else {
                            try {
                                Connection con = MyJDBC.getConnection();
                                PreparedStatement pst = con.prepareStatement("select * from clients " +
                                        "where nameClientCompany between ? and ?");
                                String obj1 = String.valueOf(comboBox1.getSelectedItem());
                                String obj2 = String.valueOf(comboBox2.getSelectedItem());
                                pst.setString(1, obj1);
                                pst.setString(2, obj2);
                                ResultSet resultSet = pst.executeQuery();
                                ResultSetMetaData rsmd = resultSet.getMetaData();
                                DefaultTableModel model = (DefaultTableModel) table1.getModel();
                                int cols = rsmd.getColumnCount();
                                String[] colName = new String[cols];
                                String[] colStringNames = {"ID", "Company"};
                                for (int i = 0; i < cols; i++) {
                                    colName[i] = colStringNames[i];
                                }
                                model.setColumnIdentifiers(colName);
                                String idclients, nameClientCompany;
                                while (resultSet.next()) {
                                    idclients = resultSet.getString(1);
                                    nameClientCompany = resultSet.getString(2);
                                    String[] row = {idclients, nameClientCompany};
                                    model.addRow(row);
                                }
                            } catch (Exception ex) {
                                System.out.println("" + ex);
                            }
                        }
                        break;
                    case 5:
                        if (textField1.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(panel1, "Some Field Are Empty!", "Error", 1);
                        }
                        else {
                            try {
                                double doub = Double.parseDouble(textField1.getText());
                                try {
                                    Connection con = MyJDBC.getConnection();
                                    PreparedStatement pst = con.prepareStatement(bigger_tariffs);
                                    pst.setString(1, String.valueOf(doub));
                                    ResultSet resultSet = pst.executeQuery();
                                    ResultSetMetaData rsmd = resultSet.getMetaData();
                                    DefaultTableModel model = (DefaultTableModel) table1.getModel();
                                    int cols = rsmd.getColumnCount();
                                    String[] colName = new String[cols];
                                    String[] colStringNames = {"ID", "Price"};
                                    for (int i = 0; i < cols; i++) {
                                        colName[i] = colStringNames[i];
                                    }
                                    model.setColumnIdentifiers(colName);
                                    String nameTariff, price;
                                    while (resultSet.next()) {
                                        nameTariff = resultSet.getString(1);
                                        price = resultSet.getString(2);
                                        String[] row = {nameTariff, price};
                                        model.addRow(row);
                                    }
                                } catch (Exception ex) {
                                    System.out.println("" + ex);
                                }
                            } catch (NumberFormatException nfe) {
                                JOptionPane.showMessageDialog(panel1, "String Is Not Integer!", "Error", 1);
                            }
                        }
                        break;
                }
            }
        });
    }
}
