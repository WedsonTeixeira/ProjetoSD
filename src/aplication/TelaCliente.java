package aplication;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class TelaCliente extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCliente frame = new TelaCliente();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaCliente() {
		setForeground(Color.WHITE);
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 625, 433);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel lblDigiteONome = new JLabel("Digite o nome do arquivo");
		lblDigiteONome.setBounds(10, 13, 189, 19);
		contentPane.add(lblDigiteONome);
		
		textField = new JTextField();
		textField.setBounds(10, 42, 189, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(10, 71, 80, 30);
		contentPane.add(btnBuscar);
		
		JButton btnNewButton = new JButton("Baixar");
		btnNewButton.setBounds(119, 71, 80, 30);
		btnNewButton.setEnabled(false);
		contentPane.add(btnNewButton);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nome da Maquina", "IP", "Porta",
			}
		));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(252, 38, 315, 313);
		scrollPane.setViewportView(table);
		contentPane.add(scrollPane);		
		
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(!textField.getText().isEmpty()) {
					btnBuscar.setEnabled(false);
					btnNewButton.setEnabled(false);
					ClienteSend cliente = new ClienteSend(textField.getText(), table, btnNewButton, btnBuscar);
					cliente.start();	
				}
				else{
					JOptionPane.showMessageDialog(null,"Digite o nome de um arquivo!","Alerta",JOptionPane.WARNING_MESSAGE);
				}
				
			}
		});	
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() >= 0) {
					String ip = table.getValueAt(table.getSelectedRow(), 1).toString();
					int porta= Integer.parseInt(table.getValueAt(table.getSelectedRow(), 2).toString());  
					String nomeArquivo = textField.getText();
					
					btnBuscar.setEnabled(false);
					btnNewButton.setEnabled(false);
					
					ClienteReceived clienteReceived = new ClienteReceived(ip, porta, nomeArquivo, btnNewButton, btnBuscar);
					clienteReceived.start();
				}
				else{
					JOptionPane.showMessageDialog(null,"Selecione um servidor de arquivo da tabela!","Alerta",JOptionPane.WARNING_MESSAGE);
				}
				
			}
		});	
	}
}
