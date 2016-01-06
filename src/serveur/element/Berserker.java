package serveur.element;

import java.util.HashMap;

/**
 * 
 * Berserker: un personnage qui gagne de la force lorsqu'il est attaqué.
 * 
 */
public class Berserker extends Personnage {

	private static final long serialVersionUID = 1L;

	/**
	 * Cree un berserker avec un nom et un groupe.
	 * @param nom du berserker
	 * @param groupe d'etudiants du berserker
	 * @param caracts caracteristiques du berserker
	 */
	public Berserker(String nom, String groupe, HashMap<Caracteristique, Integer> caracts){
		super("Berserker "+nom, groupe, caracts);
	}
}
