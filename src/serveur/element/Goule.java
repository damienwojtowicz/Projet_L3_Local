package serveur.element;

import java.rmi.RemoteException;
import java.util.HashMap;

import serveur.Arene;
import serveur.vuelement.VuePersonnage;
import static utilitaires.Constantes.*;

/**
 * 
 * Goule: un personnage qui gagne de la vie lorsqu'il tue un adversaire,
 * peut sacrifier des points de vie pour augmenter ses caract�ristiques.
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
	public void capaciteCombatAtt(VuePersonnage defenseur,VuePersonnage attaquant, Arene arene) throws RemoteException{
		if(!defenseur.getElement().estVivant()){
			arene.incrementeCaractPersonnage(attaquant, Caracteristique.VIE, CANNIBALISME);
		}
	}
	public void capaciteCombatDef(VuePersonnage defenseur,VuePersonnage attaquant, Arene arene) throws RemoteException{
		
	}
}
