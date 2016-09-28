import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import java.util.LinkedHashMap;


public class Matcher {

	//declaring global variables
	public static LinkedHashMap<String,String> currentState = new LinkedHashMap<String,String>();
	public static int numOfPairs = 0;
	public static LinkedHashMap<String,ArrayList<String>> linkedInP = new LinkedHashMap<String,ArrayList<String>>();
	public static LinkedHashMap<String,ArrayList<String>> linkedInC = new LinkedHashMap<String,ArrayList<String>>();

	//generates initial hashmap of n companies and programmers
	public static void generateHash(int n){

		numOfPairs = n;
		
		for (int i=1; i<=n;i++){
			ArrayList<String> temp = new ArrayList<String>(n);
			for (int j=1;j<=n;j++){
				String tempS = "C"+j;
				temp.add(tempS);
			}
			Collections.shuffle(temp);
			linkedInP.put("P" + i, temp);
		}
		for (int i=1; i<=n;i++){
			ArrayList<String> temp = new ArrayList<String>(n);
			for (int j=1;j<=n;j++){
				String tempS = "P"+j;
				temp.add(tempS);
			}
			Collections.shuffle(temp);
			linkedInC.put("C" + i, temp);
		}

	}
	
	//method for printing the initially generated hashmap
	public static void printHashMap(){
		ArrayList<String> listOfKeys = new ArrayList<String>();

		for (String key : linkedInP.keySet()){
			ArrayList<String> temp = linkedInP.get(key);
			System.out.print(key + " ");
			System.out.println(Arrays.toString(temp.toArray()));
			listOfKeys.add(key);
		}
		System.out.println("*************************************");
		for (String key : linkedInC.keySet()){
			ArrayList<String> temp = linkedInC.get(key);
			System.out.print(key + " ");
			System.out.println(Arrays.toString(temp.toArray()));
			listOfKeys.add(key);
		}
		System.out.println("*************************************");
	}
	
	//sets initial preferences- not satisfactory
	public static void generateStartState(){
		for (int i = 1;i<=numOfPairs;i++){
			currentState.put("C" + i, "P" + i);
		}
	}
	
	//prints the pairs of companies and programmers
	public static void printState(){
		for (int i = 1; i <= numOfPairs; i++){
			System.out.println("C" + i + " " + currentState.get("C" + i));
		}
		System.out.println("*************************************");
	}
	
	//gets the ranking of a company and programmer pair
	public static int getRank(String str1,String str2){
		if (str1.substring(0,1).equals("C")){
			ArrayList<String> tempP = linkedInC.get(str1);
			for (int i = 0;i<numOfPairs;i++){
				if (tempP.get(i).equals(str2)){
					return i+1;
				}
			}
		}
		
		if (str1.substring(0,1).equals("P")){
			ArrayList<String> tempC = linkedInP.get(str1);
			for (int j = 0;j<numOfPairs;j++){
				if (tempC.get(j).equals(str2)){
					return j+1;
				}
			}
		}
		return 0;
	}
	
	//checks if there is a more satisfactory pairing available
	public static boolean canSwap(String C1, String P1, String C2, String P2){
		int rankP1C1 = getRank(P1,C1);
		int rankC2P2 = getRank(C2,P2);
		int rankC2P1 = getRank(C2,P1);
		int rankP1C2 = getRank(P1,C2);
		
		if ((rankP1C2 < rankP1C1) && (rankC2P1 < rankC2P2)){
			return true;
		}
		return false;
	}
	
	//checks if pairing is satisfactory-basically the reverse of canSwap
	public static boolean isSatisfactory(String C1, String P1, String C2, String P2){
		int rankP1C1 = getRank(P1,C1);
		int rankC2P2 = getRank(C2,P2);
		int rankC2P1 = getRank(C2,P1);
		int rankP1C2 = getRank(P1,C2);
		
		if ((rankP1C2 < rankP1C1) && (rankC2P1 < rankC2P2)){
			return false;
		}
		return true;
	}
	
	//swaps elements
	public static void doSwap(String C1, String P1, String C2, String P2){
		currentState.replace(C1, P2);
		currentState.replace(C2, P1);
	}
	
	//the algorithm- calls the helpers on every key in each hashmap
	public static void match(){
		int counter = 0;
		
		for (int i = 1;i <=numOfPairs;i++){
			String Ci = "C" + i;
			for (int j = 1; j <= numOfPairs; j++){
				String Cj = "C" + j;
				if (canSwap(Ci,currentState.get(Ci),Cj,currentState.get(Cj))){
					doSwap(Ci,currentState.get(Ci),Cj,currentState.get(Cj));
					counter++;
				}
				if (canSwap(Cj,currentState.get(Cj),Ci,currentState.get(Ci))){
					doSwap(Cj,currentState.get(Cj),Ci,currentState.get(Ci));
					counter++;
				}
			}
		}
		if (counter != 0){
			match();
		}
	}
	
	//checks all pairs for satisfaction and prints the result
	public static void checkForSatisfaction(){
		int counter = 0;
		
		for (int i = 1;i <=numOfPairs;i++){
			String Ci = "C" + i;
			for (int j = 1; j <= numOfPairs; j++){
				String Cj = "C" + j;
				if (isSatisfactory(Ci,currentState.get(Ci),Cj,currentState.get(Cj))){
					counter++;
				}
				if (isSatisfactory(Cj,currentState.get(Cj),Ci,currentState.get(Ci))){
					counter++;
				}
			}
		}
		if (counter != 2 * numOfPairs*numOfPairs){
			System.out.println("There is unsatisfaction");
		}
		else 
			System.out.println("Satisfaction guaranteed");
	}

	//main
	public static void main(String [] args){
		for(int i = 0; i<3; i++){
			System.out.println("Testing " + (i+5) + " Companies and Programmers");
			generateHash(i+5);
			generateStartState();
			printHashMap();
			//printState();
			match();
			printState();
			checkForSatisfaction();
			System.out.println("*************************************");
		}
	}
}
