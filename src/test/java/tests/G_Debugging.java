package tests;

public class G_Debugging {
	
	public static void main(String[] args) {
		
		int a = 10;
		int b = 20;
		
		int c = sum(a,b);
		
		int d = 20;
		int e = 40;
		
		int f = sum(d,e);
		
		int g = 40;
		int h = 50;
		
		int i = sum(g,h);		
	}
	
	public static int sum(int i, int j) {
		int k = i + j;
		return k;
	}

}
