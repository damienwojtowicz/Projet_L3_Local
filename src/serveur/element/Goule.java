package serveur.element;

import java.util.HashMap;

/**
 * 
 * Goule: un personnage qui gagne de la vie lorsqu'il tue un adversaire,
 * peut sacrifier des points de vie pour augmenter ses caractéristiques.
 * 
 */
public class Goule extends Personnage {

	private static final long serialVersionUID = 1L;

	/**
	 * Cree une Goule avec un nom et un groupe.
	 * @param nom de la Goule
	 * @param groupe d'etudiants de la Goule
	 * @param caracts caracteristiques de la Goule
	 */
	public Goule(String nom, String groupe, HashMap<Caracteristique, Integer> caracts){
		super("Goule "+nom, groupe, caracts);
	}
}
