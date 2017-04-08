import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA {

	private BigInteger n, d, e;
	private	int bitlen = 1024;
		
	protected void generateKey(String path) throws FileNotFoundException, IOException {
	
			// Generowanie dwóch du¿ych liczb pierwszych p i q
		 	SecureRandom r = new SecureRandom();
		 	
		 	PublicKey publicKey = new PublicKey();
		 	PrivateKey privateKey = new PrivateKey();
		 	
			BigInteger p = new BigInteger(bitlen / 2, 100, r);
		    BigInteger q = new BigInteger(bitlen / 2, 100, r);
		    
		    // n=p*q
		    n = p.multiply(q);
		    publicKey.setN(n);
		    privateKey.setN(n);
		    // m=(p-1)(q-1)
		    BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		    
		    // e - liczba nieparzysta, wzglêdnie pierwsza z m
		    e = new BigInteger("3");
		    while (m.gcd(e).intValue() > 1) {
		    	e = e.add(new BigInteger("2"));
		    }
		    // d takie, ¿e d*e mod(m)=1
		    d = e.modInverse(m);
		    publicKey.setE(e);
		    privateKey.setD(d);
		    
		    ObjectOutputStream publicKeyStream = new ObjectOutputStream(new FileOutputStream(new File(path,"PublicKey.key")));
		    ObjectOutputStream privateKeyStream = new ObjectOutputStream(new FileOutputStream(new File(path,"PrivateKey.key")));
		    publicKeyStream.writeObject(publicKey);
		    privateKeyStream.writeObject(privateKey);
		    publicKeyStream.close();
		    privateKeyStream.close();
		    
	}
	
	protected static PublicKey loadPublicKey(String path) throws Exception {
		PublicKey key = new PublicKey();
		ObjectInputStream publicKeyStream = new ObjectInputStream(new FileInputStream(path));
		key = (PublicKey) publicKeyStream.readObject();
		publicKeyStream.close();
		return key;
	}
	
	protected static PrivateKey loadPrivateKey(String path) throws Exception {
		PrivateKey key = new PrivateKey();
		ObjectInputStream privateKeyStream = new ObjectInputStream(new FileInputStream(path));
		key = (PrivateKey) privateKeyStream.readObject();
		privateKeyStream.close();
		return key;
	}
	
	protected String encrypt(String message, PublicKey key) {
		// zaszyfrowanie ci¹gu cyfr
		BigInteger e = key.getE();
		BigInteger n = key.getN();
		BigInteger encryptedMessage = new BigInteger(message).modPow(e, n);
		return encryptedMessage.toString();
	}
	
	protected String decrypt(String encryptedMessage, PrivateKey key) {
		// odszyfrowanie ci¹gu cyfr
		BigInteger d = key.getD();
		BigInteger n = key.getN();
		BigInteger decryptedMessage = new BigInteger(encryptedMessage).modPow(d, n);
		return decryptedMessage.toString();
			
	}
}
