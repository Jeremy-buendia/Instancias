package application.model;

public class CategoriaDO {
	
	private int idCategoria;
	private String NombreCategoria;
	public int getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}
	public String getNombreCategoria() {
		return NombreCategoria;
	}
	public void setNombreCategoria(String nombreCategoria) {
		NombreCategoria = nombreCategoria;
	}
	public CategoriaDO(int idCategoria, String nombreCategoria) {
		super();
		this.idCategoria = idCategoria;
		NombreCategoria = nombreCategoria;
	}
	public CategoriaDO() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "CategoriaDO [idCategoria=" + idCategoria + ", NombreCategoria=" + NombreCategoria + "]";
	}
	
	
	
}
