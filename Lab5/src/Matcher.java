import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class Matcher extends Pair {

	public Matcher(String str1, String str2) {
		super(str1, str2);
		// TODO Auto-generated constructor stub
	}

	//static ArrayList<String><String> currentPairs = new LinkedHashMap<String, String>();

	public static LinkedHashMap<String,ArrayList<String>> linkedIn = new LinkedHashMap<String,ArrayList<String>>();
	public static LinkedHashMap<String,ArrayList<String>> linkedIn2 = new LinkedHashMap<String,ArrayList<String>>();

	public static void generateHash(int n){

		for (int i=1; i<=n;i++){
			ArrayList<String> temp = new ArrayList<String>(n);
			for (int j=1;j<=n;j++){
				String tempS = "C"+j;
				temp.add(tempS);
			}
			Collections.shuffle(temp);
			linkedIn.put("P" + i, temp);
		}
		for (int i=1; i<=n;i++){
			ArrayList<String> temp = new ArrayList<String>(n);
			for (int j=1;j<=n;j++){
				String tempS = "P"+j;
				temp.add(tempS);
			}
			Collections.shuffle(temp);
			linkedIn2.put("C" + i, temp);
		}

	}

	public static void printHashMap(){
		ArrayList<String> listOfKeys = new ArrayList<String>();

		for (String key : linkedIn.keySet()){
			ArrayList<String> temp = linkedIn.get(key);
			System.out.print(key + " ");
			System.out.println(Arrays.toString(temp.toArray()));
			listOfKeys.add(key);
		}
		for (String key : linkedIn2.keySet()){
			ArrayList<String> temp = linkedIn2.get(key);
			System.out.print(key + " ");
			System.out.println(Arrays.toString(temp.toArray()));
			listOfKeys.add(key);
		}

		//System.out.println(Arrays.toString(listOfKeys.toArray()));
	}


	public static LinkedHashMap<String, String> generateState(int num){
		int numOfPairs = num;
		//ArrayList<String> listOfKeys = new ArrayList<String>(numOfPairs);
		LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
		int counter = 1;
		/*for (String key : hash.keySet()){
			ArrayList<String> temp = hash.get(key);
			currentPairs.put(key, temp.get(0));
			System.out.println(key + " " + temp.get(0));
		}*/
		//while (counter != numOfPairs * 2){
		for (int i = 1; i <= numOfPairs; i++){
			ArrayList<String> tempP = linkedIn.get("P" + i);
			for (int j = 1; j <= numOfPairs; j++){
				ArrayList<String> tempC = linkedIn2.get("C" + j);
				if ((tempC.get(0).equals("P" + i)) && (tempP.get(0).equals("C" + j)) && 
						!result.containsKey("C" + j) && !result.containsValue("P"+i)){
					result.put("C" + i, tempC.get(0));
					//counter++;
					System.out.println("C" + j + ", " + tempC.get(0));
					for (int k = 1; k < numOfPairs; k++){
						for (int m = 1; m < numOfPairs; m++){
							if (linkedIn.get("P"+ k).get(0).equals(tempP.get(0))){
								String remove = linkedIn.get("P"+ k).get(0);
								linkedIn.get("P"+ k).set(m-1, linkedIn.get("P"+ k).get(m));
								linkedIn.get("P"+ k).set(m, remove);
							}
							if (linkedIn2.get("C" + k).get(0).equals(tempC.get(0))){
								String remove = linkedIn2.get("C" + k).get(0);
								linkedIn2.get("C" + k).set(m-1, linkedIn2.get("C" + k).get(m));
								linkedIn2.get("C" + k).set(m, remove);
							}
						}
					}
				}

			}	

		}

		return result;
	}


	public static void main(String [] args){
		generateHash(5);
		printHashMap();
		generateState(5);
		printHashMap();
	}
}
