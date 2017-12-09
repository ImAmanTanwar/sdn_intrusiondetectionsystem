
public class Checking {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str_attack = "100,0,49,0,0,0,48,50,47,1,48,0,50,1,50";
		String str_nonattack = "0,0,100,26,0,0,66,66,65,3,0,0,0,0,66";
		String try1 = "1,0,99,24,0,11,45,53,36,9,1,0,0,3,51"; //non attack
		String try2 = "100,0,0,0,0,0,0,99,0,0,0,0,99,0,99"; //attack
        
		boolean result = ClassifyObject.findOutForPython(str_attack);

	}

}
