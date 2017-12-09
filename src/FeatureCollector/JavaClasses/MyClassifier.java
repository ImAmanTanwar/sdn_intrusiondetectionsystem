import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.core.converters.CSVSaver;


public class MyClassifier {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyClassifier classifier = new MyClassifier();
		Training training = new Training();
		Classifier cls = training.getClassifier(training);
		File unlabelled_file = new File("C:/Users/Ritika/Documents/IIITB/SDN-NFV/ProjectFiles/attacktest.csv");	
		String str = "100,0,0,0,0,0,0,99,0,0,0,0,99,0,99";
		List<String> attributeList = Arrays.asList(str.split(","));
		try {
			Instances input = training.getInputData();
			classifier.findOut(unlabelled_file, cls, input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 public void findOut(File unlabelled_file,Classifier cls, Instances input){
		
		
		CSVLoader loader = new CSVLoader();
		try {
			loader.setFile(unlabelled_file);
			Instances unlabelled = loader.getDataSet(); // creating dataset from csv
			Instances labelled = new Instances(unlabelled); // creating a copy where I will put labels
			System.out.println(unlabelled.numAttributes());
			if (unlabelled.classIndex() == -1){
				   unlabelled.setClassIndex(input.numAttributes()-1);
			}
			if (labelled.classIndex() == -1){
				   labelled.setClassIndex(input.numAttributes()-1);
			}
			System.out.println(labelled.classIndex());
			System.out.println(unlabelled.classIndex());
			System.out.println(cls.toString());
			for (int i = 0; i < unlabelled.numInstances(); i++) {	
		//		System.out.println(unlabelled.instance(i));
				double clsLabel;
				try {
					 double[] prediction= cls.distributionForInstance(unlabelled.instance(i));
					 
				///	double predictionIndex = 
					//	    cls.classifyInstance(unlabelled.instance(i)); 
					clsLabel = cls.classifyInstance(unlabelled.instance(i));
					
					System.out.println(clsLabel);
//					String predictedClassLabel =
//						    unlabelled.classAttribute().value((int) predictionIndex);
					

				//	labelled.instance(i).setClassValue(predictedClassLabel);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
				 // save labeled data
//			CSVSaver saver = new CSVSaver();
//			BufferedWriter writer = saver.getWriter();
//			File labelled_file = new File("C:/Users/Ritika/Documents/IIITB/SDN-NFV/ProjectFiles/labelled.csv");
//			 writer = new BufferedWriter(
//				                           new FileWriter(labelled_file));
//				 writer.write(labelled.toString());
//				 writer.newLine();
//				 writer.flush();
//				 writer.close();
				
			
			// Classifier cls = new J48();
			// cls.buildClassifier(train);
//			 Evaluation eval = new Evaluation(unla);
//			 eval.evaluateModel(cls, unlabelled);
//			 System.out.println(eval.toSummaryString("\nResults\n======\n", false));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
