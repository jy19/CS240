import java.util.Scanner;


public class Main {
	static int MAX = 1428;
	static long[][] numPaths = new long[MAX][21];
	public static void main(String [] args){
		Scanner sc = new Scanner(System.in);
		setUp();
		String zeroes = "00000000000000000000";
		while(sc.hasNextInt()){
			int nodes = sc.nextInt();
			long total = 0;
			for(int i = 0; i < numPaths[0].length; i++){
				total += numPaths[nodes][i];
			}
			total %= 1000000000;
			String ans = "" + total;
			System.out.println(zeroes.substring(0, 9 - ans.length()) + ans);
		}
		sc.close();
	}
	public static void setUp(){
		numPaths[0][0] = 1;
		numPaths[1][1] = 1;
		for(int numNodes = 2; numNodes < numPaths.length; numNodes++){
			for(int left = 0, right = numNodes-1; right >= 0; left++, right--){
				for(int height = 0; height < numPaths[0].length-1; height++){
					if(height == 0){
						numPaths[numNodes][1] += numPaths[left][height]*numPaths[right][height]; 
					} 
					else { 
						numPaths[numNodes][height+1] += 
								numPaths[left][height]*numPaths[right][height] + 
								numPaths[left][height-1]*numPaths[right][height] + 
								numPaths[left][height]*numPaths[right][height-1]; 
					} 
					numPaths[numNodes][height+1] %= 1000000000; 
				}
			}
		}
	}
}
