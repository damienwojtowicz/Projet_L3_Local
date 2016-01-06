package serveur.element;

import java.util.HashMap;

/**
 * 
 * Vampire: un personnage qui gagne de la vie � chaque attaque puis se t�l�porte
 * � une position al�atoire.
 * 
 */
public class Vampire extends Personnage {

	private static final long serialVersionUID = 1L;

	/**
	 * Cree un Vampire avec un nom et un groupe.
	 * @param nom du Vampire
	 * @param groupe d'etudiants du Vampire
	 * @param caracts caracteristiques du Vampire
	 */
	public Vampire(String nom, String groupe, HashMap<Caracteristique, Integer> caracts){
		super("Vampire "+nom, groupe, caracts);
	}
}
