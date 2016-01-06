package serveur.element;

import java.util.HashMap;

/**
 * 
 * Elfe: un personnage qui attaque uniquement les adversaires qui ont moins d'initiative.
 * 
 */
public class Elfe extends Personnage {

	private static final long serialVersionUID = 1L;

	/**
	 * Cree un Elfe avec un nom et un groupe.
	 * @param nom de l'Elfe
	 * @param groupe d'etudiants de l'Elfe
	 * @param caracts caracteristiques de l'Elfe
	 */
	public Elfe(String nom, String groupe, HashMap<Caracteristique, Integer> caracts){
		super("Elfe "+nom, groupe, caracts);
	}
}
