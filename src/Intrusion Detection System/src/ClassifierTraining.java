//import weka.classifiers.evaluation.output.prediction.CSV;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.core.converters.CSVSaver;

//import weka.core.converters.ConverterUtils.DataSource;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
//import java.io.FileNotFoundException;
//import java.io.FileReader;

//import weka.core.converters.ConverterUtils;
public class ClassifierTraining {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = new File("C:/Users/Ritika/Documents/IIITB/SDN-NFV/tcp_log_171435_309117.csv");
		File testfile = new File("C:/Users/Ritika/Documents/IIITB/SDN-NFV/tcp_log_171435_309117.csv");
		File unlabelledfile = new File("C:/Users/Ritika/Documents/IIITB/SDN-NFV/testtcp.csv");
		//change it to the running instances, we write them to a csv file and then test
		try {
			CSVLoader loader = new CSVLoader();
			loader.setFile(file);
		//	CSVLoader loader2 = new CSVLoader();
		//	loader2.setFile(testfile);
			Instances train = loader.getDataSet();
		//	Instances test = loader2.getDataSet();
			CSVLoader loader3 = new CSVLoader();
			loader3.setFile(unlabelledfile);
			Instances unlabelled = loader3.getDataSet();
			if (train.classIndex() == -1){
				   train.setClassIndex(train.numAttributes() - 1);
			}
//			if (test.classIndex() == -1){
//				   test.setClassIndex(test.numAttributes() - 1);
//			}
			if(unlabelled.classIndex() == -1){
				unlabelled.setClassIndex(unlabelled.numAttributes()-1);
			}
			//creating a copy
			Instances labelled = new Instances(unlabelled);
			Classifier tree = new J48();
			tree.buildClassifier(train);
			for (int i = 0; i < unlabelled.numInstances(); i++) {	
				System.out.println(unlabelled.instance(i));
				   double clsLabel = tree.classifyInstance(unlabelled.instance(i));
				   labelled.instance(i).setClassValue(clsLabel);
				 }
				 // save labeled data
			CSVSaver saver = new CSVSaver();
			BufferedWriter writer = saver.getWriter();
			
			 writer = new BufferedWriter(
				                           new FileWriter("C:/Users/Ritika/Documents/IIITB/SDN-NFV/testtcp.csv"));
				 writer.write(labelled.toString());
				 writer.newLine();
				 writer.flush();
				 writer.close();
				
			
			// Classifier cls = new J48();
			// cls.buildClassifier(train);
			 Evaluation eval = new Evaluation(train);
			 eval.evaluateModel(tree, unlabelled);
			 System.out.println(eval.toSummaryString("\nResults\n======\n", false));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
