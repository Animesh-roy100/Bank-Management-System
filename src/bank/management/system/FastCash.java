package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;

public class FastCash extends JFrame implements ActionListener {
    
    JButton cash1, cash2, cash3, cash4, cash5, cash6, back;
    String pinnumber;
    
    FastCash(String pinnumber) {
        
        this.pinnumber = pinnumber;
        
        setLayout(null);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 900, 900);
        add(image);
        
        JLabel text = new JLabel("SELECT WITHDRAWL AMOUNT");
        text.setBounds(217, 300, 700, 35);
        text.setForeground(Color.WHITE);
        text.setFont(new Font("System", Font.BOLD, 16));
        image.add(text);
        
        cash1 = new JButton("Rs. 100");
        cash1.setBounds(170, 415, 150, 30);
        cash1.addActionListener(this);
        image.add(cash1);
        
        cash2 = new JButton("Rs. 500");
        cash2.setBounds(355, 415, 150, 30);
        cash2.addActionListener(this);
        image.add(cash2);
        
        cash3 = new JButton("Rs. 1000");
        cash3.setBounds(170, 450, 150, 30);
        cash3.addActionListener(this);
        image.add(cash3);
        
        cash4 = new JButton("Rs. 2000");
        cash4.setBounds(355, 450, 150, 30);
        cash4.addActionListener(this);
        image.add(cash4);
        
        cash5 = new JButton("Rs. 5000");
        cash5.setBounds(170, 485, 150, 30);
        cash5.addActionListener(this);
        image.add(cash5);
        
        cash6 = new JButton("Rs. 10000");
        cash6.setBounds(355, 485, 150, 30);
        cash6.addActionListener(this);
        image.add(cash6);
        
        back = new JButton("BACK");
        back.setBounds(355, 520, 150, 30);
        back.addActionListener(this);
        image.add(back);
        
        setSize(900, 900);
        setLocation(300, 0);
        setUndecorated(true);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == back) {
            setVisible(false);
            new Transactions(pinnumber).setVisible(true);
        } else {
            String amount = ((JButton) ae.getSource()).getText().substring(4); //Rs. 500
            Conn c = new Conn();
            
            try {
                ResultSet rs = c.s.executeQuery("Select * from bank where pin = '"+pinnumber+"'");
                int balance = 0;
                while(rs.next()) {
                    if (rs.getString("type").equals("Deposit")) {
                        balance += Integer.parseInt(rs.getString("amount"));
                    } else {
                        balance -= Integer.parseInt(rs.getString("amount"));
                    }
                }
                
                if(ae.getSource() != back && balance < Integer.parseInt(amount)) {
                    JOptionPane.showMessageDialog(null, "Insufficient Balance");
                    return;
                }
                
                Date date = new Date();
                String query = "insert into bank values('"+pinnumber+"', '"+date+"', 'Withdrawl', '"+amount+"')";
                c.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Rs. "+ amount + " Debited Sucessfully");
                
                setVisible(false);
                new Transactions(pinnumber).setVisible(true);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    
    public static void main(String args[]) {
        new FastCash("");
    }
}

