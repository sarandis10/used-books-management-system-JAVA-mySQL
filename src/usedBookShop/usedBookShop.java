package usedBookShop;

import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;

import com.mysql.cj.MysqlConnection;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.Console;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class usedBookShop {

	private JFrame frame;
	private JTextField textFieldBookName;
	private JTextField textFieldEdition;
	private JTextField textFieldPrice;
	private JTable table;
	private JTextField textBookId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					usedBookShop window = new usedBookShop();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	DatabaseConnection myConnection = new DatabaseConnection();
	
Connection connection=myConnection.getConnection();
PreparedStatement prepState = myConnection.getPrepState();
ResultSet result;

	public usedBookShop() {
		initialize();
		myConnection.Connect();
		table_load();

	}


		
		public void table_load() {
			try {
				prepState=myConnection.getConnection().prepareStatement("SELECT * FROM books.usedbooks;");
				result=prepState.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(result));
			}catch (SQLException ex) {
				System.out.println(ex);
			}
		}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 759, 535);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Book Shop");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(305, 11, 101, 22);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 44, 369, 259);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Book Name");
		lblNewLabel_1.setBounds(10, 66, 68, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Edition");
		lblNewLabel_1_1.setBounds(10, 113, 68, 14);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Price");
		lblNewLabel_1_1_1.setBounds(10, 169, 68, 14);
		panel.add(lblNewLabel_1_1_1);
		
		textFieldBookName = new JTextField();
		textFieldBookName.setBounds(112, 66, 216, 20);
		panel.add(textFieldBookName);
		textFieldBookName.setColumns(10);
		
		textFieldEdition = new JTextField();
		textFieldEdition.setColumns(10);
		textFieldEdition.setBounds(112, 113, 216, 20);
		panel.add(textFieldEdition);
		
		textFieldPrice = new JTextField();
		textFieldPrice.setColumns(10);
		textFieldPrice.setBounds(112, 169, 216, 20);
		panel.add(textFieldPrice);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bookName=textFieldBookName.getText();
				String edition=textFieldEdition.getText();
				String price=textFieldPrice.getText();
				
				try {
					prepState=myConnection.getConnection().prepareStatement("insert into usedbooks(name,edition,price)values(?,?,?)");
					prepState.setString(1, bookName);
					prepState.setString(2, edition);
					prepState.setString(3, price);
					prepState.executeUpdate();  //to add it in the database!!! 
					JOptionPane.showMessageDialog(null, "Record Added!");
					//refresh the screen
					table_load();
					textFieldBookName.setText("");
					textFieldEdition.setText("");
					textFieldPrice.setText("");
					textFieldBookName.requestFocus();
					
					
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				
				
				
				
			}
		});
		btnSave.setBounds(10, 314, 101, 68);
		frame.getContentPane().add(btnSave);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(148, 314, 101, 68);
		frame.getContentPane().add(btnExit);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				textFieldBookName.setText("");
				textFieldEdition.setText("");
				textFieldPrice.setText("");
			}
		});
		btnClear.setBounds(278, 314, 101, 68);
		frame.getContentPane().add(btnClear);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(418, 43, 300, 260);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(32, 393, 342, 81);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		//search button
		textBookId = new JTextField();
		textBookId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					String id=textBookId.getText();
						prepState=myConnection.getConnection().prepareStatement("select name,edition,price from books.usedbooks where id=?");
						prepState.setString(1, id);
						ResultSet resultSet = prepState.executeQuery();
						
					if (resultSet.next()==true) {
						String name= resultSet.getString(1);
						String edition= resultSet.getString(2);
						String price= resultSet.getString(3);
						
						
						textFieldBookName.setText(name);
						textFieldEdition.setText(edition);
						textFieldPrice.setText(price);
						
						System.out.println(name);
						
					}else {
						textFieldBookName.setText("");
						textFieldEdition.setText("");
						textFieldPrice.setText("");
					}
					
				} catch (Exception ex) {
					System.out.println(ex);
				}
				
				
			}
		});
		textBookId.setColumns(10);
		textBookId.setBounds(10, 31, 216, 20);
		panel_1.add(textBookId);
		
		JLabel lblNewLabel_1_2 = new JLabel("Book ID");
		lblNewLabel_1_2.setBounds(90, 11, 68, 14);
		panel_1.add(lblNewLabel_1_2);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bookName=textFieldBookName.getText();
				String edition=textFieldEdition.getText();
				String price=textFieldPrice.getText();
				String idd= textBookId.getText();
				
				try {
					prepState=myConnection.getConnection().prepareStatement("update books.usedbooks set name=?,edition=?, price=? where id=?");
					prepState.setString(1, bookName);
					prepState.setString(2, edition);
					prepState.setString(3, price);
					prepState.setString(4, idd);
					prepState.executeUpdate();  //to add it in the database!!! 
					JOptionPane.showMessageDialog(null, "Record Updated");
					//refresh the screen
					table_load();
					textFieldBookName.setText("");
					textFieldEdition.setText("");
					textFieldPrice.setText("");
					textFieldBookName.requestFocus();
					
					
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				
				
				
			}
		});
		btnUpdate.setBounds(461, 393, 101, 68);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				

				String idd= textBookId.getText();
				
				try {
					prepState=myConnection.getConnection().prepareStatement("delete from books.usedbooks where id=?");
					prepState.setString(1, idd);
					prepState.executeUpdate();  //to add it in the database!!! 
					JOptionPane.showMessageDialog(null, "Record Deleted!");
					//refresh the screen
					table_load();
					textFieldBookName.setText("");
					textFieldEdition.setText("");
					textFieldPrice.setText("");
					textFieldBookName.requestFocus();
					
					
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
			}
		});
		btnDelete.setBounds(591, 393, 101, 68);
		frame.getContentPane().add(btnDelete);
	}
}
