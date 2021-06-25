import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Peca implements Part {
	private UUID uid = UUID.randomUUID();
	private String name;
	private String description;
	private ArrayList<Subcomponente> subcomponentes;
	private String repositorio;

	/* Criando uma nova peça com atributos definidos */

	public Peca(UUID uid, String name, String description, ArrayList<Subcomponente> Subcomponentes,
			String repositorio) {

		super();
		this.uid = uid;
		this.name = name;
		this.description = description;
		this.subcomponentes = Subcomponentes;
		this.repositorio = repositorio;
		this.isPrimitivePart();

	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public ArrayList<Subcomponente> getSubComponentes() {
		return subcomponentes;
	}

	@Override
	public void setSubcomponentes(ArrayList<Subcomponente> subcomponentes) {
		this.subcomponentes = subcomponentes;
	}

	@Override
	public String getRepositorio() {
		return repositorio;
	}

	@Override
	public void setRepositorio(String repositorio) {
		this.repositorio = repositorio;
	}
}
