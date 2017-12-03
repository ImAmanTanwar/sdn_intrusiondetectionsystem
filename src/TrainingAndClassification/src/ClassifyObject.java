import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import weka.core.Attribute;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.ProtectedProperties;


public class ClassifyObject {
	public static void main(String[] args){
		
		Training training = new Training();
		Classifier cls = training.getClassifier(training);	
		Instances input = null;
		try {
			input = training.getInputData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String str_attack = "100,0,49,0,0,0,48,50,47,1,48,0,50,1,50";
		String str_nonattack = "0,0,100,26,0,0,66,66,65,3,0,0,0,0,66";
		String try1 = "1,0,99,24,0,11,45,53,36,9,1,0,0,3,51"; //non attack
		String try2 = "100,0,0,0,0,0,0,99,0,0,0,0,99,0,99"; //attack
        

		ClassifyObject obj = new ClassifyObject();
		obj.findOut(try2, cls, input);
	}

	public static boolean findOutForPython(String str){
		ClassifyObject obj = new ClassifyObject();
		double result = -99;
		try {
			String path = "C:/Users/Ritika/Documents/IIITB/SDN-NFV/Project/IntrusionDetectionSystem/src/j48.model";
			Classifier cls = (Classifier) weka.core.SerializationHelper.read(path);
			Instances input = Training.getInputData();
			result = obj.findOut(str, cls, input);
			if(result == 0.0){
				return true;
			}
			else if(result == 1.0){
				return false;
			}
			else{
				System.out.println("Unexpected result, check logs");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	obj.findOut(str, cls, input);

		return false;
		
		
	}
	
	 public double findOut(String str,Classifier cls, Instances input){
		 
		 List<String> attributeList = Arrays.asList(str.split(",")); 		 
		 Instance newInstance = new Instance(16);		 
		 
		 String[] classValues = {"TRUE", "FALSE"};
		// System.out.println("a is nominal "+ a.isNominal());
		
		 
		 Instances dataUnlabeled = new Instances(input,1);
		 System.out.println(dataUnlabeled.toString());
		 newInstance.setDataset(dataUnlabeled);
		
		 dataUnlabeled.add(newInstance);
		 dataUnlabeled.setClassIndex(dataUnlabeled.numAttributes() - 1); 
		 System.out.println("class attribute being "+dataUnlabeled.classAttribute());
		 System.out.println("class index "+dataUnlabeled.classIndex());
		 System.out.println("number of instances"+dataUnlabeled.numInstances());
		 
		 for(int i = 0 ; i < 15 ; i++)
		 {
			 int value = Integer.parseInt(attributeList.get(i));
			 System.out.print(value+" ");
			 newInstance.setValue(i , value);
		     //i is the index of attribute
		     //value is the value that you want to set
		 }
		 
		 double classified_index = -99;
		 try {
		//	 dataUnlabeled.classAttribute().value((int) clsLabel);
			classified_index = cls.classifyInstance(newInstance);
			System.out.println("classifed val"+ classified_index);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		return classified_index;
		 
	 }
}
