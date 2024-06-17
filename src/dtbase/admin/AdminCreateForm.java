
package dtbase.admin;

import dtbase.MyJDBC;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import static dtbase.statement.get_data_contracts;

public class AdminCreateForm extends JFrame {
    private JPanel myFrame;
    private JTextField Company_NameTextField;
    private JTextField CargoTextField;
    private JTextField WeightTextField;
    private JButton createButton;
    private JButton editButton;
    private JButton deleteButton;
    private JComboBox trainscomboBox;
    private JButton refreshButton;
    private JCheckBox yesCheckBox;
    private JComboBox<Double> tariffcomboBox;
    private JComboBox dispcomboBox;
    private JComboBox arrcomboBox;
    private JTable table1;
    private JButton getButton;
    private JTabbedPane tabbedPane1;

    public AdminCreateForm() {
        setContentPane(myFrame);
        setTitle("Contracts");
        setSize(1000, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        Company_NameTextField.setEnabled(false);
        CargoTextField.setEnabled(false);
        WeightTextField.setEnabled(false);
        trainscomboBox.setEnabled(false);
        yesCheckBox.setEnabled(false);
        tariffcomboBox.setEnabled(false);
        dispcomboBox.setEnabled(false);
        arrcomboBox.setEnabled(false);
        createButton.setEnabled(false);
        editButton.setEnabled(false);
        getButton.setEnabled(false);
        deleteButton.setEnabled(false);
        try {
            Connection con = MyJDBC.getConnection();
            PreparedStatement pst1 = con.prepareStatement("select typeTrain from trains");
            ResultSet resultSet1 = pst1.executeQuery();
            while (resultSet1.next()) {
                String name = resultSet1.getString("typeTrain");
                trainscomboBox.addItem(name);
            }
            PreparedStatement pst2 = con.prepareStatement("select nameStation from stations");
            ResultSet resultSet2 = pst2.executeQuery();
            while (resultSet2.next()) {
                String name = resultSet2.getString("nameStation");
                dispcomboBox.addItem(name);
                arrcomboBox.addItem(name);
            }
            PreparedStatement pst3 = con.prepareStatement("select price from tariffs");
            ResultSet resultSet3 = pst3.executeQuery();
            while (resultSet3.next()) {
                Double doub = resultSet3.getDouble("price");
                tariffcomboBox.addItem(doub);
            }
        } catch (Exception ex) {
            System.out.println("" + ex);
        }

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dispcomboBox.getSelectedIndex()==arrcomboBox.getSelectedIndex()
                        || Company_NameTextField.getText()==""
                        ||CargoTextField.getText()==""
                        ||WeightTextField.getText()==""
                        || (WeightTextField.getText().matches("-?\\d+(\\.\\d+)?"))==false) {
                    JOptionPane.showMessageDialog(myFrame,"Some Field Are Wrong!","Error",1);
                } else {
                    int confirmed = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to create this contract?", "Check Message",
                            JOptionPane.YES_NO_OPTION);
                    if (confirmed == JOptionPane.YES_OPTION) {
                        String Cargo = CargoTextField.getText();
                        double Weight = Double.parseDouble(WeightTextField.getText());
                        double res = Double.parseDouble(WeightTextField.getText()) * tariffcomboBox.getItemAt(tariffcomboBox.getSelectedIndex());
                        double price = tariffcomboBox.getItemAt(tariffcomboBox.getSelectedIndex());
                        try {
                            Connection con = MyJDBC.getConnection();
                            Statement st = con.createStatement();
                            String exst = "insert ignore into clients (nameClientCompany) values ('" + Company_NameTextField.getText() + "')";
                            st.executeUpdate(exst);
                            String exst1 = "insert into payments (price, datePayment, idtariffs) values (" + res + ", " +
                                    "curdate(), (select idtariffs from tariffs where price=" + price + "))";
                            st.executeUpdate(exst1);
                            exst = "insert into insurance (statusInsurance) values (" + yesCheckBox.isSelected() + ")";
                            st.executeUpdate(exst);
                            exst = "insert into contracts (typeCargo, " +
                                    "weightCargo, contractDate, contractStatus, idpayment, " +
                                    "idinsurance, idclients, idtrains, contractstations) " +
                                    "values ('" + Cargo + "', " + Weight + ", " +
                                    "curdate(), " +
                                    "'Uncompleted', " +
                                    "(select max(idpayment) from payments), " +
                                    "(select max(idinsurance) from insurance), " +
                                    "(select idclients from clients where nameClientCompany='" + Company_NameTextField.getText() + "'), " +
                                    "(select idtrains from trains where typeTrain='" + trainscomboBox.getSelectedItem() + "'), " +
                                    "(select idcontractstations from contractstations where dispatchStation=(select idstations from stations where nameStation='" + dispcomboBox.getSelectedItem() + "') and arrivalStation=(select idstations from stations where nameStation='" + arrcomboBox.getSelectedItem() + "')))";
                            st.executeUpdate(exst);
                        } catch (Exception ex) {
                            System.out.println("" + ex);
                        }
                    }
                }
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table1.setModel(new DefaultTableModel());
                createButton.setEnabled(true);
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);
                Company_NameTextField.setEnabled(true);
                CargoTextField.setEnabled(true);
                WeightTextField.setEnabled(true);
                trainscomboBox.setEnabled(true);
                yesCheckBox.setEnabled(true);
                tariffcomboBox.setEnabled(true);
                dispcomboBox.setEnabled(true);
                arrcomboBox.setEnabled(true);
                getButton.setEnabled(true);
                try {
                    Connection con = MyJDBC.getConnection();
                    PreparedStatement rpst = con.prepareStatement(get_data_contracts);
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
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel tm1 = (DefaultTableModel) table1.getModel();
                int row = table1.getSelectedRow();
                if (table1.getSelectedRowCount()==1) {
                    int confirmed = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to delete this contract?", "Check Message",
                            JOptionPane.YES_NO_OPTION);
                    if (confirmed == JOptionPane.YES_OPTION) {
                        try {
                            String cell = table1.getModel().getValueAt(row, 0).toString();
                            Connection con = MyJDBC.getConnection();
                            String deleteSt = "delete from contracts where idContracts = ?";
                            PreparedStatement pst = con.prepareStatement(deleteSt);
                            pst.setString(1, cell);
                            pst.execute();
                            tm1.removeRow(table1.getSelectedRow());
                        } catch (Exception ex) {
                            System.out.println("" + ex);
                        }
                    }
                }
                else {
                    if (table1.getRowCount()==0){
                        JOptionPane.showMessageDialog(myFrame,"Table is Empty!","Error",1);
                    }
                    else {
                        JOptionPane.showMessageDialog(myFrame,"Select Row!","Error",1);
                    }
                }

            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dispcomboBox.getSelectedIndex()==arrcomboBox.getSelectedIndex()
                        || Company_NameTextField.getText()==""
                        ||CargoTextField.getText()==""
                        ||WeightTextField.getText()=="") {
                    JOptionPane.showMessageDialog(myFrame,"Some Field Are Wrong!","Error",1);
                } else {
                    int confirmed = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to edit this contract?", "Check Message",
                            JOptionPane.YES_NO_OPTION);
                    if (confirmed == JOptionPane.YES_OPTION) {
                        String Cargo = CargoTextField.getText();
                        double Weight = Double.parseDouble(WeightTextField.getText());
                        double res = Double.parseDouble(WeightTextField.getText()) * tariffcomboBox.getItemAt(tariffcomboBox.getSelectedIndex());
                        double price = tariffcomboBox.getItemAt(tariffcomboBox.getSelectedIndex());

                        int row = table1.getSelectedRow();
                        TableModel model = table1.getModel();
                        String ID = (String) model.getValueAt(row, 0);
                        try {
                            Connection con = MyJDBC.getConnection();
                            Statement st = con.createStatement();
                            String exst = "update ignore clients set nameClientCompany = '" + Company_NameTextField.getText() + "' where nameClientCompany = '" + model.getValueAt(row, 7) + "'";
                            st.executeUpdate(exst);
                            exst = "update payments inner join contracts on contracts.idpayment = payments.idpayment set price=" + res + ", idtariffs = (select idtariffs from tariffs where price=" + price + ") where contracts.idContracts = " + model.getValueAt(row, 0);
                            st.executeUpdate(exst);
                            exst = "update insurance inner join contracts on contracts.idinsurance = insurance.idinsurance  set statusInsurance = " + yesCheckBox.isSelected() + " where contracts.idContracts = " + model.getValueAt(row, 0);
                            st.executeUpdate(exst);
                            exst = "update ignore contractstations inner join contracts on contracts.contractstations = contractstations.idcontractstations set dispatchStation = (select idstations from stations where nameStation='" + dispcomboBox.getSelectedItem() + "'), arrivalStation = (select idstations from stations where nameStation='" + arrcomboBox.getSelectedItem() + "') where" +
                                    " contracts.idContracts = " + model.getValueAt(row, 0);
                            st.executeUpdate(exst);
                            exst = "update contracts set typeCargo = '" + Cargo + "', weightCargo = " + Weight + ", contractStatus = 'Edited' where idContracts = " + ID;
                            st.executeUpdate(exst);
                        } catch (Exception ex) {
                            System.out.println("" + ex);
                        }
                    }
                }

            }
        });
        getButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                TableModel model = table1.getModel();
                Company_NameTextField.setText((String) model.getValueAt(row,7));
                CargoTextField.setText((String) model.getValueAt(row,1));
                WeightTextField.setText((String) model.getValueAt(row,2));
                trainscomboBox.setSelectedItem(model.getValueAt(row,8));
                if (model.getValueAt(row,6).toString()=="1"){
                    yesCheckBox.setSelected(true);
                }
                else {
                    yesCheckBox.setSelected(false);
                }
                dispcomboBox.setSelectedItem(model.getValueAt(row,9));
                arrcomboBox.setSelectedItem(model.getValueAt(row,10));
            }
        });
    }
    public static void main(String[] args) {
        AdminCreateForm myFrame = new AdminCreateForm();
    }
}

