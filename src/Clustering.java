import java.util.*;
public class Clustering {
	public ArrayList<List<Integer>> form_clusters(double[][] tf_idf, int k, int sim_measure) {
		
		int rows = tf_idf[0].length;
		//Form list of clusters (each cluster will have a list of document #'s)
		int step=0;
		ArrayList<List<Integer>> clusters = new ArrayList<>();
		double[][] means = new double[k][rows];
		
		//Add cluster indices into clusters
		//copy tf_idf into means
		for(int i = 0; i < k; i++)
		{
			ArrayList<Integer> clusterIndex = new ArrayList<>();
			means[i] = tf_idf[i];
			clusters.add(clusterIndex);
		}
		
		double[][] means_duplicate = new double[k][rows];
		
		for(int i = 0; i < k; i++) {
		
			means_duplicate[i] = tf_idf[i];
		}
		
		while(true) {
		
			//Insert individual documentd into clusters through euclidean/cosine similarity distancing
			step++;
			for(int j = 0; j <tf_idf.length; j++) {
			
				double min = Integer.MAX_VALUE;
				int minIndex = 0;
				for(int i = 0; i < means.length; i++) {
				
					if(sim_measure==0) {
					
						if(get_cosine_distance(means[i],tf_idf[j]) < min) {
							
							min = get_cosine_distance(means[i],tf_idf[j]);
							minIndex = i;
						}
					}
					else {
					
						if(get_euclidean_distance(means[i],tf_idf[j]) < min) {
				    		min = get_euclidean_distance(means[i],tf_idf[j]);
							minIndex = i;
						}
					}
				}
				clusters.get(minIndex).add(j);	
			}
			
			
			for(int i = 0 ; i < clusters.size(); i++) {
				means[i] = means(clusters.get(i),tf_idf);
			}
			
			
			if(compare_matrices(means, means_duplicate) || step==1000)
			{
				break;
			}				
			means_duplicate = means;	
			
			//reset before next iteration of while loop
			clusters = new ArrayList<>();
			for(int i = 0; i < k; i++)
			{
				ArrayList<Integer> clusterIndex = new ArrayList<>();
				clusters.add(clusterIndex);
			}
			
		}
		return clusters;				
	}
	
	//Retrieve means given indices of clusters and the tf_idf matrix
	public double [] means(List<Integer> list, double [][] tf_idf) {
		int len = tf_idf[0].length;
		double [] ans = new double[len];
		
		for (int i = 0; i < list.size(); i++) {
			int index = list.get(i);
			for (int j = 0; j < len; j++) {
				ans[j] += tf_idf[index][j];
			}
		}
		for (int a = 0; a < len; a++) {
			ans[a] = ans[a] / list.size();
		}
		return ans;
	}
	
	public boolean compare_matrices(double [][] arr_one, double [][] arr_two) {
		for (int i = 0; i < arr_one.length; i++) {
			for (int j = 0; j < arr_one[0].length; j++) {
				if (arr_one[i][j] != arr_two[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	public double get_euclidean_distance(double [] arr_one, double [] arr_two) {
		double ans = 0.0;
		for (int i = 0; i < arr_one.length; i++) {
			double difference = arr_one[i] - arr_two[i];
			ans += (difference * difference);
		}
		ans = Math.sqrt(ans);
		return ans;
	}
	
	public double get_cosine_distance(double [] arr_one, double [] arr_two) {
		double a_one = 0.0;
		double a_two = 0.0;
		double dot = 0.0;
		
		
		for (int i = 0; i < arr_one.length; i++) {
			a_one += arr_one[i] * arr_one[i];
			a_two += arr_two[i] * arr_two[i];
			dot += arr_one[i] * arr_two[i];
		}
		double ans = dot / (Math.sqrt(a_one) * Math.sqrt(a_two));
		return ans;
	}
	
}
