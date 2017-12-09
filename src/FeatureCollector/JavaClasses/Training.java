//import weka.classifiers.evaluation.output.prediction.CSV;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Debug.Random;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.core.converters.CSVSaver;




//import weka.core.converters.ConverterUtils.DataSource;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


import java.io.IOException;

//import java.io.FileNotFoundException;
//import java.io.FileReader;
import weka.filters.supervised.instance.StratifiedRemoveFolds;
//import weka.core.converters.ConverterUtils;
public class Training {

	public static void main(String[] args) {
		Training training = new Training();
		File input_file = new File("MergedFile.csv");
		Classifier cls = training.train(input_file);
	}
	public Classifier train(File input_file){
		// TODO Auto-generated method stub

	    int folds = 10;
	    int seed = 1;
	    Classifier cls = new J48(); // Instantiate the classifier c4.5 in our case - developed on ID3
		try {
			CSVLoader loader = new CSVLoader();
			loader.setFile(input_file);
		//	CSVLoader loader2 = new CSVLoader();
		//	loader2.setFile(testfile);
			Instances input = loader.getDataSet(); // creating dataset from csv

			//Setting Class Attribute
			if (input.classIndex() == -1){
				   input.setClassIndex(input.numAttributes() - 1);
			}
			//Randomize the rows - Shuffling


			Random rand = new Random(1);   // create seeded number generator
			Instances randData = new Instances(input);   // create copy of original data
			randData.randomize(rand);         // randomize data with number generator
			if (randData.classAttribute().isNominal()){
				randData.stratify(10); // for startified cross validation
			 }
		//	Classifier cls = new J48(); // Instantiate the classifier c4.5 in our case - developed on ID3
			Evaluation eval = new Evaluation(randData);
			Classifier clsCopy = Classifier.makeCopy(cls);
			for (int n = 0; n < folds; n++) {
			      Instances train = randData.trainCV(folds, n);
			      Instances test = randData.testCV(folds, n);

			      // build and evaluate classifier
			      clsCopy = Classifier.makeCopy(cls);
			      clsCopy.buildClassifier(train);
			      eval.evaluateModel(clsCopy, test);
			    }


                cls = Classifier.makeCopy(clsCopy);
			    // output evaluation
			    System.out.println();
			    System.out.println("=== Setup ===");
			    System.out.println("Classifier: " + cls.getClass().getName());
			    System.out.println("Dataset: " + input.relationName());
			    System.out.println("Folds: " + folds);
			    System.out.println("Seed: " + seed);
			    System.out.println();
			    System.out.println("here "+eval.toSummaryString("=== " + folds + "-fold Cross-validation ===", false));
			    String path = "../j48.model";
			    weka.core.SerializationHelper.write(path, cls);
		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			return cls;
		}

	}
	public Classifier getClassifier(Training training){

		File input_file = new File("MergedFile.csv");
		Classifier cls = training.train(input_file);
		return cls;

	}

	public  static Instances getInputData() throws IOException{
		File input_file = new File("MergedFile.csv");
		CSVLoader loader = new CSVLoader();
		loader.setFile(input_file);
		Instances input = loader.getDataSet(); // creating dataset from csv
		return input;

	}


}
