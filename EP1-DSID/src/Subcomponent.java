import java.io.Serializable;

public class Subcomponent implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Part subPart;
	private int quant;

	public Subcomponent(Part subPart, int quant) {
		super();
		this.subPart = subPart;
		this.quant = quant;
	}

	public Part getSubPart() {
		return subPart;
	}

	public int getQuant() {
		return quant;
	}
	
	
}
