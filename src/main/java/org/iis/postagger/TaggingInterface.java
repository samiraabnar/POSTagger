package org.iis.postagger;

public abstract class TaggingInterface {

	/**
	 * @param args
	 */
	
	public static void test(String trainedModelFileName, String testDatasetFileName)
	{
		
	}
	
	
	public static void train(String datasetFileName)
	{
		
	}
	
	abstract public  String tagText(String text);
}
