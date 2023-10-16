class Vector3DEmp implements ObjEmp{

	private double X;
	private double Y;
	private double Z;

	public Vector3DEmp(double x, double y, double z) {
		this.X = x;
		this.Y = y;
		this.Z = z;
	}


	// OPERATIONS


	public boolean add(ObjEmp obj){
		if (!(obj instanceof Vector3DEmp))
			return false;
		return this.add((Vector3DEmp)obj);
	}
	public boolean sub(ObjEmp obj){
		if (!(obj instanceof Vector3DEmp))
			return false;
		return this.sub((Vector3DEmp)obj);
	}
	

	public boolean add(Vector3DEmp obj) {
		this.X += obj.getX();
		this.Y += obj.getY();
		this.Z += obj.getZ();
		return true;
	}


	public boolean sub(Vector3DEmp obj) {
		this.X -= obj.getX();
		this.Y -= obj.getY();
		this.Z -= obj.getZ();
		return true;

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
		out += this.X+",";
		out += this.Y+",";
		out += this.Z+")";
		return out;
	}
}
