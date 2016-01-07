package lanceur;

/**
 * @author 21001611
 *
 */
public class ToutLancer{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LanceArene.lancerArene(args);
		LanceIHM.lancerIHM(args);
		LancePotion.lancerPotion(args);
		LancePersonnage.lancerPerso(args);
	}
}
