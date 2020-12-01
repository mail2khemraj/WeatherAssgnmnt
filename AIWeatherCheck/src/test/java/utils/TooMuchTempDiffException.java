package utils;

public class TooMuchTempDiffException extends Exception {

	String exceptionMsg; 
	
	public TooMuchTempDiffException(String msg) {
		exceptionMsg = msg;
	}
	
	public String toString(){ 
			return ("TooMuchTempDiffException Occurred: "+exceptionMsg) ;
    }
}
