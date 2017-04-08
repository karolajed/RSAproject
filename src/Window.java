import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Window extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private RSA rsa;
	private JLabel lKey, lEncrypt, lMessageEncrypt, lDecrypt, lMessageDecrypt;
	private JTextField tEncrypt, tMessageEncrypted, tDecrypt, tMessageDecrypted;
	private JButton bKey, bPublicKeyUpload, bEncrypt, bPrivateKeyUpload, bDecrypt;
	private PublicKey publicKey;
	private PrivateKey privateKey;
	
	public Window() {
		setSize(410,300);
		setTitle("RSA");
		setLayout(null);
		
		rsa = new RSA();
		
		//title
		lKey = new JLabel("KEY ");
		lKey.setBounds(20, 0, 150, 20);
		add(lKey);
		
		//generate key button
		bKey = new JButton("Generate Key");
		bKey.setBounds(20, 30, 150, 20);
		add(bKey);
		bKey.addActionListener(this);
		
		//title
		lEncrypt = new JLabel("ENCRYPTION");
		lEncrypt.setBounds(20, 60, 150, 20);
		add(lEncrypt);
		
		//upload public key button
		bPublicKeyUpload = new JButton("Upload public key");
		bPublicKeyUpload.setBounds(20, 90, 150, 20);
		add(bPublicKeyUpload);
		bPublicKeyUpload.addActionListener(this);
		
		//enter message text
		lMessageEncrypt = new JLabel("Enter message: ");
		lMessageEncrypt.setBounds(20, 120, 150, 20);
		add(lMessageEncrypt);
		
		//text field to upload message
		tEncrypt = new JTextField("");
		tEncrypt.setBounds(20, 150, 150, 20);
		add(tEncrypt);
		
		//encrypt button
		bEncrypt = new JButton("Encrypt");
		bEncrypt.setBounds(20, 180, 150, 20);
		add(bEncrypt);
		bEncrypt.addActionListener(this);
		
		//text field to receive encrypted message
		tMessageEncrypted = new JTextField("");
		tMessageEncrypted.setBounds(20, 210, 150, 20);
		add(tMessageEncrypted);
		
		//title
		lDecrypt = new JLabel("DECRYPTION");
		lDecrypt.setBounds(220, 60, 150, 20);
		add(lDecrypt);
		
		//upload private key button
		bPrivateKeyUpload = new JButton("Upload private key");
		bPrivateKeyUpload.setBounds(220, 90, 150, 20);
		add(bPrivateKeyUpload);
		bPrivateKeyUpload.addActionListener(this);
		
		//enter message text
		lMessageDecrypt = new JLabel("Enter message: ");
		lMessageDecrypt.setBounds(220, 120, 150, 20);
		add(lMessageDecrypt);
		
		//text field to upload message
		tDecrypt = new JTextField("");
		tDecrypt.setBounds(220, 150, 150, 20);
		add(tDecrypt);
		
		//decrypt button
		bDecrypt = new JButton("Decrypt");
		bDecrypt.setBounds(220, 180, 150, 20);
		add(bDecrypt);
		bDecrypt.addActionListener(this);
		
		//text field to receive decrypted message
		tMessageDecrypted = new JTextField("");
		tMessageDecrypted.setBounds(220, 210, 150, 20);
		add(tMessageDecrypted);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();
		
		if(source==bKey) {
			JFileChooser chooseKeyPath = new JFileChooser();
			chooseKeyPath.setCurrentDirectory(new java.io.File("."));
			chooseKeyPath.setDialogTitle("Choose the directory to save keys.");
			chooseKeyPath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooseKeyPath.setAcceptAllFileFilterUsed(false);
			if (chooseKeyPath.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				String path = chooseKeyPath.getSelectedFile().toString();
				try {
					  rsa.generateKey(path);
				 } catch (Exception e1) {
					 JOptionPane.showMessageDialog(null, "Something went wrong :(");
					 e1.printStackTrace();
				 }
			}
		}
		else if(source==bPublicKeyUpload) {
			JFileChooser publicKeyChooser = new JFileChooser();
			publicKeyChooser.setCurrentDirectory(new java.io.File("."));
			publicKeyChooser.setDialogTitle("Select public key.");
			publicKeyChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("KEYS", "key");
			publicKeyChooser.setFileFilter(filter);
			publicKeyChooser.setAcceptAllFileFilterUsed(false);
			if (publicKeyChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				String path = publicKeyChooser.getSelectedFile().toString();
				try {
					ObjectInputStream publicKeyStream = new ObjectInputStream(new FileInputStream(path));
					publicKey = (PublicKey) publicKeyStream.readObject();
					publicKeyStream.close();
				} catch (FileNotFoundException e1) {
					 JOptionPane.showMessageDialog(null, "Something went wrong :(");
					e1.printStackTrace();
				} catch (IOException e1) {
					 JOptionPane.showMessageDialog(null, "Something went wrong :(");
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					 JOptionPane.showMessageDialog(null, "Something went wrong :(");
					e1.printStackTrace();
				}
			}
		}
		else if(source==bPrivateKeyUpload) {
			JFileChooser privateKeyChooser = new JFileChooser();
			privateKeyChooser.setCurrentDirectory(new java.io.File("."));
			privateKeyChooser.setDialogTitle("Select private key.");
			privateKeyChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("KEYS", "key");
			privateKeyChooser.setFileFilter(filter);
			privateKeyChooser.setAcceptAllFileFilterUsed(false);
			if (privateKeyChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				String path = privateKeyChooser.getSelectedFile().toString();
				try {
					ObjectInputStream privateKeyStream = new ObjectInputStream(new FileInputStream(path));
					privateKey = (PrivateKey) privateKeyStream.readObject();
					privateKeyStream.close();
				} catch (FileNotFoundException e1) {
					 JOptionPane.showMessageDialog(null, "Something went wrong :(");
					e1.printStackTrace();
				} catch (IOException e1) {
					 JOptionPane.showMessageDialog(null, "Something went wrong :(");
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					 JOptionPane.showMessageDialog(null, "Something went wrong :(");
					e1.printStackTrace();
				}
			}
		}
		else if(source==bEncrypt) {
			String messageToEncrypt = tEncrypt.getText();
			if(publicKey==null) {
				JOptionPane.showMessageDialog(null, "Upload public key!");
			}
			else if(messageToEncrypt=="") {
				JOptionPane.showMessageDialog(null, "Give me message to encrypt!");
				}
				else {
					String encrypted = rsa.encrypt(messageToEncrypt, publicKey);
					tMessageEncrypted.setText(encrypted);
				}
		}
		else if(source==bDecrypt) {
			String messageToDecrypt = tDecrypt.getText();
			if(privateKey==null) {
				JOptionPane.showMessageDialog(null, "Upload private key!");
			}
			else if(messageToDecrypt=="") {
				JOptionPane.showMessageDialog(null, "Give me message to decrypt!");
			}
			else {
				String decrypted = rsa.decrypt(messageToDecrypt, privateKey);
				tMessageDecrypted.setText(decrypted);
			}
		}
	}
}
