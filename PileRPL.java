import java.util.Objects;
import java.io.*;

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
		this( NBOBJMAX);
	}

	private boolean isEmpty(){
		return this.nbObj == 0;
	}

	private boolean isFull(){
		return this.nbObj == this.pile.length;
	}


	public void push(ObjEmp obj, PrintStream outStream){
		if (!this.isFull()){ // don't overflow
			this.pile[this.nbObj] = obj; // add object
			this.nbObj += 1; // increment nb of obj
			if (obj.toString().length()+2 > this.reprWidth)
			{
				this.reprWidth = obj.toString().length()+2; // adapt the pile visual representation's width
			}
		}else{
			outStream.println("The stack is full!");
		}
	}

	public ObjEmp pop(PrintStream outStream){
		ObjEmp temp = null;
		if (!this.isEmpty()){
			this.nbObj -= 1;
			temp = this.pile[this.nbObj];
			this.pile[this.nbObj] = null;
		}else{
			outStream.println("The stack is empty!");
		}
		return temp;
	}
	
	// OPERATIONS ON OBJ


	public void add(PrintStream out){
		if (this.nbObj < 2){
			out.println("Can't add : not enough objects!");
		}else{
			ObjEmp o1 = this.pop(out);
			ObjEmp o2 = this.pop(out);
			Error err = o2.add(o1);
			if (!err.isError())
			{
				this.push(o2, out); // operation successful
				o1 = null;
			}else{ // operation failed
				out.println("Operation failed: operands of different types!");
				this.push(o2, out); // pushing back both operands
				this.push(o1, out);
			}
		}
	}


	public void sub(PrintStream out){
		if (this.nbObj < 2){
			out.println("Can't substract: not enough objects!");
		}else{
			ObjEmp o1 = this.pop(out);
			ObjEmp o2 = this.pop(out);
			Error err = o2.sub(o1);
			if (!err.isError())
			{
				this.push(o2, out); // successful operation
				o1 = null;
			}else{
				out.println("Operation failed: "+err.get());
				this.push(o2, out); // operation failed
				this.push(o1, out);
			}
		}
	}

	public void mult(PrintStream out){
		if (this.nbObj < 2){
			out.println("Can't multiply : not enough objects!");
		}else{
			ObjEmp o1 = this.pop(out);
			ObjEmp o2 = this.pop(out);
			Error err = o2.mult(o1);
			if (!err.isError()){
				this.push(o2, out); // successful operation
				o1 = null;
			}else{
				out.println("Operation failed: "+err.get());
				this.push(o2, out); // operation failed
				this.push(o1, out);
			}
		}
	}

	public void div(PrintStream out){
		if (this.nbObj < 2){
			out.println("Can't divide : not enough objects!");
		}else{
			ObjEmp o1 = this.pop(out);
			ObjEmp o2 = this.pop(out);
			Error err = o2.div(o1); // operation
			if (!err.isError()){
				this.push(o2, out); // successful operation
				o1 = null;
			}else{
				out.println("Operation failed: "+err.get());
				this.push(o2, out); // operation failed
				this.push(o1, out);
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
				out += this.pile[i];
				out += " ".repeat(this.reprWidth-this.pile[i].toString().length()-1);
				out += "|\n";
			}
			out += " +"+"-".repeat(this.reprWidth)+"+\n";
		}
		out += "(capacity: "+this.pile.length+")";
		return out;
	}

}
