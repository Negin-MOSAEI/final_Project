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
    JButton next_ad=new JButton("next page");
    JButton next_cl=new JButton("next page");
    JButton previous_ad=new JButton("previous page");
    JButton previous_cl=new JButton("previous page");
    JButton profile = new JButton("profile");
    String name;
    Boolean admin;
    List<Product> productss;
    public static List<Product> fillpage() {//It reads the information from the database and puts it in the list of goods
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM PRODUCTS";//We specify which table to read the information from

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {//We extract the information using specific keys
                String name = resultSet.getString("TITLE");
                int price = resultSet.getInt("PRICE");
                String image_addres = resultSet.getString("IMAGE");
                products.add(new Product(name, price, image_addres));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    Product_list(String user_name,Boolean is_admin){//We create the constructor of the class and keep the necessary default information and create the primary objects
        name=user_name;
        admin=is_admin;
        this.setTitle("Product_list");
        this.setSize(600, 700);
        this.setLayout(null);
        this.setVisible(true);
        this.setResizable(true);
        next_ad.addActionListener(this);
        next_cl.addActionListener(this);
        previous_ad.addActionListener(this);
        previous_cl.addActionListener(this);
        profile.addActionListener(this);
        productss=fillpage();
        productnum=productss.size();
        if(admin)//We specify which of the fields to go to for each user regarding being an admin or not
            admin_panel();
        else
            client_panel();
    }
    public void delet(int index,String title){//We delete a specific item from its title from the database and list of items
        String deleteQuery = "DELETE FROM PRODUCTS WHERE TITLE = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, title);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        productss.remove((page-1)*9+index);
        productnum--;
        this.getContentPane().removeAll();
        admin_panel();
        this.revalidate();
        this.repaint();
    }
    public void client_panel() {//We build the customer panel and add different buttons for user access
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.cyan);
        topPanel.setLayout(null);
        profile.setBounds(250, 10, 120, 50);
        topPanel.add(profile);
        JPanel prodPanel = new JPanel();
        JPanel BottomPanel = new JPanel();
        BottomPanel.setLayout(null);
        BottomPanel.setBackground(Color.gray);
        topPanel.setBounds(0, 0, 600, 100);
        prodPanel.setBounds(10, 110, 580, 480);
        BottomPanel.setBounds(0, 600, 600, 100);
        prodPanel.setLayout(new GridLayout(3, 3, 30, 30));
        JPanel products[] = new JPanel[10];
        int[] quantities = new int[10];

        for (int i = 0; i < 9; i++) {//Made product panels that include 9 product panels
            int product_index = i;
            products[i] = new JPanel();
            products[i].setLayout(null);
            products[i].setBackground(Color.pink);
            String name = productss.get(i + (page - 1) * 9).getName();
            int price = productss.get(i + (page - 1) * 9).getprice();
            JLabel name_label = new JLabel("name : "+name);
            JLabel price_label = new JLabel("price : "+Integer.toString(price));
            JButton add_button = new JButton("+");
            JButton subtract_button = new JButton("-");
            JLabel quantity_label = new JLabel("Quantity: 0");//We make the button to add and subtract and label the number of products

            name_label.setBounds(40, 20, 100, 15);
            price_label.setBounds(40, 45, 100, 15);
            add_button.setBounds(43, 70, 45, 20);
            subtract_button.setBounds(93, 70, 45, 20);
            quantity_label.setBounds(50, 95, 100, 20);

            add_button.addActionListener(new ActionListener() {//Action listener defines each button for a specific task
                public void actionPerformed(ActionEvent e) {
                    quantities[product_index]++;
                    quantity_label.setText("Quantity: " + quantities[product_index]);
                }
            });

            subtract_button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (quantities[product_index] > 0) {
                        quantities[product_index]--;
                    }
                    quantity_label.setText("Quantity: " + quantities[product_index]);
                }
            });

            products[i].add(name_label);
            products[i].add(price_label);
            products[i].add(add_button);
            products[i].add(subtract_button);
            products[i].add(quantity_label);
            prodPanel.add(products[i]);
            if ((i + (page - 1) * 9) == productss.size() - 1)
                break;
        }
        if((productnum+8)/9>page){//We add the next and previous buttons according to special conditions
            next_cl.setBounds(450,5,120,50);
            BottomPanel.add(next_cl);
        }
        JLabel page_label=new JLabel();
        page_label.setText(String.valueOf(page));
        page_label.setBounds(300,5,50,50);
        BottomPanel.add(page_label);
        if(1<page){
            previous_cl.setBounds(20,5,120,50);
            BottomPanel.add(previous_cl);
        }

        this.add(topPanel);
        this.add(prodPanel);
        this.add(BottomPanel);
    }
    public void admin_panel(){//We create the admin panel and add the necessary things for the admin
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.cyan);
        topPanel.setLayout(null);
        profile.setBounds(250, 10, 120, 50);
        topPanel.add(profile);
        JPanel prodPanel = new JPanel();
        JPanel BottomPanel = new JPanel();
        BottomPanel.setLayout(null);
        BottomPanel.setBackground(Color.gray);
        topPanel.setBounds(0, 0, 600, 100);
        prodPanel.setBounds(10, 110, 580, 480);
        BottomPanel.setBounds(0, 600, 600, 100);
        prodPanel.setLayout(new GridLayout(3, 3, 30, 30));

        JPanel products[] = new JPanel[10];
        for (int i = 0; i < 9; i++) {////Made product panels that include 9 product panels
            int product_index = i;
            products[i] = new JPanel();
            products[i].setLayout(null);
            products[i].setBackground(Color.pink);
            String name = productss.get(i + (page - 1) * 9).getName();
            int price = productss.get(i + (page - 1) * 9).getprice();
            JLabel name_label = new JLabel("name : "+name);//We create the name of the product and its price along with the delete button
            JLabel price_label = new JLabel("price : "+Integer.toString(price));
            JButton delete_Button = new JButton("delete Item");

            name_label.setBounds(35, 20, 100, 15);
            price_label.setBounds(35, 40, 100, 15);
            delete_Button.setBounds(35, 70, 100, 30);
            products[i].add(name_label);
            products[i].add(price_label);
            products[i].add(delete_Button);
            prodPanel.add(products[i]);
            delete_Button.addActionListener(new ActionListener() {//We create action listener of the delete button that calls the desired function
                public void actionPerformed(ActionEvent e) {
                    delet(product_index,name);
                }
            });
            if ((i + (page - 1) * 9) == productss.size() - 1)
                break;
        }
        if((productnum+8)/9>page){
            next_ad.setBounds(450,5,120,50);
            BottomPanel.add(next_ad);
        }
        JLabel page_label=new JLabel();
        page_label.setText(String.valueOf(page));
        page_label.setBounds(300,5,50,50);
        BottomPanel.add(page_label);
        if(1<page){
            previous_ad.setBounds(20,5,120,50);
            BottomPanel.add(previous_ad);
        }

        this.add(topPanel);
        this.add(prodPanel);
        this.add(BottomPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==previous_ad){//Action listener, we make the previous and next buttons in relation to the admin panel or the client and add the necessary commands.
            page--;
            this.getContentPane().removeAll();
            admin_panel();
            this.revalidate();
            this.repaint();
        }else if(e.getSource()==next_ad){
            page++;
            this.getContentPane().removeAll();
            admin_panel();
            this.revalidate();
            this.repaint();
        }else if(e.getSource()==previous_cl){
            page--;
            this.getContentPane().removeAll();
            client_panel();
            this.revalidate();
            this.repaint();
        }else if(e.getSource()==next_cl){
            page++;
            this.getContentPane().removeAll();
            client_panel();
            this.revalidate();
            this.repaint();
        } else if (e.getSource()==profile) {
            this.dispose();
            user_profile user=new user_profile(name);
        }
    }
}
class Product{//We create a class to store a specific type of object
    private String name;
    private int price;
    private String image_addres;
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
