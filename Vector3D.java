class Vector3D implements ObjEmp{

	private double X;
	private double Y;
	private double Z;

	public Vector3D(double x, double y, double z) {
		this.X = x;
		this.Y = y;
		this.Z = z;
	}


	// OPERATIONS


	public void add(ObjEmp obj){}
	public void sub(ObjEmp obj){}
	

	public void add(Vector3D obj) {
		this.X += obj.getX();
		this.Y += obj.getY();
		this.Z += obj.getZ();
	}

//		if (Class.forName("Vector3D").isInstance(obj)){

	public void sub(Vector3D obj) {
		this.X -= obj.getX();
		this.Y -= obj.getY();
		this.Z -= obj.getZ();

	}

	public double getX(){
		return this.X;
	}


	public double getY(){
		return this.Y;
	}

	public double getZ(){
		return this.Z;
	}

	@Override
	public String toString(){
		String out = "(";
		out += this.X;
		out += this.Y;
		out += this.Z;
		return out;
	}
}
