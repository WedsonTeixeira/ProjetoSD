package aplication;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JList;

public class TelaCliente extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCliente frame = new TelaCliente();
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 709, 433);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(46, 73, 117, 25);
		contentPane.add(btnBuscar);
		
		textField = new JTextField();
		textField.setBounds(12, 42, 189, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblDigiteONome = new JLabel("Digite o nome do arquivo");
		lblDigiteONome.setBounds(18, 27, 189, 15);
		contentPane.add(lblDigiteONome);
		
		JList list = new JList();
		list.setBounds(624, 345, -323, -317);
		contentPane.add(list);
	}
}
