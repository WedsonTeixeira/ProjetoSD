package cliente;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
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
import javax.swing.SwingConstants;
import java.awt.Toolkit;

/*
 * Visualizacao da tela do cliente
 * */

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
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaCliente.class.getResource("/cliente/sistemasdistribuidos.png")));
		setResizable(false);
		setTitle("SISTEMAS DISTRIBUIDOS");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 708, 400);
		setContentPane(contentPane);
		
		JLabel jLabel1 = new JLabel("Digite o nome do arquivo");
		jLabel1.setBounds(10, 20, 180, 19);
		contentPane.add(jLabel1);
		
		jTextField = new JTextField();
		jTextField.setBounds(10, 42, 185, 25);
		contentPane.add(jTextField);
		
		JLabel JLabel2 = new JLabel("Status do Download:");
		JLabel2.setBounds(10, 156, 180, 13);
		contentPane.add(JLabel2);
		
		JLabel JLabel3 = new JLabel("");
		JLabel3.setVisible(false);
		JLabel3.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("vetor.gif")).getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT)));
		JLabel3.setBounds(86, 109, 25, 25);
		contentPane.add(JLabel3);
		
		JProgressBar jProgressBar = new JProgressBar();
		jProgressBar.setBounds(10, 179, 185, 25);
		contentPane.add(jProgressBar);
		
		JButton jButtonBuscar = new JButton("Buscar");
		jButtonBuscar.setBounds(10, 71, 90, 25);
		contentPane.add(jButtonBuscar);
		
		JButton jButtonBaixar = new JButton("Baixar");
		jButtonBaixar.setBounds(105, 71, 90, 25);
		jButtonBaixar.setEnabled(false);
		contentPane.add(jButtonBaixar);
		
		jTable = new JTable();
		jTable.getTableHeader().setReorderingAllowed(false);
		jTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nome da Maquina", "IP", "Porta", "Tamanho Arquivo",
			}
		));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(202, 42, 482, 307);
		scrollPane.setViewportView(jTable);
		contentPane.add(scrollPane);		
		
		JLabel lblDevelopedByWedson = new JLabel("Developed by Wedson & Daniel");
		lblDevelopedByWedson.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDevelopedByWedson.setBounds(463, 347, 221, 15);
		contentPane.add(lblDevelopedByWedson);
		
		jButtonBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!jTextField.getText().isEmpty()) {
					
					jTable.setModel(new DefaultTableModel(
							new Object[][] {
							},
							new String[] {
								"Nome da Maquina", "IP", "Porta", "Tamanho Arquivo",
							}
					));
					
					jButtonBuscar.setEnabled(false);
					jButtonBaixar.setEnabled(false);
					
					// Criando a thread para o botao de busca de arquivo
					ThreadBuscar threadBuscar = new ThreadBuscar(jTextField, JLabel3, jTable, jButtonBuscar, jButtonBaixar);
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
					
					// Criando thread para o botao de baixar um arquivo
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