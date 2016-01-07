package serveur.element;

import java.rmi.RemoteException;
import java.util.HashMap;

import serveur.Arene;
import serveur.vuelement.VuePersonnage;
import static utilitaires.Constantes.*;

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
	
	/**
	 * eventuelles actions à effectuer lorsque le personnage attaque
	 * @param defenseur la vue personnage du defenseur
	 * @param attaquant la vue personnage de l'attaquant
	 * @param arene l'arene ou combattent les personnages
	 * @throws RemoteException
	 */
	public void capaciteCombatAtt(VuePersonnage defenseur,VuePersonnage attaquant, Arene arene) throws RemoteException{
		// quand la goule tue une personnage, elle est soignée
		if(!defenseur.getElement().estVivant()){
			arene.incrementeCaractPersonnage(attaquant, Caracteristique.VIE, CANNIBALISME);
		}
	}
}
