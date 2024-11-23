package product.UI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import product.UI.GetProductListViewModel;
import product.Database.GetProductListDAOMySQL;
import product.Entity.CeramicsProduct;
import product.Entity.ElectronicsProduct;
import product.Entity.FoodProduct;
import product.Entity.Product;
import product.UseCase.AddProduct.AddProductUseCase;
import product.UseCase.CalculateTotal.CalculateTotalStockController;
import product.UseCase.CalculateTotal.CalculateTotalStockUseCase;
import product.UseCase.DeleteProduct.DeleteProductController;
import product.UseCase.DeleteProduct.DeleteProductUseCase;
import product.UseCase.SeachProduct.SearchProductController;
import product.UseCase.SeachProduct.SearchProductUseCase;
import product.UseCase.UpdateProduct.UpdateProductController;
import product.UseCase.UpdateProduct.UpdateProductUseCase;



public class GetProductListView {

    private List<GetProductListViewModel> products = null; 
    private GetProductListDAOMySQL getProductListDAOMySQL;
    private JTable table; 
    
    private JTextField tgBaoHanhField;
    private JTextField congSuatField;
    private JTextField ngaySanXuatField;
    private JTextField ngayHetHanField;
    private JTextField nhaCungCapField;
    private JTextField nhaSanXuatField;
    private JTextField ngayNhapKhoField;

    
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
         this.table = new JTable(tableModel);

       

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
        frame.setLayout(new BorderLayout());
        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

         addButton.addActionListener(e -> showAddProductDialog(frame, tableModel));
         deleteButton.addActionListener(e -> showDeleteProductDialog(frame, tableModel));
         editButton.addActionListener(e -> showFindProductDialog(frame));
         totalButton.addActionListener(e -> calculateTotalStock());

         searchButton.addActionListener(e -> {
            SearchProductController searchProductController = new SearchProductController(new SearchProductUseCase(getProductListDAOMySQL));
            List<Product> expiringProducts = searchProductController.searchProductsExpiringSoon();
        
            // Hiển thị kết quả tìm kiếm
            if (expiringProducts.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Không có sản phẩm nào hết hạn trong 1 tuần.", "Kết quả tìm kiếm", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder result = new StringBuilder("Sản phẩm hết hạn trong 1 tuần:\n");
                for (Product product : expiringProducts) {
                    if (product instanceof FoodProduct) {
                        FoodProduct foodProduct = (FoodProduct) product;
                        result.append(foodProduct.getTenHang())
                        .append("\n");
                    }
                }
                JOptionPane.showMessageDialog(frame, result.toString(), "Kết quả tìm kiếm", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        }

        
        
        private void showAddProductDialog(JFrame parentFrame, DefaultTableModel tableModel) {
            JDialog dialog = new JDialog(parentFrame, "Thêm Sản Phẩm", true);
            dialog.setSize(400, 400);
            dialog.setLayout(new BorderLayout());
        
            JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
            tgBaoHanhField = new JTextField();
            congSuatField = new JTextField();
            ngaySanXuatField = new JTextField();
            ngayHetHanField = new JTextField();
            nhaCungCapField = new JTextField();
            nhaSanXuatField = new JTextField();
            ngayNhapKhoField = new JTextField();
            JTextField maSanPhamField = new JTextField();
            JTextField tenSanPhamField = new JTextField();
            JTextField soLuongField = new JTextField();
            JTextField donGiaField = new JTextField();
           
            JComboBox<String> loaiHangComboBox = new JComboBox<>();
            List<String> productTypes = getProductListDAOMySQL.getAllProductTypes();
            for (String type : productTypes) {
            loaiHangComboBox.addItem(type);
            }
        
            inputPanel.add(new JLabel("Mã sản phẩm:"));
            inputPanel.add(maSanPhamField);
            inputPanel.add(new JLabel("Tên sản phẩm:"));
            inputPanel.add(tenSanPhamField);
            inputPanel.add(new JLabel("Số lượng:"));
            inputPanel.add(soLuongField);
            inputPanel.add(new JLabel("Đơn giá:"));
            inputPanel.add(donGiaField);
            inputPanel.add(new JLabel("Loại hàng:"));
            inputPanel.add(loaiHangComboBox);
        
            // Panel for product-specific fields using CardLayout
            JPanel cardPanel = new JPanel(new CardLayout());
        
            // Electronics fields
            JPanel electronicsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
            electronicsPanel.add(new JLabel("Thời gian bảo hành:"));
            electronicsPanel.add(tgBaoHanhField);
            electronicsPanel.add(new JLabel("Công suất:"));
            electronicsPanel.add(congSuatField);
        
            // Food fields
            JPanel foodPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        
            foodPanel.add(new JLabel("Ngày sản xuất:"));
            foodPanel.add(ngaySanXuatField);
            foodPanel.add(new JLabel("Ngày hết hạn:"));
            foodPanel.add(ngayHetHanField);
            foodPanel.add(new JLabel("Nhà cung cấp:"));
            foodPanel.add(nhaCungCapField);
        
            // Ceramics fields
            JPanel ceramicsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
           
            ceramicsPanel.add(new JLabel("Nhà sản xuất:"));
            ceramicsPanel.add(nhaSanXuatField);
            ceramicsPanel.add(new JLabel("Ngày nhập kho:"));
            ceramicsPanel.add(ngayNhapKhoField);
        
            // Add panels to the card panel
            cardPanel.add(new JPanel(), "None"); 
            cardPanel.add(electronicsPanel, "Electronics");
            cardPanel.add(foodPanel, "Food");
            cardPanel.add(ceramicsPanel, "Ceramics");
        
             loaiHangComboBox.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String selectedType = (String) loaiHangComboBox.getSelectedItem();
            CardLayout cl = (CardLayout) (cardPanel.getLayout());
            cl.show(cardPanel, selectedType);
        }
    });
        
            JPanel buttonPanel = new JPanel();
            JButton saveButton = new JButton("Lưu");
            buttonPanel.add(saveButton);
        
            dialog.add(inputPanel, BorderLayout.NORTH);
            dialog.add(cardPanel, BorderLayout.CENTER);
            dialog.add(buttonPanel, BorderLayout.SOUTH);
        
            saveButton.addActionListener(e -> {
                try {
                    int maHang = Integer.parseInt(maSanPhamField.getText());
                    String tenHang = tenSanPhamField.getText();
                    int soLuong = Integer.parseInt(soLuongField.getText());
                    double donGia = Double.parseDouble(donGiaField.getText());
                    String loaiHang = (String) loaiHangComboBox.getSelectedItem();
                    Product product = createProduct(maHang, tenHang, soLuong, donGia, loaiHang);
                    AddProductUseCase addProductUseCase = new AddProductUseCase(getProductListDAOMySQL);
        
                    if (soLuong < 0) {
                        JOptionPane.showMessageDialog(dialog, "Số lượng không thể âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
        
                    boolean success = addProductUseCase.execute(product);
                    if (success) {
                        double vat = product.tinhVAT();
                        tableModel.addRow(new Object[]{maHang, tenHang, soLuong, donGia, loaiHang, vat});
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Thêm sản phẩm không thành công!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Vui lòng nhập đúng định dạng số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            });
        
            dialog.setVisible(true);
        }

        
        private void updateTableModel(List<GetProductListViewModel> products, DefaultTableModel tableModel) {
            tableModel.setRowCount(0); // Xóa tất cả các hàng hiện tại
            for (GetProductListViewModel product : products) {
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

    
    
   
    private void showDeleteProductDialog(JFrame parentFrame, DefaultTableModel tableModel) {
        JDialog dialog = new JDialog(parentFrame, "Xóa Sản Phẩm", true);
        dialog.setSize(300, 150);
        dialog.setLayout(new GridLayout(3, 1));
    
        JTextField maHangField = new JTextField();
        dialog.add(new JLabel("Nhập Mã Hàng để Xóa:"));
        dialog.add(maHangField);
    
        JButton deleteButton = new JButton("Xóa");
        dialog.add(deleteButton);
    
        deleteButton.addActionListener(e -> {
            try {
                int productId = Integer.parseInt(maHangField.getText().trim());
                DeleteProductController deleteProductController = new DeleteProductController(new DeleteProductUseCase(getProductListDAOMySQL));
                boolean success = deleteProductController.deleteProduct(productId); // Đảm bảo phương thức này trả về boolean
                if (success) {
                    // Remove the row from the table model
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        if (tableModel.getValueAt(i, 0).equals(productId)) {
                            tableModel.removeRow(i);
                            break;
                        }
                    }
                    JOptionPane.showMessageDialog(parentFrame, "Xóa sản phẩm thành công!");
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Không thể xóa sản phẩm. Vui lòng kiểm tra mã hàng.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng nhập mã hàng hợp lệ.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Đã xảy ra lỗi: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }

    private void showFindProductDialog(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "Tìm Sản Phẩm", true);
        dialog.setSize(300, 150);
        dialog.setLayout(new GridLayout(3, 1));

        JTextField maHangField = new JTextField();
        dialog.add(new JLabel("Nhập Mã Hàng:"));
        dialog.add(maHangField);

        JButton findButton = new JButton("Tìm");
        dialog.add(findButton);

        findButton.addActionListener(e -> {
            try {
                int maHang = Integer.parseInt(maHangField.getText());
                Product product = getProductListDAOMySQL.getProductById(maHang);
                if (product != null) {
                    dialog.dispose();
                    showEditProductDialog(parentFrame, product);
                } else {
                    JOptionPane.showMessageDialog(dialog, "Không tìm thấy sản phẩm với mã hàng: " + maHang);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng nhập mã hàng hợp lệ.");
            }
        });

        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }

    private void showEditProductDialog(JFrame parentFrame, Product product) {
        JDialog dialog = new JDialog(parentFrame, "Sửa Sản Phẩm", true);
        dialog.setSize(400, 500);
        dialog.setLayout(new BorderLayout());

        JPanel basicInfoPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        JTextField tenSanPhamField = new JTextField(product.getTenHang());
        JTextField soLuongField = new JTextField(String.valueOf(product.getSoLuong()));
        JTextField donGiaField = new JTextField(String.valueOf(product.getDonGia()));
        
        basicInfoPanel.add(new JLabel("Tên sản phẩm:"));
        basicInfoPanel.add(tenSanPhamField);
        basicInfoPanel.add(new JLabel("Số lượng:"));
        basicInfoPanel.add(soLuongField);
        basicInfoPanel.add(new JLabel("Đơn giá:"));
        basicInfoPanel.add(donGiaField);

        JPanel specificInfoPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        
        if (product instanceof ElectronicsProduct) {
            ElectronicsProduct ep = (ElectronicsProduct) product;
            tgBaoHanhField = new JTextField(String.valueOf(ep.getThoiGianBaoHanh()));
            congSuatField = new JTextField(String.valueOf(ep.getCongSuat()));
            
            specificInfoPanel.add(new JLabel("Thời gian bảo hành:"));
            specificInfoPanel.add(tgBaoHanhField);
            specificInfoPanel.add(new JLabel("Công suất:"));
            specificInfoPanel.add(congSuatField);
        }
        else if (product instanceof FoodProduct) {
            FoodProduct fp = (FoodProduct) product;
            ngaySanXuatField = new JTextField(fp.getNgaySanXuat().toString());
            ngayHetHanField = new JTextField(fp.getNgayHetHan().toString());
            nhaCungCapField = new JTextField(fp.getNhaCungCap());
            
            specificInfoPanel.add(new JLabel("Ngày sản xuất:"));
            specificInfoPanel.add(ngaySanXuatField);
            specificInfoPanel.add(new JLabel("Ngày hết hạn:"));
            specificInfoPanel.add(ngayHetHanField);
            specificInfoPanel.add(new JLabel("Nhà cung cấp:"));
            specificInfoPanel.add(nhaCungCapField);
        }
        else if (product instanceof CeramicsProduct) {
            CeramicsProduct cp = (CeramicsProduct) product;
            nhaSanXuatField = new JTextField(cp.getNhaSanXuat());
            ngayNhapKhoField = new JTextField(cp.getNgayNhapKho().toString());
            
            specificInfoPanel.add(new JLabel("Nhà sản xuất:"));
            specificInfoPanel.add(nhaSanXuatField);
            specificInfoPanel.add(new JLabel("Ngày nhập kho:"));
            specificInfoPanel.add(ngayNhapKhoField);
        }

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(basicInfoPanel, BorderLayout.NORTH);
        mainPanel.add(specificInfoPanel, BorderLayout.CENTER);
        
        JButton saveButton = new JButton("Lưu");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);

        dialog.add(mainPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(e -> {
            try {
                String tenHang = tenSanPhamField.getText();
                int soLuong = Integer.parseInt(soLuongField.getText());
                double donGia = Double.parseDouble(donGiaField.getText());
                
                Product updatedProduct = createProduct(
                    product.getMaHang(), 
                    tenHang,
                    soLuong,
                    donGia,
                    product.getLoaiHang()
                );

                UpdateProductController controller = new UpdateProductController(
                    new UpdateProductUseCase(getProductListDAOMySQL)
                );

                if (controller.updateProduct(updatedProduct)) {
                    List<Product> updatedProducts = getProductListDAOMySQL.getAllProductList();
                    List<GetProductListViewModel> viewModels = convertToViewModel(updatedProducts);
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.setRowCount(0);
                    for (GetProductListViewModel vm : viewModels) {
                        model.addRow(new Object[]{
                            vm.maHang,
                            vm.tenHang,
                            vm.soLuong,
                            vm.donGia,
                            vm.loaiHang,
                            vm.tinhVAT
                        });
                    }
                    dialog.dispose();
                    JOptionPane.showMessageDialog(parentFrame, "Cập nhật sản phẩm thành công!");
                } else {
                    JOptionPane.showMessageDialog(dialog, "Không thể cập nhật sản phẩm");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Lỗi: " + ex.getMessage());
            }
        });

        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }
    private List<GetProductListViewModel> convertToViewModel(List<Product> products) {
        List<GetProductListViewModel> viewModels = new ArrayList<>();
        for (Product product : products) {
            viewModels.add(new GetProductListViewModel(
                product.getMaHang(),
                product.getTenHang(),
                product.getSoLuong(),
                product.getDonGia(),
                product.getLoaiHang(),
                product.tinhVAT()
            ));
        }
        return viewModels;
    }
    
    private void calculateTotalStock() {
    CalculateTotalStockController controller = new CalculateTotalStockController(
            new CalculateTotalStockUseCase(getProductListDAOMySQL));
    Map<String, Integer> totalStock = controller.calculateTotalStock();

    StringBuilder result = new StringBuilder("Tổng số lượng tồn kho theo loại hàng:\n");
    for (Map.Entry<String, Integer> entry : totalStock.entrySet()) {
        result.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
    }

    JOptionPane.showMessageDialog(null, result.toString(), "Tổng Số Lượng Tồn Kho", JOptionPane.INFORMATION_MESSAGE);
}
private Product createProduct(int maHang, String tenHang, int soLuong, double donGia, String loaiHang) {
    switch (loaiHang) {
        case "Electronics":
            String tgBaoHanh = tgBaoHanhField.getText();
            double congSuat = Double.parseDouble(congSuatField.getText());
            return new ElectronicsProduct(maHang, tenHang, soLuong, donGia, loaiHang, 
                Integer.parseInt(tgBaoHanh), congSuat);
            
        case "Food":
            Date ngaySanXuat = Date.valueOf(ngaySanXuatField.getText());
            Date ngayHetHan = Date.valueOf(ngayHetHanField.getText());
            String nhaCungCap = nhaCungCapField.getText();
            return new FoodProduct(maHang, tenHang, soLuong, donGia, loaiHang, 
                ngayHetHan, ngaySanXuat, nhaCungCap);
            
        case "Ceramics":
            String nhaSanXuat = nhaSanXuatField.getText();
            Date ngayNhapKho = Date.valueOf(ngayNhapKhoField.getText());
            return new CeramicsProduct(maHang, tenHang, soLuong, donGia, loaiHang,
                nhaSanXuat, ngayNhapKho);
            
        default:
            throw new IllegalArgumentException("Invalid product type: " + loaiHang);
    }
}


}

