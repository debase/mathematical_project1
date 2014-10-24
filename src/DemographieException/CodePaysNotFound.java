package DemographieException;

public class CodePaysNotFound extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CodePaysNotFound(String codePays) {
		System.out.println("Error : country code " + "\"" + codePays + "\"" + " not found");
	}
}
