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
	
	/**
	 * eventuelles actions à effectuer lorsque le personnage attaque
	 * @param defenseur la vue personnage du defenseur
	 * @param attaquant la vue personnage de l'attaquant
	 * @param arene l'arene ou combattent les personnages
	 * @throws RemoteException
	 */
	public void capaciteCombatAtt(VuePersonnage defenseur,VuePersonnage attaquant, Arene arene) throws RemoteException{
		//quand le vampire attaque il se soigne puis se téléporte
		arene.incrementeCaractPersonnage(attaquant, Caracteristique.VIE, DRAIN);
		attaquant.setPosition(Calculs.positionAleatoireArene());
	}
}
