package serveur.element;

import java.rmi.RemoteException;
import java.util.HashMap;

import serveur.Arene;
import serveur.vuelement.VuePersonnage;

/**
 * 
 * Feticheur: un personnage qui ne ramasse pas de potion mais laisse des potions piégées.
 * 
 */
public class Feticheur extends Personnage {

	private static final long serialVersionUID = 1L;

	/**
	 * Cree un Feticheur avec un nom et un groupe.
	 * @param nom du Feticheur
	 * @param groupe d'etudiants du Feticheur
	 * @param caracts caracteristiques du Feticheur
	 */
	public Feticheur(String nom, String groupe, HashMap<Caracteristique, Integer> caracts){
		super("Feticheur "+nom, groupe, caracts);
	}
}
