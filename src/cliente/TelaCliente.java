package cliente;

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
import javax.swing.JProgressBar;

public class TelaCliente extends JFrame {

	private JPanel contentPane;
	private JTextField jTextField;
	private JTable jTable;

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

	public TelaCliente() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 625, 433);
		setContentPane(contentPane);
		
		JLabel jLabel1 = new JLabel("Digite o nome do arquivo");
		jLabel1.setBounds(10, 13, 180, 19);
		contentPane.add(jLabel1);
		
		jTextField = new JTextField();
		jTextField.setBounds(10, 42, 180, 19);
		contentPane.add(jTextField);
		
		JLabel JLabel2 = new JLabel("Status do Download:");
		JLabel2.setBounds(10, 156, 180, 13);
		contentPane.add(JLabel2);
		
		JProgressBar jProgressBar = new JProgressBar();
		jProgressBar.setBounds(10, 179, 180, 30);
		contentPane.add(jProgressBar);
		
		JButton jButtonBuscar = new JButton("Buscar");
		jButtonBuscar.setBounds(10, 71, 80, 30);
		contentPane.add(jButtonBuscar);
		
		JButton jButtonBaixar = new JButton("Baixar");
		jButtonBaixar.setBounds(110, 71, 80, 30);
		jButtonBaixar.setEnabled(false);
		contentPane.add(jButtonBaixar);
		
		jTable = new JTable();
		jTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nome da Maquina", "IP", "Porta", "Tamanho Arquivo",
			}
		));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(209, 38, 392, 313);
		scrollPane.setViewportView(jTable);
		contentPane.add(scrollPane);	
		
		
		jButtonBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!jTextField.getText().isEmpty()) {
					
					jButtonBuscar.setEnabled(false);
					jButtonBaixar.setEnabled(false);
					
					ThreadBuscar threadBuscar = new ThreadBuscar(jTextField, jTable, jButtonBuscar, jButtonBaixar);
					threadBuscar.start();	
				}
				else{
					JOptionPane.showMessageDialog(null,"Digite o nome de um arquivo!","Alerta",JOptionPane.WARNING_MESSAGE);
				}
			}
		});	
		
		jButtonBaixar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(jTable.getSelectedRow() >= 0) {
					
					jButtonBuscar.setEnabled(false);
					jButtonBaixar.setEnabled(false);
					
					ThreadBaixar threadBaixar = new ThreadBaixar(jTextField, jTable, jButtonBuscar, jButtonBaixar, jProgressBar);
					threadBaixar.start();
				}
				else{
					JOptionPane.showMessageDialog(null,"Selecione um servidor de arquivo da tabela!","Alerta",JOptionPane.WARNING_MESSAGE);
				}
			}
		});	
	}
}