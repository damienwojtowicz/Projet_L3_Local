package serveur.element;

import java.rmi.RemoteException;
import java.util.HashMap;

import serveur.Arene;
import serveur.vuelement.VuePersonnage;

import static utilitaires.Constantes.*;

/**
 * 
 * Berserker: un personnage qui gagne de la force lorsqu'il est attaqu�.
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
	
	/**
	 * eventuelles actions � effectuer lorsque le personnage est attaqu�
	 * @param defenseur la vue personnage du defenseur
	 * @param attaquant la vue personnage de l'attaquant
	 * @param arene l'arene ou combattent les personnages
	 * @throws RemoteException
	 */
	public void capaciteCombatDef(VuePersonnage defenseur,VuePersonnage attaquant, Arene arene) throws RemoteException{
		// quand le berserker est attaqu� il gagne de la force
		arene.incrementeCaractPersonnage(defenseur, Caracteristique.FORCE, BERSERK_FORCE);
	}
}
