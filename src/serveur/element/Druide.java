package serveur.element;

import java.util.HashMap;

/**
 * 
 * Druide: un personnage qui améliore les potions avant de les ramasser.
 * 
 */
public class Druide extends Personnage {

	private static final long serialVersionUID = 1L;

	/**
	 * Cree un Druide avec un nom et un groupe.
	 * @param nom du Druide
	 * @param groupe d'etudiants du Druide
	 * @param caracts caracteristiques du Druide
	 */
	public Druide(String nom, String groupe, HashMap<Caracteristique, Integer> caracts){
		super("Druide "+nom, groupe, caracts);
	}
}
