class ComplexEmp implements ObjEmp{

	private double X;
	private double Y;


	public ComplexEmp(double x, double y) {
		this.X = x;
		this.Y = y;
	}


	// OPERATIONS


	public boolean add(ObjEmp obj){
		if (!(obj instanceof ComplexEmp))
			return false;
		return this.add((ComplexEmp)obj);
	}
	public boolean sub(ObjEmp obj){
		if (!(obj instanceof ComplexEmp))
			return false;
		return this.sub((ComplexEmp)obj);
	}
	

	public boolean add(ComplexEmp obj) {
		this.X += obj.getX();
		this.Y += obj.getY();
		return true;
	}

//		if (Class.forName("ComplexEmp").isInstance(obj)){

	public boolean sub(ComplexEmp obj) {
		this.X -= obj.getX();
		this.Y -= obj.getY();
		return true;
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
