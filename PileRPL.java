import java.util.Objects;

class PileRPL {

	private final static int NBOBJMAX = 5;
	private int nbObj;
	public ObjEmp[] pile;

	private int reprWidth;

	public PileRPL(int size){
		if (size <= 0)
			size = this.NBOBJMAX; // default size
		this.pile = new ObjEmp[size];
		this.nbObj = 0;
		this.reprWidth = 0;
	}

	public PileRPL(){
		this(NBOBJMAX);
	}

	private boolean isEmpty(){
		return this.nbObj == 0;
	}

	private boolean isFull(){
		return this.nbObj == this.pile.length;
	}


	public void push(ObjEmp obj){
		if (!this.isFull()){ // don't overflow
			//System.out.println("Pushing "+obj);
			this.pile[this.nbObj] = obj; // add object
			this.nbObj += 1; // increment nb of obj
			if (obj.toString().length()+2 > this.reprWidth)
			{
				this.reprWidth = obj.toString().length()+2; // adapt the pile visual representation's width
			}
		}else{
			System.out.println("The stack is full!");
		}
	}

	public ObjEmp pop(){
		ObjEmp temp = null;
		if (!this.isEmpty()){
			this.nbObj -= 1;
			temp = this.pile[this.nbObj];
			this.pile[this.nbObj] = null;
		}else{
			System.out.println("The stack is empty!");
		}
		return temp;
	}
	
	// OPERATIONS ON OBJ


	public void add(){
		if (this.nbObj < 2){
			System.out.println("Can't add : not enough objects!");
		}else{
			//System.out.println("Addition");
			ObjEmp o1 = this.pop();
			ObjEmp o2 = this.pop();
			if (o2.add(o1))
			{
				this.push(o2); // operation successful
				o1 = null;
			}else{ // operation failed
				System.out.println("Operation failed: operands of different types!");
				this.push(o2); // pushing back both operands
				this.push(o1);
			}
		}
	}


	public void sub(){
		if (this.nbObj < 2){
			System.out.println("Can't substract : not enough objects!");
		}else{
			//System.out.println("Subsraction");
			ObjEmp o1 = this.pop();
			ObjEmp o2 = this.pop();
			if (o2.sub(o1))
			{
				this.push(o2); // operation successful
				o1 = null;
			}else{
				System.out.println("Operation failed: operands of different types!");
				this.push(o2); // operation failed
				this.push(o1);
			}
		}
	}

	public String toString(){
		String out = "";
		if (this.isEmpty())
		{
			out += "The pile is empty!\n";
		}else{
			for (int i=this.nbObj-1; i>=0; i--){
				out += i+"| ";
				out+= this.pile[i]+" ".repeat(this.reprWidth-this.pile[i].toString().length()-1);
				out += "|\n";
			}
			out += " +"+"-".repeat(this.reprWidth)+"+\n";
		}
		out += "(capacity: "+this.pile.length+")";
		return out;
	}

}
