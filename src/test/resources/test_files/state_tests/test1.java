class test1{
	public static void main(String[] args){
	}

	public static void bar(){
		Object o = new Object();
		o.toString();
	}
	public static void baz(boolean b){
		Object o = null;
		if(b){
			o = new Object();
		}
		o.toString();	
	}
}
