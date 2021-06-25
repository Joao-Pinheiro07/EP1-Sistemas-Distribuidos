import java.util.ArrayList;

public interface Part {
	public String getName();
	public String getDescription();
	public RepositoryServant getRepository();
	public boolean isPrimitivePart();
	public ArrayList<Part> listSubParts();
}
