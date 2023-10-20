
class Parser{
	
	String[] tokens = null;
	int index;
	int size;

	public Parser(String[] args){
		this.tokens = args;
		this.size = args.length;
		this.index = 0;
	}

	public Parser(String args){
		this(args.split(" ")); // split on space
	}


	public String consume(){
		// return the next token and consuming it
		String out = "NO_TOKEN"; // default out, if not token can be consumed

		if (this.index < this.size){
			out = this.tokens[this.index];
			this.index += 1;
		}
		
		return out;
	}


	public String getAhead(int n){
		// return the nth next token without consuming it
		String out = "NO_TOKEN";
		if (this.index+n <this.size){
			out = this.tokens[this.index+n];
		}
		return out;
	}

	public String getToken(){
		// return the current token without consuming it
		return this.getAhead(0);
	}

	public void advance(int n){
		// move the index of n positions to the right
		this.index += n;
	}

	public boolean hasTokens(){
		// return true if there are remaining tokens
		return this.index < this.size;
	}

}
