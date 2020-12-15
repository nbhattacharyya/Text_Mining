import java.io.*;
import java.util.*;
import java.util.Map.Entry;
public class Main {
	public static void main(String[] args) {
		
		File data_folder = new File("data");
		String inputPath = data_folder.toString();
		
		// Get list of folders (C1, C4. C7)
		ArrayList<String> files = getFiles(inputPath);
		
		//Instantiate classes and get stop words
		Preprocess pre = new Preprocess();
		HashSet<String> stop_words = pre.retrieve_stop_words();
		DocumentMatrix dm = new DocumentMatrix();
		
		//Preprocess each text document
		//Put a list of maps per document containing frequencies per phrase after preprocessing
		HashSet<String> all_words = new HashSet<String>();
		HashMap<String, Integer> frequency;
		ArrayList<HashMap<String, Integer>> list_map = new ArrayList<HashMap<String, Integer>>();
		for (String file_name : files) {
			File file = new File(file_name);
			frequency = new HashMap<String, Integer>();
			List<String> preprocessed = pre.readFile(stop_words, file);
			HashMap<String, Integer> m = dm.getFreq(preprocessed, all_words, frequency);
			list_map.add(m);
		} 
		

		//Create document matrix given all frequencies and phrases
		System.out.println("Document matrix:");
		int [][] document_matrix = dm.generate_matrix(list_map, all_words);
		for (int [] row : document_matrix) {
			System.out.println(Arrays.toString(row));
		}
		System.out.println();
		System.out.println("TF-IDF Matrix: ");
		//Create tf_idf doc matrix
		double [][] tf_idf = dm.generate_tf_idf_matrix(document_matrix);
		for (double [] d : tf_idf) {
			System.out.println(Arrays.toString(d));
		}
		
		System.out.println();
		System.out.println();
		
		//Generate new doc_matrix for convenience of clusteting
		double [][] double_doc_matrix = new double[document_matrix.length][document_matrix[0].length];
		for (int r = 0; r < document_matrix.length; r++) {
			for (int c = 0; c < document_matrix[0].length; c++) {
				double_doc_matrix[r][c] = (double) document_matrix[r][c] * 1000;
			}
		}
		
		//Generate keywords
		ArrayList<String> keywords = dm.produce_keywords(tf_idf);
		System.out.println("Keywords per cluster:");
		for (int i = 0; i < keywords.size(); i++) {
			System.out.println("The keywords for cluster " + i + " are: " + keywords.get(i));
		}
		System.out.println();
		
		//SVD
		SVD svd = new SVD();
		double [][] svd_components = svd.svd(tf_idf, 2);
		System.out.println("SVD Matrix:");
		for (double [] row : svd_components) {
			System.out.println(Arrays.toString(row));
		}
		System.out.println();
		
		//k-means and clustering
		Clustering clusters = new Clustering();
		int cosine = 0;
		int euclidean = 1;
		int cluster_length = 3;
		
		ArrayList<List<Integer>> list_of_clusters = clusters.form_clusters(double_doc_matrix, cluster_length, euclidean);
		System.out.println("Cluster Prediction: ");
		for (List<Integer> l : list_of_clusters) {
			System.out.println(l);
		}
		
		System.out.println();
		
		//Cluster performance + precisions, recalls, and F1 scores
		ClusterPerformance cp = new ClusterPerformance();
		cp.generate_confusion_matrix(list_of_clusters);
		ArrayList<Double> precisions = cp.generate_precisions(cp.c1_tp, cp.c1_fp, cp.c4_tp, cp.c4_fp, cp.c7_tp, cp.c7_fp);
		ArrayList<Double> recalls = cp.generate_recalls(cp.c1_tp, cp.c4_tp, cp.c7_tp);
		ArrayList<Double> F1_scores = cp.generate_F1_scores(precisions, recalls);


		System.out.println("Precisions:");
		for (Double d : precisions) {
			System.out.println(d);
		}
		
		System.out.println();
		
		System.out.println("Recalls");
		for (Double d : recalls) {
			System.out.println(d);
		}
		
		System.out.println();
		
		System.out.println("F1 Scores:");
		for (double score : F1_scores) {
			System.out.println(score);
		}
		
		//Visualization
		Visualization v = new Visualization(tf_idf);
		v.setVisible(true);
		
		
	}
	//Put all files in a list
	public static ArrayList<String> getFiles(String input) {
		ArrayList<String> files = new ArrayList<String>();
		File file = new File(input);
		File [] sub_folders = file.listFiles();
		for (int i = 1; i < sub_folders.length; i++) {
			File [] text_files = sub_folders[i].listFiles();
			for (int j = 0; j < text_files.length; j++) {
				files.add(text_files[j].toString());
			}
		}
		return files;
	}
	
}
