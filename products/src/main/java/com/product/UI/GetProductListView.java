package com.product.UI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;



public class GetProductListView {

    private List<GetProductListViewModel> products = null; 


    public void createAndshowGUI(List <GetProductListViewModel> products){
        this.products = products;

        JFrame frame = new JFrame("Product Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 450);

        JLabel tiJLabel = new JLabel("Danh sách sản phẩm", SwingConstants.CENTER);
        tiJLabel.setFont(new Font("Arial", Font.BOLD,24));
        tiJLabel.setBorder(BorderFactory.createEmptyBorder(10, 0,0,0 ));

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

        //setup layout for the frame
         frame.setLayout(new BorderLayout());
         frame.add(tiJLabel, BorderLayout.NORTH);
         frame.add(scrollPane, BorderLayout.CENTER);

         frame.setLocationRelativeTo(null);
         frame.setVisible(true);
    } 

    public void createAndshowGUI2(){
        

        JFrame frame = new JFrame("Product Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 450);

        JLabel tiJLabel = new JLabel("Danh sách sản phẩm", SwingConstants.CENTER);
        tiJLabel.setFont(new Font("Arial", Font.BOLD,24));
        tiJLabel.setBorder(BorderFactory.createEmptyBorder(10, 0,0,0 ));

        String [] columns = {
            "Mã Hàng", "Tên Hàng", "Số Lượng", "Đơn Giá", "Loại Hàng", "VAT"
        };

         // Create table model
         DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
         JTable table = new JTable(tableModel);

       

         //add product data to the model
        //  for(int i=0; i< products.size(); i++){
        //     GetProductListViewModel product = products.get(i);
        //     Object[] row ={
        //         product.maHang,
        //         product.tenHang,
        //         product.soLuong,
        //         product.donGia,
        //         product.loaiHang,
        //         product.tinhVAT
        //     };
        //     tableModel.addRow(row);
        //  }

         // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        //setup layout for the frame
         frame.setLayout(new BorderLayout());
         frame.add(tiJLabel, BorderLayout.NORTH);
         frame.add(scrollPane, BorderLayout.CENTER);

         frame.setLocationRelativeTo(null);
         frame.setVisible(true);
    } 

    public static void main(String[] args) {
        
        GetProductListView getProductListForm = new GetProductListView();
        getProductListForm.createAndshowGUI2();


    }

  
    
}
