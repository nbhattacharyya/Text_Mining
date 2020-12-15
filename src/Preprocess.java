import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
//package Clustering.Clustering;

import edu.stanford.nlp.coref.CorefCoreAnnotations;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.io.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.util.*;
import edu.stanford.nlp.simple.*;
import edu.stanford.nlp.simple.Document;




//import edu.stanford.nlp.simple.*;

public class Preprocess {
	public List<String> readFile(HashSet<String> stop_words, File text_data) {

		Scanner input = null;
		try {
			input = new Scanner(text_data);
		}
		catch (FileNotFoundException fnf) {
			fnf.printStackTrace();
		}
		
		List<String> ans = new ArrayList<String>();

		while (input.hasNextLine()) {
			//Remove stop words
			String filtered = remove_stop_words(input.nextLine(), stop_words);
			//System.out.println(filtered);
			StringBuilder s = new StringBuilder();
			String [] arr = filtered.split(",");
			for (String f : arr) {
				s.append(f);
			}
			String next_final = s.toString();
			String temp = "";
			if (next_final.length() != 0) {
				temp = next_final.substring(1, next_final.length()-1);
			}
			ans.add(tokenizing(temp));
		}
		return ans;
	}
	public HashSet<String> retrieve_stop_words() {
		HashSet<String> stopWords = new HashSet<>();
		File file = new File("stop_words.txt");
		Scanner input = null;
		try {
			input = new Scanner(file);
		}
		catch (FileNotFoundException fnf) {
			fnf.printStackTrace();
		}
		while (input.hasNextLine()) {
			String s = input.nextLine();
			stopWords.add(s);
		}
		return stopWords;
	}
	
	public String remove_stop_words(String line, HashSet<String> stopWords) {
		List<String> l = new ArrayList<String>();
		for (String sentence : line.split(" ")) {
			sentence = sentence.replaceAll("[^a-zA-Z0-9]", "");
			if (!stopWords.contains(sentence) && sentence.length() != 0) {
				l.add(sentence);
			}
		}
		return l.toString();
	}
	
	public String tokenizing(String line) {
		Document d = new Document(line);
		
		// populate lemmas, ner
		List<String> ans = new ArrayList<String>();
		
		List<String> ners = new ArrayList<String>();
		List<String> all_lemmas = new ArrayList<String>();
		for (Sentence s : d.sentences()) {
			String lemma = String.join(",", s.lemmas());
			all_lemmas.add(lemma);
			String ner = String.join(",", (new Sentence(s.lemmas()).nerTags()));
			ners.add(ner);
		}
		
		//Now we do ngrams in same function
		//Both lists have same size
		int list_counter = 0;
		for (int i = 0; i < all_lemmas.size(); i++) {
			String words = all_lemmas.get(i);
			String ner = ners.get(i);
			String [] words_split = words.split(",");
			String [] ner_split = ner.split(",");
			for (int j = 0; j < ner_split.length-1; j++) {
				if (!ner_split[j].equals("O") && !ner_split[j+1].equals("O")) {
					if (ner_split[j].equals(ner_split[j+1])) {
						//System.out.println(words_split[j] + " " + words_split[j+1]);
						ans.add(list_counter, words_split[j]+" "+words_split[j+1]);
						list_counter++;
					}
				}
			}
		}
		return String.join(",", ans);
	}
	public static void ngram(List<List<String>> list) {
		HashMap<String, Integer> ngrams = new HashMap<String, Integer>();
		System.out.println(list.get(0).size());
		System.out.println(list.get(1).size());

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//System.out.println(list);
		/*HashMap<String, Integer> ngrams = new HashMap<String, Integer>();
		for (int i = 0; i < list.size()-1; i++) {
			String first = list.get(i);
			String second = list.get(i+1);
			if (!first.equals("O") && !first.equals("O,") && !second.equals("O") && !second.equals("O,")) {
				String temp = list.get(i)+" "+list.get(i+1);
				temp = temp.replaceAll(",", "");
				//System.out.println(temp);
				if (ngrams.containsKey(temp)) {
					ngrams.replace(temp, ngrams.get(temp)+1);
				}
				else {
					ngrams.put(temp, 1);
				}
			}
		}
		for (String k : ngrams.keySet()) {
			String [] split = k.split(" ");
			if (split[0].equals(split[1])) {
				//System.out.println(k);
				for (int i = 0; i < list.size()-1; i++) {
					String val = list.get(i)+" "+list.get(i+1);
					val = val.replaceAll(",", "");
					//String [] split_val = val.split(" ");
					if (ngrams.get(k) > 1 && (val.equals(k))) {
						//System.out.println(k);
						list.set(i, k);
						list.set(i+1, "void");
					}
				}
			}
		}
		Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			if (it.next().equals("void")) {
				it.remove();
			}
		}*/
	}
	
}
