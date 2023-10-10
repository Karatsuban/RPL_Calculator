class ComplexEmp implements ObjEmp{

	private double X;
	private double Y;


	public ComplexEmp(double x, double y) {
		this.X = x;
		this.Y = y;
	}


	// OPERATIONS


	public void add(ObjEmp obj){}
	public void sub(ObjEmp obj){}
	

	public void add(ComplexEmp obj) {
		this.X += obj.getX();
		this.Y += obj.getY();
	}

//		if (Class.forName("ComplexEmp").isInstance(obj)){

	public void sub(ComplexEmp obj) {
		this.X -= obj.getX();
		this.Y -= obj.getY();

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
