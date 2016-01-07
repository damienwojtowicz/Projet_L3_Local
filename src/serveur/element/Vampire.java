package serveur.element;

import java.rmi.RemoteException;
import java.util.HashMap;

import serveur.Arene;
import serveur.vuelement.VuePersonnage;
import utilitaires.Calculs;
import static utilitaires.Constantes.*;

/**
 * 
 * Vampire: un personnage qui gagne de la vie à chaque attaque puis se téléporte
 * à une position aléatoire.
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
	public void capaciteCombatAtt(VuePersonnage defenseur,VuePersonnage attaquant, Arene arene) throws RemoteException{
		arene.incrementeCaractPersonnage(attaquant, Caracteristique.VIE, DRAIN);
		attaquant.setPosition(Calculs.positionAleatoireArene());
	}
	public void capaciteCombatDef(VuePersonnage defenseur,VuePersonnage attaquant, Arene arene) throws RemoteException{
		
	}
}
