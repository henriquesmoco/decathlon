package com.github.henriquesmoco.decathlon;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class ConversaoDeDadosForm {

	private JFrame frame;
	private JTextField txtOrigem;
	private JTextField txtDestino;
	private JButton btnConverter;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConversaoDeDadosForm window = new ConversaoDeDadosForm();
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
	private ConversaoDeDadosForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 166);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblDecathlonConverter = new JLabel("Decathlon Converter - CSV para XML");
		lblDecathlonConverter.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_lblDecathlonConverter = new GridBagConstraints();
		gbc_lblDecathlonConverter.gridwidth = 4;
		gbc_lblDecathlonConverter.insets = new Insets(5, 0, 5, 0);
		gbc_lblDecathlonConverter.gridx = 0;
		gbc_lblDecathlonConverter.gridy = 0;
		frame.getContentPane().add(lblDecathlonConverter, gbc_lblDecathlonConverter);
		
		JLabel lblEntradacsv = new JLabel("Entrada:");
		GridBagConstraints gbc_lblEntradacsv = new GridBagConstraints();
		gbc_lblEntradacsv.anchor = GridBagConstraints.EAST;
		gbc_lblEntradacsv.insets = new Insets(0, 5, 5, 5);
		gbc_lblEntradacsv.gridx = 0;
		gbc_lblEntradacsv.gridy = 1;
		frame.getContentPane().add(lblEntradacsv, gbc_lblEntradacsv);
		
		txtOrigem = new JTextField();
		GridBagConstraints gbc_txtOrigem = new GridBagConstraints();
		gbc_txtOrigem.insets = new Insets(0, 0, 5, 5);
		gbc_txtOrigem.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtOrigem.gridx = 1;
		gbc_txtOrigem.gridy = 1;
		frame.getContentPane().add(txtOrigem, gbc_txtOrigem);
		txtOrigem.setColumns(10);
		
		JButton btnBrowseOrigem = new JButton("");
		btnBrowseOrigem.addActionListener(new AbrirArquivoAction());
		btnBrowseOrigem.setIcon(new ImageIcon(ConversaoDeDadosForm.class.getResource("/com/sun/java/swing/plaf/windows/icons/Directory.gif")));
		GridBagConstraints gbc_btnBrowseOrigem = new GridBagConstraints();
		gbc_btnBrowseOrigem.insets = new Insets(0, 0, 5, 0);
		gbc_btnBrowseOrigem.gridx = 2;
		gbc_btnBrowseOrigem.gridy = 1;
		frame.getContentPane().add(btnBrowseOrigem, gbc_btnBrowseOrigem);
		
		JLabel lblSaidaxml = new JLabel("Saida:");
		GridBagConstraints gbc_lblSaidaxml = new GridBagConstraints();
		gbc_lblSaidaxml.anchor = GridBagConstraints.EAST;
		gbc_lblSaidaxml.insets = new Insets(0, 5, 5, 5);
		gbc_lblSaidaxml.gridx = 0;
		gbc_lblSaidaxml.gridy = 2;
		frame.getContentPane().add(lblSaidaxml, gbc_lblSaidaxml);
		
		txtDestino = new JTextField();
		GridBagConstraints gbc_txtDestino = new GridBagConstraints();
		gbc_txtDestino.insets = new Insets(0, 0, 5, 5);
		gbc_txtDestino.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDestino.gridx = 1;
		gbc_txtDestino.gridy = 2;
		frame.getContentPane().add(txtDestino, gbc_txtDestino);
		txtDestino.setColumns(10);
		
		JButton btnBrowseDestino = new JButton("");
		btnBrowseDestino.addActionListener(new SalvarArquivoAction());
		btnBrowseDestino.setIcon(new ImageIcon(ConversaoDeDadosForm.class.getResource("/com/sun/java/swing/plaf/windows/icons/Directory.gif")));
		GridBagConstraints gbc_btnBrowseDestino = new GridBagConstraints();
		gbc_btnBrowseDestino.insets = new Insets(0, 0, 5, 0);
		gbc_btnBrowseDestino.gridx = 2;
		gbc_btnBrowseDestino.gridy = 2;
		frame.getContentPane().add(btnBrowseDestino, gbc_btnBrowseDestino);
		
		btnConverter = new JButton("Converter");
		btnConverter.addActionListener(new ConverterArquivoAction(frame));
		GridBagConstraints gbc_btnConverter = new GridBagConstraints();
		gbc_btnConverter.insets = new Insets(0, 0, 0, 5);
		gbc_btnConverter.gridx = 1;
		gbc_btnConverter.gridy = 3;
		frame.getContentPane().add(btnConverter, gbc_btnConverter);
	}
	
	private class AbrirArquivoAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int tipoDialogo = JFileChooser.OPEN_DIALOG;
			String titulo = "Selecione o arquivo CSV para importar";
			String extensao = "CSV";
			
			JFileChooser dialogo = criaDiaogoArquivo(tipoDialogo, titulo, extensao);
			if (dialogo.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
				txtOrigem.setText(dialogo.getSelectedFile().getAbsolutePath());
			}
		}
	}

	private class SalvarArquivoAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int tipoDialogo = JFileChooser.SAVE_DIALOG;
			String titulo = "Salvar XML";
			String extensao = "XML";
			
			JFileChooser dialogo = criaDiaogoArquivo(tipoDialogo, titulo, extensao);
			if (dialogo.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
				String filePath = dialogo.getSelectedFile().getAbsolutePath();
				if ( ! filePath.toLowerCase().endsWith(extensao.toLowerCase())) {
					filePath += ".xml";
				}
				txtDestino.setText(filePath);
			}
		}		
	}	
	
	private JFileChooser criaDiaogoArquivo(int tipoDialogo, String titulo, final String extensao) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setDialogType(tipoDialogo);
		fileChooser.setDialogTitle(titulo);
		fileChooser.setFileFilter(new FileFilter() {			
			@Override
			public String getDescription() {
				return String.format("Arquivos %s (*.%s)", extensao, extensao);
			}
			
			@Override
			public boolean accept(File file) {
				return file.isDirectory() || file.getName().toLowerCase().endsWith("."+ extensao.toLowerCase());
			}
		});
		return fileChooser;
	}
	
	private class ConverterArquivoAction implements ActionListener {
		private JFrame parentFrame;

		public ConverterArquivoAction(JFrame parentFrame) {
			this.parentFrame = parentFrame;
		}

		public void actionPerformed(ActionEvent e) {
			String origem = txtOrigem.getText();
			String destino = txtDestino.getText();
			btnConverter.setEnabled(false);
			
			try {				
				Decathlon decathlon = new Decathlon();
				decathlon.converterArquivo(origem, destino);
				showMessage("Arquivo convertido com sucesso");
			} catch (Exception err) {
				showMessage("Erro ao converter arquivos: " + err.getMessage());
			} finally {
				btnConverter.setEnabled(true);
			}
		}
		
		private void showMessage(String message) {
			JOptionPane.showMessageDialog(parentFrame, message);
		}
	}
}
