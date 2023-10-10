import java.lang.*;
import java.util.Scanner;

class CalcUI{


	private boolean isOver;;
	private PileRPL pile;

	public CalcUI(int size){
		this.isOver = false;
		this.pile = new PileRPL(size);
	}

	public CalcUI(){
		this.isOver = false;
		this.pile = new PileRPL();
	}


	private boolean match(String[] rule, String toCheck){
		boolean ret = true;
		int i=0;
		while (i<arr.length){
			switch(arr[i]){
				case "double":
					ret &= this.isDouble(arr[i]);
					break;
				case "int":
					ret &= this.isInt(arr[i]);
					break;
				case "cmd":
				
		}
	}

	private boolean isInt(String w){
	}

	private boolean isdouble(String w){
	}

	private boolean isCommand(String s){
		String[] cmds = {"push", "disp", "add", "sub"};
		return cmds.contains(s);
	}
		

	private void analyze(String s){
		String[] words = s.split(" "); // split at spaces
		
			
	}

	public void launch(){
		Scanner sc;
		String[] words;
		String line;
		while (!this.isOver){
			sc = new Scanner(System.in);
			line = sc.nextLine();
			if (line.equals("exit"))
				this.isOver = true;


		}
	}

	public static void main(String[] args){

		CalcUI UI = null;

		if (args.length > 0)
			UI = new CalcUI(Integer.valueOf(args[0]));
		else
			UI = new CalcUI();

		UI.launch();
	}
}
