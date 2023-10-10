class RealEmp implements ObjEmp{

	private double X;


	public RealEmp(double x) {
		this.X = x;
	}


	// OPERATIONS


	public void add(ObjEmp obj){}
	public void sub(ObjEmp obj){}
	

	public void add(RealEmp obj) {
		this.X += obj.getX();
	}

//		if (Class.forName("RealEmp").isInstance(obj)){

	public void sub(RealEmp obj) {
		this.X -= obj.getX();
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
