
public enum Error {
	NO_ERROR ("No error"),
	INCOMPATIBLE ("Incompatible tpyes"),
	NOT_PERMITTED ("Operation not permitted"),
	DIV_BY_ZERO ("Division by zero");

	private final String explanation;

	Error(String explanation){
		this.explanation = explanation;
	}

	public String get() { 
		return explanation;
	}

	public boolean isError(){
		return (!this.equals(Error.values()[0]));
	}

}
