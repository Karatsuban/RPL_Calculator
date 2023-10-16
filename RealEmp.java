class RealEmp implements ObjEmp{

	private double X;


	public RealEmp(double x) {
		this.X = x;
	}


	// OPERATIONS


	public boolean add(ObjEmp obj){
		if (!(obj instanceof RealEmp))
			return false; // incompatibles types
		return this.add((RealEmp)obj);
	}

	public boolean sub(ObjEmp obj){
		if (!(obj instanceof RealEmp))
			return false; // incompatibles types
		return this.sub((RealEmp)obj);
	}
	

	public boolean add(RealEmp obj) {
		this.X += obj.getX();
		return true;
	}

//		if (Class.forName("RealEmp").isInstance(obj)){

	public boolean sub(RealEmp obj) {
		this.X -= obj.getX();
		return true;
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
