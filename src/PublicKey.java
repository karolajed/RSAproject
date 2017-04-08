import java.io.Serializable;
import java.math.BigInteger;

public class PublicKey implements Serializable {

	private static final long serialVersionUID = 1L;
	private BigInteger n;
	private BigInteger e;
	
	public BigInteger getN() {
		return n;
	}
	public void setN(BigInteger n) {
		this.n = n;
	}
	public BigInteger getE() {
		return e;
	}
	public void setE(BigInteger e) {
		this.e = e;
	}
}

