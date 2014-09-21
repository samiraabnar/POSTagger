package org.iis.postagger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HunPOSTagger {

	public static final String taggers_folder = "";
	private static final String dir = "/Users/MacBookPro/Documents/Uni-MS/FinalProject/Code/POSTagger";
	private static Map<String, String> MODELS = new HashMap<String, String>();

	static {
		MODELS.put("FA", "model_TagPer");
		MODELS.put("EN", "en_wsj.model");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		tagSentence("سلام من سمیرا هستم!", "FA");
	}

	// hunpos-tag model_TagPer < input_file.txt > output_file.txt
	public static List<TaggedWord> tagSentence(String sentence, String lang) {
		sentence = sentence.replaceAll("([:,;'`؛،!‍..])", " $1 ");
		String[] tokens = sentence.split("[\\s]+");
		List<String> tokenizedSentence = Arrays.asList(tokens);
		return tagSentence(tokenizedSentence, lang);
	}

	public static List<TaggedWord> tagSentence(String sentence) {
		sentence = sentence.replaceAll("([:,;'`؛،!‍..])", " $1 ");
		String[] tokens = sentence.split("[\\s]+");
		List<String> tokenizedSentence = Arrays.asList(tokens);
		return tagSentence(tokenizedSentence);
	}

	public static List<TaggedWord> tagSentence(List<String> sentence) {
		try {
			PrintWriter tmpWriter = new PrintWriter("HunTagger_Input.txt",
					"UTF-8");
			for (String token : sentence) {
				tmpWriter.write(token + "\n");
			}
			tmpWriter.close();

			Runtime rt = Runtime.getRuntime();
			String[] params = { "/bin/sh", "-c", "cd " + dir };
			Process proc1 = rt.exec(params);// > HunTagger_Output.txt");
			int exitVal = proc1.waitFor();
			// System.out.println("Exited with error code "+exitVal);
			String[] paramsArray = { "/bin/sh", "-c",
					"./hunpos-tag model_TagPer < HunTagger_Input.txt > HunTagger_Output.txt" };
			Process proc = rt.exec(paramsArray);// > HunTagger_Output.txt");
			// BufferedReader input = new BufferedReader(new
			// InputStreamReader(proc.getInputStream()));
			BufferedReader input = new BufferedReader(new InputStreamReader(
					proc.getErrorStream()));

			String lline = null;

			while ((lline = input.readLine()) != null) {
				// System.out.println(lline);
			}

			exitVal = proc.waitFor();
			// System.out.println("Exited with error code "+exitVal);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream("HunTagger_Output.txt"), "utf-8"));

			String line = "";
			List<TaggedWord> taggedSentence = new ArrayList<TaggedWord>();
			while ((line = br.readLine()) != null) {
				if (line.length() > 1) {
					String[] parts = line.split("\\s+");
					taggedSentence.add(new TaggedWord(parts[0], parts[1]));
					// System.err.println(parts[0] + "\n" + parts[1] +
					// "\n ####");

				}
			}
			br.close();
			File file = new File("HunTagger_Output.txt");
			if (file.exists()) {
				file.delete();
			}
			file = new File("HunTagger_Input.txt");
			if (file.exists()) {
				file.delete();
			}

			return taggedSentence;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static List<TaggedWord> tagSentence(List<String> sentence,
			String lang) {
		try {
			PrintWriter tmpWriter = new PrintWriter("HunTagger_Input.txt",
					"UTF-8");
			for (String token : sentence) {
				tmpWriter.write(token + "\n");
			}
			tmpWriter.close();

			Runtime rt = Runtime.getRuntime();
			String[] params = { "/bin/sh", "-c", "cd " + dir };
			Process proc1 = rt.exec(params);// > HunTagger_Output.txt");
			int exitVal = proc1.waitFor();
			// System.out.println("Exited with error code "+exitVal);
			String[] paramsArray = {
					"/bin/sh",
					"-c",
					"./hunpos-tag " + MODELS.get(lang)
							+ " < HunTagger_Input.txt > HunTagger_Output.txt" };
			Process proc = rt.exec(paramsArray);// > HunTagger_Output.txt");
			// BufferedReader input = new BufferedReader(new
			// InputStreamReader(proc.getInputStream()));
			BufferedReader input = new BufferedReader(new InputStreamReader(
					proc.getErrorStream()));

			String lline = null;

			while ((lline = input.readLine()) != null) {
				// System.out.println(lline);
			}

			exitVal = proc.waitFor();
			// System.out.println("Exited with error code "+exitVal);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream("HunTagger_Output.txt"), "utf-8"));

			String line = "";
			List<TaggedWord> taggedSentence = new ArrayList<TaggedWord>();
			while ((line = br.readLine()) != null) {
				if (line.length() > 1) {
					String[] parts = line.split("\\s+");
					taggedSentence.add(new TaggedWord(parts[0], parts[1]));
					// System.err.println(parts[0] + "\n" + parts[1] +
					// "\n ####");
				}
			}
			br.close();
			File file = new File("HunTagger_Output.txt");
			if (file.exists()) {
				file.delete();
			}
			file = new File("HunTagger_Input.txt");
			if (file.exists()) {
				file.delete();
			}

			return taggedSentence;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return null;
	}
}
