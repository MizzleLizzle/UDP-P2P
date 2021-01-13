/**
 * IncorrectStatusStringRepresentation
 */
public class IncorrectStatusStringRepresentation extends Exception{
    public IncorrectStatusStringRepresentation(String errorMessage){
        super("Encountered wrong formating while trying to parse:" + errorMessage);
    }
}