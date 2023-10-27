class ComplexEmp extends ObjEmp{

	private double X;
	private double Y;


	public ComplexEmp(double x, double y) {
		this.X = x;
		this.Y = y;
	}


	// OPERATIONS

	// ADD
	public Error add(ObjEmp obj){
		Error out = Error.NO_ERROR;
		if (!(obj instanceof ComplexEmp))
			out = Error.INCOMPATIBLE;
		else
			out = this.add((ComplexEmp)obj);
		return out;
	}

	public Error add(ComplexEmp obj) {
		this.X += obj.getX();
		this.Y += obj.getY();
		return Error.NO_ERROR;
	}


	// SUB
	public Error sub(ObjEmp obj){
		Error out = Error.NO_ERROR;
		if (!(obj instanceof ComplexEmp))
			out = Error.INCOMPATIBLE;
		else
			out = this.sub((ComplexEmp)obj);
		return out;
	}
	
	public Error sub(ComplexEmp obj) {
		this.X -= obj.getX();
		this.Y -= obj.getY();
		return Error.NO_ERROR;
	}


	// MULT
	public Error mult(ObjEmp obj){ // TODO
		Error out = Error.NO_ERROR;
		if (!(obj instanceof ComplexEmp))
			out = Error.INCOMPATIBLE;
		else
			out = this.sub((ComplexEmp)obj);
		return out;
	}
	
	public Error mult(ComplexEmp obj) { // TODO
		this.X -= obj.getX();
		this.Y -= obj.getY();
		return Error.NO_ERROR;
	}


	// DIV
	public Error div(ObjEmp obj){ // TODO
		Error out = Error.NO_ERROR;
		if (!(obj instanceof ComplexEmp))
			out = Error.INCOMPATIBLE;
		else
			out = this.div((ComplexEmp)obj);
		return out;
	}
	
	public Error div(ComplexEmp obj) { // TODO
		this.X -= obj.getX();
		this.Y -= obj.getY();
		return Error.NO_ERROR;
	}

	public double getX(){
		return this.X;
	}

	public double getY(){
		return this.Y;
	}

	@Override
	public String toString(){
		String out = "";
		out += this.X;
		if (this.Y >= 0)
			out += "+";
		out += this.Y+"i";
		return out;
	}
}
