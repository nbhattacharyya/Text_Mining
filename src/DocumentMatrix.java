import java.util.*;
public class DocumentMatrix {
	
	//Stores every single phrase (unique only) across all documents
	ArrayList<String> unique = new ArrayList<String>();
	
	//Forms the matrix through iterating over every Document's frequency map
	public int [][]  generate_matrix(ArrayList<HashMap<String, Integer>> listmap, Set<String> phrases) {
		int[][] ans = new int[listmap.size()][phrases.size()];
		for (int i = 0; i < listmap.size(); i++) {
			int j = 0;
			HashMap<String, Integer> map = listmap.get(i);
			for (String s : phrases) {
				//Add phrases into unique
				unique.add(s);
				if (map.containsKey(s)) {
					ans[i][j] = map.get(s);
				}
				else {
					ans[i][j] = 0;
				}
				j++;
			}
		}

		return ans;
		
	}
	//Returns a single map with frequency of phrases (called per document)
	public HashMap<String, Integer> getFreq(List<String> list, HashSet<String> all_words, HashMap<String, Integer> map) {
		HashMap<String, Integer> ans = new HashMap<String, Integer>();
		for (String s : list) {
			if (!all_words.contains(s)) {
				all_words.add(s);
			}
		}
		
		for (String s : list) {
			if (ans.containsKey(s)) {
				ans.put(s, ans.get(s)+1);
			}
			else {
				ans.put(s, 1);
			}
		}
		
		return ans;
	}
	
	//Simple calculation of tf_idf
	public double[][] generate_tf_idf_matrix(int[][] doc_matrix) {
		
		//Initialize variables for calculation
		double tf=0.0;
		double idf=0.0;
		int i=0;
		int j=0;
		int len = doc_matrix.length;
		double[] idf_matrix = new double[doc_matrix[0].length];
	    double[][] tf_idf = new double[doc_matrix.length][doc_matrix[0].length];
	    int counter;
		int sum;

		
		for(j = 0; j < doc_matrix[0].length; j++) {
		
			counter=0;
			for(i=0; i < doc_matrix.length; i++) {
			
				if(doc_matrix[i][j] != 0) {
				
					counter++;
				}
			}
			idf_matrix[j] = counter;
		}
		
		for(i = 0; i < doc_matrix.length; i++) {		
			sum=0;
			for(j=0; j < doc_matrix[0].length; j++) {
			
			   sum = sum + doc_matrix[i][j]; 
			}
			for(j = 0; j < doc_matrix[0].length; j++) {
			
				 tf = (double)doc_matrix[i][j] / sum;
				 idf = Math.log(len/ idf_matrix[j]);
				 tf_idf[i][j] = tf*idf;
			}
		}
		
		return tf_idf;
	}
	
	//Generate keywords given tf_idf matrix
	public ArrayList<String> produce_keywords(double [][] tf_idf) {
		double[][] temp_matrix = new double[3][tf_idf[0].length];
		int one_third = 8;
		int two_third = 16;
		int num_docs = 24;
		
		//Create temporary matrix with 3 rows (1 per cluster)
		for(int j = 0;j < tf_idf[0].length; j++) {
		
			double sum=0.0;
			for(int i = 0; i < one_third; i++) {
			
				if (!Double.isNaN(tf_idf[i][j])) {
					sum = sum + tf_idf[i][j];
				}
			}
			temp_matrix[0][j] = sum;
			sum = 0.0;
			
			for(int i = one_third; i < two_third; i++) {
			
				if (!Double.isNaN(tf_idf[i][j])) {
					sum = sum + tf_idf[i][j];
				}			
			}
			temp_matrix[1][j] = sum;
			sum = 0.0;
			
			for(int i = two_third ; i < num_docs; i++) {
			
				if (!Double.isNaN(tf_idf[i][j])) {
					sum = sum + tf_idf[i][j];
				}			
			}
			temp_matrix[2][j] = sum;
		}
		
		//populate keywords given unique list
		ArrayList<String> keyWords = new ArrayList<>();
		String keyWord = "";
		
		for(double[] arr : temp_matrix) {
		
			double max = Double.MIN_VALUE;
			for(int i = 0; i < arr.length; i++) {
			
				if(arr[i] > max) {
				
					max = arr[i];
					keyWord = unique.get(i);
				}
			}
			keyWords.add(keyWord);
		}
		
		return keyWords;
		
	}
}