package product.UI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import product.Database.GetProductListDAOMySQL;
import product.Entity.CeramicsProduct;
import product.Entity.ElectronicsProduct;
import product.Entity.FoodProduct;
import product.Entity.Product;



public class GetProductListView {

    private List<GetProductListViewModel> products = null; 
    private GetProductListDAOMySQL getProductListDAOMySQL;
    

    // Modify the constructor to accept GetProductListDAOMySQL
    public GetProductListView(GetProductListDAOMySQL getProductListDAOMySQL) {
        this.getProductListDAOMySQL = getProductListDAOMySQL;
        
    }

    public void createAndshowGUI(List <GetProductListViewModel> products){
        this.products = products;

        JFrame frame = new JFrame("Product Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 450);

        JLabel titleLabel = new JLabel("Danh sách sản phẩm", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD,24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0,0,0 ));

        String [] columns = {
            "Mã Hàng", "Tên Hàng", "Số Lượng", "Đơn Giá", "Loại Hàng", "VAT"
        };

         // Create table model
         DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
         JTable table = new JTable(tableModel);

       

         //add product data to the model
         for(int i=0; i< products.size(); i++){
            GetProductListViewModel product = products.get(i);
            Object[] row ={
                product.maHang,
                product.tenHang,
                product.soLuong,
                product.donGia,
                product.loaiHang,
                product.tinhVAT
            };
            tableModel.addRow(row);
         }

         // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        

        // Panel for input fields and labels
        JPanel inputPanel = new JPanel(new GridLayout(4, 4, 10, 10));

        inputPanel.add(new JLabel("Mã sản phẩm:"));
        JTextField maSanPhamField = new JTextField();
        inputPanel.add(maSanPhamField);

        inputPanel.add(new JLabel("Tên sản phẩm:"));
        JTextField tenSanPhamField = new JTextField();
        inputPanel.add(tenSanPhamField);

        inputPanel.add(new JLabel("Số lượng:"));
        JTextField soLuongField = new JTextField();
        inputPanel.add(soLuongField);

        inputPanel.add(new JLabel("Đơn giá:"));
        JTextField donGiaField = new JTextField();
        inputPanel.add(donGiaField);

        inputPanel.add(new JLabel("Loại hàng:"));
        JComboBox<String> loaiHangComboBox = new JComboBox<>(new String[]{"Electronics", "Food", "Ceramics"});
        inputPanel.add(loaiHangComboBox);

        inputPanel.add(new JLabel("Ngày sản xuất:"));
        JTextField ngaySanXuatField = new JTextField();
        inputPanel.add(ngaySanXuatField);

        inputPanel.add(new JLabel("Ngày hết hạn:"));
        JTextField ngayHetHanField = new JTextField();
        inputPanel.add(ngayHetHanField);

        inputPanel.add(new JLabel("Nhà cung cấp:"));
        JTextField nhaCungCapField = new JTextField();
        inputPanel.add(nhaCungCapField);

        inputPanel.add(new JLabel("TG Bảo hành:"));
        JTextField tgBaoHanhField = new JTextField();
        inputPanel.add(tgBaoHanhField);

        inputPanel.add(new JLabel("Công suất:"));
        JTextField congSuatField = new JTextField();
        inputPanel.add(congSuatField);

        inputPanel.add(new JLabel("Nhà sản xuất:"));
        JTextField nhaSanXuatField = new JTextField();
        inputPanel.add(nhaSanXuatField);

        

        inputPanel.add(new JLabel("Ngày nhập kho:"));
        JTextField ngayNhapKhoField = new JTextField();
        inputPanel.add(ngayNhapKhoField);

        

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 10));

        JButton addButton = new JButton("Thêm");
        JButton editButton = new JButton("Sửa");
        JButton deleteButton = new JButton("Xóa");
        JButton totalButton = new JButton("Tính tổng");
        JButton searchButton = new JButton("Tìm kiếm");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(totalButton);
        buttonPanel.add(searchButton);
        //setup layout for the frame
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Set up layout for the frame
        frame.setLayout(new BorderLayout());
        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

         frame.setLocationRelativeTo(null);
         frame.setVisible(true);

         addButton.addActionListener(e -> {
            try {
                // Gather input data
                int maHang = Integer.parseInt(maSanPhamField.getText());
                String tenHang = tenSanPhamField.getText();
                int soLuong = Integer.parseInt(soLuongField.getText());
                double donGia = Double.parseDouble(donGiaField.getText());
                String loaiHang = (String) loaiHangComboBox.getSelectedItem();
        
                // Create an AddProduct instance based on the selected type
                Product newProduct = null;
                if (loaiHang.equals("Food")) {
                    Date ngaySanXuat = Date.valueOf(ngaySanXuatField.getText());
                    Date ngayHetHan = Date.valueOf(ngayHetHanField.getText());
                    String nhaCungCap = nhaCungCapField.getText();
                    newProduct = new FoodProduct(maHang, tenHang, soLuong, donGia, loaiHang, ngaySanXuat, ngayHetHan, nhaCungCap);
                } else if (loaiHang.equals("Electronics")) {
                    int thoiGianBaoHanh = Integer.parseInt(tgBaoHanhField.getText());
                    double congSuat = Double.parseDouble(congSuatField.getText());
                    newProduct = new ElectronicsProduct(maHang, tenHang, soLuong, donGia, loaiHang, thoiGianBaoHanh, congSuat);
                } else if (loaiHang.equals("Ceramics")) {
                    String nhaSanXuat = nhaSanXuatField.getText();
                    Date ngayNhapKho = Date.valueOf(ngayNhapKhoField.getText());
                    newProduct = new CeramicsProduct(maHang, tenHang, soLuong, donGia, loaiHang, nhaSanXuat, ngayNhapKho);
                } else {
                    // Handle invalid product type
                    JOptionPane.showMessageDialog(null, "Loại hàng không hợp lệ!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Call the method to add the product to the database
                boolean success = getProductListDAOMySQL.addProduct(newProduct);
                if (success) {
                    // Refresh the product list or update UI
                    JOptionPane.showMessageDialog(null, "Thêm sản phẩm thành công!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Optionally clear input fields
                    clearInputFields(maSanPhamField, tenSanPhamField, soLuongField, donGiaField, 
                                     ngaySanXuatField, ngayHetHanField, nhaCungCapField, 
                                     tgBaoHanhField, congSuatField, nhaSanXuatField, 
                                     ngayNhapKhoField);
                } else {
                    // Show error message
                    JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi thêm sản phẩm!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void clearInputFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }
    private void updateTableModel(List<GetProductListViewModel> updatedProducts, DefaultTableModel tableModel) {
        // Clear existing rows
        tableModel.setRowCount(0);
    
        // Add each product from the updated list to the table model
        for (GetProductListViewModel product : updatedProducts) {
            Object[] row = {
                product.maHang,
                product.tenHang,
                product.soLuong,
                product.donGia,
                product.loaiHang,
                product.tinhVAT
            };
            tableModel.addRow(row);
        }
}
}

