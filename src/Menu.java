import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Menu extends JFrame {
    public static void main(String[] args) {
        Menu window = new Menu();
        window.setSize(480, 560);
        window.setLocationRelativeTo(null);
        window.setContentPane(window.mainPanel);
        window.getContentPane().setBackground(Color.white);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private int selectedIndex = -1;
    private Database database;

    private JPanel mainPanel;
    private JTextField nimField;
    private JTextField namaField;
    private JTable mahasiswaTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox<String> jenisKelaminComboBox;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel nimLabel;
    private JLabel namaLabel;
    private JLabel jenisKelaminLabel;

    public Menu() {
        database = new Database();
        mahasiswaTable.setModel(setTable());

        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));
        String[] jenisKelaminData = {"", "Laki-laki", "Perempuan"};
        jenisKelaminComboBox.setModel(new DefaultComboBoxModel<>(jenisKelaminData));
        deleteButton.setVisible(false);

        addUpdateButton.addActionListener(e -> {
            if (nimField.getText().isEmpty() || namaField.getText().isEmpty() || jenisKelaminComboBox.getSelectedItem().toString().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua kolom harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (selectedIndex == -1) {
                insertData();
            } else {
                updateData();
            }
        });

        deleteButton.addActionListener(e -> {
            if (selectedIndex >= 0) {
                deleteData();
            }
        });

        cancelButton.addActionListener(e -> clearForm());

        mahasiswaTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedIndex = mahasiswaTable.getSelectedRow();
                nimField.setText(mahasiswaTable.getModel().getValueAt(selectedIndex, 1).toString());
                namaField.setText(mahasiswaTable.getModel().getValueAt(selectedIndex, 2).toString());
                jenisKelaminComboBox.setSelectedItem(mahasiswaTable.getModel().getValueAt(selectedIndex, 3).toString());
                addUpdateButton.setText("Update");
                deleteButton.setVisible(true);
            }
        });
    }

    public final DefaultTableModel setTable() {
        Object[] column = {"No", "NIM", "Nama", "Jenis Kelamin"};
        DefaultTableModel temp = new DefaultTableModel(null, column);

        try {
            ResultSet resultSet = database.selectQuery("SELECT * FROM mahasiswa");
            int i = 0;
            while (resultSet.next()) {
                Object[] row = new Object[4];
                row[0] = i + 1;
                row[1] = resultSet.getString("nim");
                row[2] = resultSet.getString("nama");
                row[3] = resultSet.getString("jenis_kelamin");
                temp.addRow(row);
                i++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return temp;
    }

    public void insertData() {
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();

        try {
            ResultSet rs = database.selectQuery("SELECT nim FROM mahasiswa WHERE nim = '" + nim + "'");
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "NIM sudah terdaftar!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String sql = "INSERT INTO mahasiswa (nim, nama, jenis_kelamin) VALUES ('" + nim + "', '" + nama + "', '" + jenisKelamin + "')";
        database.insertUpdateDeleteQuery(sql);
        mahasiswaTable.setModel(setTable());
        clearForm();
        JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
    }

    public void updateData() {
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();

        String sql = "UPDATE mahasiswa SET nama='" + nama + "', jenis_kelamin='" + jenisKelamin + "' WHERE nim='" + nim + "'";
        database.insertUpdateDeleteQuery(sql);
        mahasiswaTable.setModel(setTable());
        clearForm();
        JOptionPane.showMessageDialog(null, "Data berhasil diubah");
    }

    public void deleteData() {
        String nim = nimField.getText();
        String sql = "DELETE FROM mahasiswa WHERE nim='" + nim + "'";
        database.insertUpdateDeleteQuery(sql);
        mahasiswaTable.setModel(setTable());
        clearForm();
        JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
    }

    public void clearForm() {
        nimField.setText("");
        namaField.setText("");
        jenisKelaminComboBox.setSelectedItem("");
        addUpdateButton.setText("Add");
        deleteButton.setVisible(false);
        selectedIndex = -1;
    }
}
