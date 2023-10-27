class RealEmp extends ObjEmp{

	private double X;


	public RealEmp(double x) {
		this.X = x;
	}


	// OPERATIONS


	// ADD
	public Error add(ObjEmp obj){
		Error out = Error.NO_ERROR;
		if (!(obj instanceof RealEmp))
			out = Error.INCOMPATIBLE; // incompatibles types
		else
			out = this.add((RealEmp)obj);
		return out;
	}

	public Error add(RealEmp obj) {
		this.X += obj.getX();
		return Error.NO_ERROR; // OK
	}


	// SUB
	public Error sub(ObjEmp obj){
		Error out = Error.NO_ERROR;
		if (!(obj instanceof RealEmp))
			out = Error.INCOMPATIBLE; // incompatibles types
		else
			out = this.sub((RealEmp)obj);
		return out;
	}
	
	public Error sub(RealEmp obj) {
		this.X -= obj.getX();
		return Error.NO_ERROR; // OK
	}

	// MULT
	public Error mult(ObjEmp obj){
		Error out = Error.NO_ERROR;
		if (!(obj instanceof RealEmp))
			out = Error.INCOMPATIBLE; // incompatibles types
		else
			out = this.mult((RealEmp)obj);
		return out;
	}
	
	public Error mult(RealEmp obj) {
		this.X -= obj.getX();
		return Error.NO_ERROR; // OK
	}

	// DIV
	public Error div(ObjEmp obj){
		Error out = Error.NO_ERROR;
		if (!(obj instanceof RealEmp))
			out = Error.INCOMPATIBLE; // incompatibles types
		else
			out = this.sub((RealEmp)obj);
		return out;
	}
	
	public Error div(RealEmp obj) {
		Error out = Error.NO_ERROR;
		if (obj.getX() != 0.0){
			this.X /= obj.getX();
			out = Error.NO_ERROR;
		}else{
			out = Error.DIV_BY_ZERO; // division by zero !
		}
		return out;
	}



	public double getX(){
		return this.X;
	}

	@Override
	public String toString(){
		String out = "";
		out += this.X;
		return out;
	}
}
