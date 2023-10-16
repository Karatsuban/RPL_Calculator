import java.lang.*;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class CalcUI{


	private PileRPL pile;
	private Pattern realPattern;
	private Pattern complexPattern;
	private Pattern vector3DPattern;
	private Pattern cmdPattern;

	public CalcUI(int size){
		this.pile = new PileRPL(size);
		this.initPatterns();
	}

	public CalcUI(){
		this.pile = new PileRPL();
		this.initPatterns();
	}


	private void initPatterns(){
		this.realPattern = Pattern.compile("[\\+-]?\\p{Digit}+(\\.\\p{Digit}+)?");
		this.complexPattern = Pattern.compile("[\\+-]?\\p{Digit}+(\\.\\p{Digit}+)?[\\+-](\\p{Digit}+(\\.\\p{Digit}+)?)?[iI]");
		this.vector3DPattern = Pattern.compile("([\\+-]?\\p{Digit}+(\\.\\p{Digit}+)?,){2}[\\+-]?(\\p{Digit})+(\\.\\p{Digit}+)?");
		this.cmdPattern = Pattern.compile("(push|disp|add|sub|exit)");
	}

	private boolean isReal(String w){
		return this.realPattern.matcher(w).matches();
	}

	private boolean isComplex(String w){
		return this.complexPattern.matcher(w).matches();
	}

	private boolean isVector3D(String w){
		return this.vector3DPattern.matcher(w).matches();
	}

	private boolean isCommand(String w){
		return this.cmdPattern.matcher(w).matches();
	}

	private void addReal(String w){
		ObjEmp r = new RealEmp(Double.parseDouble(w));
		this.pile.push(r);
	}

	
	private void addComplex(String w){
		int indexSepSign = Math.max(w.indexOf("+", 1), w.indexOf("-", 1));
		double im = 1;
		double re = 1;

		if (indexSepSign == -1){
			// the string is only composed of an imaginary number !
			re = 0;
			indexSepSign = 0;
		}else{

			String res = w.substring(0,indexSepSign); // get the real part
			re = Double.parseDouble(res); // convert the real part to double
		}

		if (w.charAt(indexSepSign) == '-')
			im = -1; // if the sign is '-', the im part will be negative
		
		
		String ims = w.substring(indexSepSign+1, w.length()-1); // get the imaginary part
		if (ims.length() != 0) // the im part is only composed of i...
			im *= Double.parseDouble(ims); // so im = 1
		
		ObjEmp c = new ComplexEmp(re, im);
		this.pile.push(c);
	}

	private void addVector3D(String w){
		String[] words = w.split(",");
		double[] values = new double[3];
		for (int i=0; i<3; i++)
			values[i] = Double.parseDouble(words[i]);
		ObjEmp v3d = new Vector3DEmp(values[0], values[1], values[2]);
		this.pile.push(v3d);
	}
	


	public void launch(){
		boolean isOver = false;
		Scanner sc;
		String[] words;
		Boolean isCmd;
		
		String line;
		while (!isOver){
			sc = new Scanner(System.in);
			line = sc.nextLine();
			words = line.split(" ");

			if (this.isCommand(words[0])){
				switch (words[0]){

					case "exit":
						isOver = true;
						break;
			
					case "push":
						for (int i=1; i<words.length; i++){

							if (this.isReal(words[i]))
								this.addReal(words[i]);
							else if (this.isComplex(words[i]))
								this.addComplex(words[i]);
							else if (this.isVector3D(words[i]))
								this.addVector3D(words[i]);

						}

						break;
		
					case "disp":
						System.out.println(this.pile);
						break;
			
					case "add":
						this.pile.add();
						break;
			
					case "sub":
						this.pile.sub();
						break;
				};

			}else{
				System.out.println("Unknonw command: '"+words[0]+"'");
			}

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
