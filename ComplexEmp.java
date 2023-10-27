
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
	public Error mult(ObjEmp obj){
		Error out = Error.NO_ERROR;
		if (!(obj instanceof ComplexEmp))
			out = Error.INCOMPATIBLE;
		else
			out = this.mult((ComplexEmp)obj);
		return out;
	}
	
	public Error mult(ComplexEmp obj) {
		double real = (this.X*obj.getX() - this.Y*obj.getY());
		double img = (this.X*obj.getY() + this.Y*obj.getX());
		this.X = real;
		this.Y = img;
		return Error.NO_ERROR;
	}


	// DIV
	public Error div(ObjEmp obj){
		Error out = Error.NO_ERROR;
		if (!(obj instanceof ComplexEmp))
			out = Error.INCOMPATIBLE;
		else
			out = this.div((ComplexEmp)obj);
		return out;
	}
	
	public Error div(ComplexEmp obj) {
		Error out = Error.NO_ERROR;
		double den = Math.pow(obj.getX(), 2) + Math.pow(obj.getY(), 2);
		if (den == 0.0){
			out = Error.DIV_BY_ZERO;
		}else{
			double real = (this.X*obj.getX() + this.Y*obj.getY()) / den;
			double img = (this.Y*obj.getX() - this.X*obj.getY()) / den;
			this.X = real;
			this.Y = img;
		}
		return out;
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
