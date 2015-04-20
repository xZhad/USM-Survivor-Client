package cl.edenprime.survivor.modelo;

public class Blocks {
	
	private int id; // From 1 to 14
	private int starthour;
	private int startminute;
	private int endhour;
	private int endminute;
	
	
	public Blocks(int id, int starthour, int startminute, int endhour, int endminute) {
		setId(id);
		setStarthour(starthour);
		setStartminute(startminute);
		setEndhour(endhour);
		setEndminute(endminute);
	}
	
	public Blocks(int starthour, int startminute, int endhour, int endminute) {
		setId(0);
		setStarthour(starthour);
		setStartminute(startminute);
		setEndhour(endhour);
		setEndminute(endminute);
	}
	
	public String formatStart() {
		String aux = "";
		if (getStartminute() < 10)
			aux = "0";
		String result = getStarthour() + ":" + aux + getStartminute();
		return result;
	}
	
	public String formatEnd() {
		String aux = "";
		if (getEndminute() < 10)
			aux = "0";
		String result = getEndhour() + ":" + aux + getEndminute();
		return result;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}

	public void setStarthour(int starthour) {
		this.starthour = starthour;
	}

	public int getStarthour() {
		return starthour;
	}

	public void setStartminute(int startminute) {
		this.startminute = startminute;
	}

	public int getStartminute() {
		return startminute;
	}

	public void setEndhour(int endhour) {
		this.endhour = endhour;
	}

	public int getEndhour() {
		return endhour;
	}

	public void setEndminute(int endminute) {
		this.endminute = endminute;
	}

	public int getEndminute() {
		return endminute;
	}
	
}
