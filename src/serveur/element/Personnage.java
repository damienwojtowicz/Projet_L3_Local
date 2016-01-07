/**
 * 
 */
package serveur.element;

import java.rmi.RemoteException;
import java.util.HashMap;

import serveur.Arene;
import serveur.vuelement.VuePersonnage;
import utilitaires.Calculs;

/**
 * Un personnage: un element possedant des caracteristiques et etant capable
 * de jouer une strategie.
 * 
 */
public class Personnage extends Element {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Cree un personnage avec un nom et un groupe.
	 * @param nom du personnage
	 * @param groupe d'etudiants du personnage
	 * @param caracts caracteristiques du personnage
	 */
	public Personnage(String nom, String groupe, HashMap<Caracteristique, Integer> caracts) {
		super(nom, groupe, caracts);
	}
	
	/**
	 * Incremente la caracteristique donnee de la valeur donnee.
	 * Si la caracteristique n'existe pas, elle sera cree avec la valeur 
	 * donnee.
	 * @param c caracteristique
	 * @param inc increment (peut etre positif ou negatif)
	 */
	public void incrementeCaract(Caracteristique c, int inc) {		
		if(caracts.containsKey(c)) {
			caracts.put(c, Calculs.restreintCarac(c, caracts.get(c) + inc));
		} else {
			caracts.put(c, Calculs.restreintCarac(c, inc));
		}
	}
	
	/**
	 * Tue ce personnage en mettant son nombre de poins de vie a 0.
	 */
	public void tue() {
		caracts.put(Caracteristique.VIE, 0);
	}

	/**
	 * Teste si le personnage est vivant, i.e., son nombre de points de vie
	 * est strictement superieur a 0.
	 * @return vrai si le personnage est vivant, faux sinon
	 */
	public boolean estVivant() {
		Integer vie = caracts.get(Caracteristique.VIE);
		return vie != null && vie > 0;
	}
	
	/**
	 * eventuelles actions à effectuer lorsque le personnage attaque
	 * @param defenseur la vue personnage du defenseur
	 * @param attaquant la vue personnage de l'attaquant
	 * @param arene l'arene ou combattent les personnages
	 * @throws RemoteException
	 */
	public void capaciteCombatAtt(VuePersonnage defenseur,VuePersonnage attaquant, Arene arene) throws RemoteException{
		// pas de capacité spéciale en attaque (comportement par defaut)
	}
	
	/**
	 * eventuelles actions à effectuer lorsque le personnage est attaqué
	 * @param defenseur la vue personnage du defenseur
	 * @param attaquant la vue personnage de l'attaquant
	 * @param arene l'arene ou combattent les personnages
	 * @throws RemoteException
	 */
	public void capaciteCombatDef(VuePersonnage defenseur,VuePersonnage attaquant, Arene arene) throws RemoteException{
		// pas de capacité spéciale en defense (comportement par defaut)
	}
}
