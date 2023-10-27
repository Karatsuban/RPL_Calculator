class Vector3DEmp extends ObjEmp{

	private double X;
	private double Y;
	private double Z;

	public Vector3DEmp(double x, double y, double z) {
		this.X = x;
		this.Y = y;
		this.Z = z;
	}


	// OPERATIONS


	public Error add(ObjEmp obj){
		Error out = Error.NO_ERROR;
		if (!(obj instanceof Vector3DEmp))
			out = Error.INCOMPATIBLE;
		else
			out = this.add((Vector3DEmp)obj);
		return out;
	}

	public Error sub(ObjEmp obj){
		Error out = Error.NO_ERROR;
		if (!(obj instanceof Vector3DEmp))
			out = Error.INCOMPATIBLE;
		else
			out = this.sub((Vector3DEmp)obj);
		return out;
	}
	

	public Error add(Vector3DEmp obj) {
		this.X += obj.getX();
		this.Y += obj.getY();
		this.Z += obj.getZ();
		return Error.NO_ERROR;
	}


	public Error sub(Vector3DEmp obj) {
		this.X -= obj.getX();
		this.Y -= obj.getY();
		this.Z -= obj.getZ();
		return Error.NO_ERROR;

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
