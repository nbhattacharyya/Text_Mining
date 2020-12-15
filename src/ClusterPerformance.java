import java.util.*;
public class ClusterPerformance {
	int c1_tp = 0;
	int c1_fp = 0;
	
	int c4_tp = 0;
	int c4_fp = 0;
	
	int c7_tp = 0;
	int c7_fp = 0;
	
	public void generate_confusion_matrix(ArrayList<List<Integer>> clusters)
	{
		List<Integer> c1 = new ArrayList<>();
		List<Integer> c4 = new ArrayList<>();
		List<Integer> c7 = new ArrayList<>();
	
		ArrayList<List<Integer>> temp_clusters = new ArrayList<>();

		for(int i=0;i<24;i=i+3) {
			
			c1.add(i);
			c4.add(i+1);
			c7.add(i+2);
		}
		
		temp_clusters.add(c1);
		temp_clusters.add(c4);
		temp_clusters.add(c7);
			
		List<Integer> cluster_one = new ArrayList<>();
		List<Integer> cluster_four = new ArrayList<>();
		List<Integer> cluster_seven = new ArrayList<>();
	
		for(List<Integer> cluster:clusters) {
			int distribution[] = new  int[3];
			
			
			for(int i = 0; i < cluster.size(); i++) {
				if(temp_clusters.get(0).contains(cluster.get(i))) {
					distribution[0] = distribution[0] + 1;
				}
				else if(temp_clusters.get(1).contains(cluster.get(i))) {
					distribution[1] = distribution[1] + 1;
				}
				else {
					distribution[2] = distribution[2] + 1;
				}
			}
			
			int temp = Integer.MIN_VALUE;
			int max = 0;
			for(int i = 0; i < distribution.length; i++) {
				if(distribution[i] > temp) {
					temp = distribution[i];
					max = i;
				}
			}
			
			if(max == 0) {
			
				cluster_one = cluster;
			}
			else if(max == 1) {
			 
				cluster_four = cluster;
			}
			else {
			
				cluster_seven = cluster;
			}
		}
		
		//Increment true and false positives based on matching
		for(int i : cluster_one)
		{
			if(c1.contains(i))
			{
				c1_tp++;
			}
			else
			{
				 c1_fp++;
			}
		}
		
		for(int i : cluster_four)
		{
			if(c4.contains(i))
			{
				c4_tp++;
			}
			else
			{
				c4_fp++;
			}
		}
		
	
		for(int i : cluster_seven)
		{
			if(c7.contains(i))
			{
				c7_tp++;
			}
			else
			{
				c7_fp++;
			}
		}
	}
	
	public ArrayList<Double> generate_precisions(int c1_tp, int c1_fp, int c4_tp, int c4_fp, int c7_tp, int c7_fp)
	{
		ArrayList<Double> precisions = new ArrayList<>();

		precisions.add((c1_tp)/(double)(c1_tp + c1_fp));
		precisions.add((c4_tp)/(double)(c4_tp + c4_fp));
		precisions.add((c7_tp)/(double)(c7_tp + c7_fp));
		
		return precisions;
	}
	
	public ArrayList<Double> generate_recalls(int c1_tp, int c4_tp, int c7_tp)
	{
        ArrayList<Double> recalls = new ArrayList<>();
		
		recalls.add((double)(c1_tp) / 8);
		recalls.add((double)(c4_tp) / 8);
		recalls.add((double)(c7_tp) / 8);
		
		return recalls;
	}
	
	public ArrayList<Double> generate_F1_scores(ArrayList<Double> precisions, ArrayList<Double> recalls) {
	
		 ArrayList<Double> ans = new ArrayList<>();
						
			ans.add(precisions.get(0) * recalls.get(1) / (precisions.get(0) + recalls.get(0)));
			ans.add(precisions.get(1) * recalls.get(1) / (precisions.get(1) + recalls.get(1)));
			ans.add(precisions.get(2) * recalls.get(2) / (precisions.get(2) + recalls.get(2)));
			
			return ans;
	}
}
