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


	public boolean push(ObjEmp obj, PrintStream out){
		boolean success = true;
		if (!this.isFull()){ // don't overflow
			this.pile[this.nbObj] = obj; // add object
			this.nbObj += 1; // increment nb of obj
			if (obj.toString().length()+2 > this.reprWidth)
			{
				this.reprWidth = obj.toString().length()+2; // adapt the pile visual representation's width
			}
		}else{
			out.println("The stack is full!");
			success = false;
		}
		return success;
	}

	public ObjEmp pop(PrintStream out){
		ObjEmp temp = null;
		if (!this.isEmpty()){
			this.nbObj -= 1;
			temp = this.pile[this.nbObj];
			this.pile[this.nbObj] = null;
		}else{
			out.println("The stack is empty!");
		}
		return temp;
	}
	
	// OPERATIONS ON OBJ


	public boolean add(PrintStream out){
		boolean success = true;
		if (this.nbObj < 2){
			out.println("Can't add : not enough objects!");
			success = false;
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
				success = false;
			}
		}
		return success;
	}


	public boolean sub(PrintStream out){
		boolean success = true;
		if (this.nbObj < 2){
			out.println("Can't substract: not enough objects!");
			success = false;
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
				success = false;
			}
		}
		return success;
	}

	public boolean mult(PrintStream out){
		boolean success = true;
		if (this.nbObj < 2){
			out.println("Can't multiply : not enough objects!");
			success = false;
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
				success = false;
			}
		}
		return success;
	}

	public boolean div(PrintStream out){
		boolean success = true;
		if (this.nbObj < 2){
			out.println("Can't divide : not enough objects!");
			success = false;
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
				success = false;
			}
		}
		return success;
	}

	public String toString(){
		String out = "";
		if (this.isEmpty())
		{
			out += "Empty pile\n";
			out += "(capacity: "+this.pile.length+")";
			this.reprWidth = 0;
		}else{
			for (int i=this.nbObj-1; i>=0; i--){
				out += i+"| ";
				out += this.pile[i];
				out += " ".repeat(this.reprWidth-this.pile[i].toString().length()-1);
				out += "|\n";
			}
			out += " +"+"-".repeat(this.reprWidth);
		}
		return out;
	}

}
