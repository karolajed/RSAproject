import java.io.Serializable;
import java.math.BigInteger;

public class PrivateKey implements Serializable {

	private static final long serialVersionUID = 1L;
	private BigInteger n;
	private BigInteger d;
	
	public BigInteger getN() {
		return n;
	}
	public void setN(BigInteger n) {
		this.n = n;
	}
	public BigInteger getD() {
		return d;
	}
	public void setD(BigInteger d) {
		this.d = d;
	}
}
