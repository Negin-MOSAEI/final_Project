import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Product_list extends JFrame implements ActionListener,User{
    int page=1,productnum=0;
    JButton next=new JButton("next page");
    JButton previous=new JButton("previous page");
    JButton profile = new JButton("profile");
    String user_name;
    List<Product> productss;
    //method to fill the page with products retrieved from database
    public static List<Product> fillpage() {
        List<Product> products = new ArrayList<>();
        //creating a new arraylist to hold products abjects
        String query = "SELECT * FROM PRODUCTS";

        try (Connection connection = DatabaseConnection.getConnection();
             //establishing a database connection
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                //iterating throuth the result set and retrieving product details
                String name = resultSet.getString("TITLE");
                int price = resultSet.getInt("PRICE");
                String image_addres = resultSet.getString("IMAGE");
                products.add(new Product(name, price, image_addres));
                //creating a new product object and adding it to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    //constructor for product list class
    Product_list(String user_name){
        user_name=user_name;
        this.setTitle("Product_list");
        this.setSize(600, 700);
        this.setLayout(null);
        this.setVisible(true);
        this.setResizable(true);
        next.addActionListener(this);
        previous.addActionListener(this);
        profile.addActionListener(this);
        productss=fillpage();
        productnum=productss.size();
        main_panel();
    }
    //method to set up the main panel of the ui
    public void main_panel() {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.cyan);
        topPanel.setLayout(null);
        profile.setBounds(250,10,120,50);
        topPanel.add(profile);
        //creating a new JPanel for the product section of the ui
        JPanel prodPanel = new JPanel();
        //creating a new JPanel for the bottom section of the ui
        JPanel BottomPanel = new JPanel();
        BottomPanel.setLayout(null);
        BottomPanel.setBackground(Color.gray);
        topPanel.setBounds(0,0,600,100);
        prodPanel.setBounds(10,110,580,480);
        BottomPanel.setBounds(0,600,600,100);
        //setting the layout of the products panel to 3.3 grid
        prodPanel.setLayout(new GridLayout(3,3,30,30));

        JPanel products[] = new JPanel[10];
        //creating an array of JPanels to hold individual product panels.
        int[] quantities = new int[10];
        //creating an array to hold quantities of each product

        for (int i = 0; i < 9; i++) {
            int product_index = i;
            //storing the current product index
            products[i] = new JPanel();
            //creating a new JPanel for the current product
            products[i].setLayout(null);
            products[i].setBackground(Color.pink);
            String name = productss.get(i+(page-1)*9).getName();
            //retreving the name of current product
            int price = productss.get(i+(page-1)*9).getprice();
            //retreving the price of current product
            JLabel name_label = new JLabel(name);
            JLabel price_label = new JLabel(Integer.toString(price));
            JButton add_button = new JButton("+");
            //creating an add button
            JButton subtract_button = new JButton("-");
            //creating an subtract button
            JLabel quantity_label = new JLabel("Quantity: 0");
            //creating a lable to display the quantity of the priduct
//setting the bounds
            name_label.setBounds(50, 20, 70, 15);
            price_label.setBounds(50, 40, 70, 15);
            add_button.setBounds(50, 60, 45, 20);
            subtract_button.setBounds(100, 60, 45, 20);
            quantity_label.setBounds(50, 80, 100, 20);

            add_button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    quantities[product_index]++;
                    quantity_label.setText("Quantity: " + quantities[product_index]);
                    //updating the quantity lable
                }
            });

            subtract_button.addActionListener(new ActionListener() {
                //defining action to be taken on button click
                public void actionPerformed(ActionEvent e) {
                    if (quantities[product_index] > 0) {
                        quantities[product_index]--;
                    }
                    quantity_label.setText("Quantity: " + quantities[product_index]);
                    //updating lable
                }
            });

            products[i].add(name_label);
            products[i].add(price_label);
            products[i].add(add_button);
            products[i].add(subtract_button);
            products[i].add(quantity_label);
            prodPanel.add(products[i]);
            if ((i+(page-1)*9)== productss.size() - 1)
                break;
        }

        if((productnum+8)/9>page){
            next.setBounds(450,5,120,50);
            BottomPanel.add(next);
        }
        JLabel page_label=new JLabel();
        page_label.setText(String.valueOf(page));
        page_label.setBounds(300,5,50,50);
        BottomPanel.add(page_label);
        if(1<page){
            previous.setBounds(20,5,120,50);
            BottomPanel.add(previous);
        }

        this.add(topPanel);
        this.add(prodPanel);
        this.add(BottomPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==previous){
            page--;
            this.getContentPane().removeAll();
            //removing all components from container
            main_panel();
            this.revalidate();
            this.repaint();
        }else if(e.getSource()==next){
            page++;
            this.getContentPane().removeAll();
            main_panel();
            this.revalidate();
            this.repaint();
        } else if (e.getSource()==profile) {
            this.dispose();
            user_profile user=new user_profile(user_name);
        }
    }
}
class Product{
    //devlaring variables
    private String name;
    private int price;
    private String image_addres;
    //constructor
    public Product( String name, int price, String image_addres) {
        this.name = name;
        this.price = price;
        this.image_addres = image_addres;
    }
    public String getName(){
        return name;
    }
    public int getprice(){
        return price;
    }
}