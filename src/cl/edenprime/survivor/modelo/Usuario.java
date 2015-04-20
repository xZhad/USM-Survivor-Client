package cl.edenprime.survivor.modelo;

public class Usuario {
	
	private int id;
	private String user;
	private String pass;
	
	
	public Usuario(int id, String user, String pass) {
		setId(id);
		setUser(user);
		setPass(pass);
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getPass() {
		return pass;
	}
	
}
