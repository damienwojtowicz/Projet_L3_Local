package serveur.interaction;

import java.rmi.RemoteException;
import java.util.logging.Level;

import serveur.Arene;
import serveur.element.Feticheur;
import serveur.element.Poison;
import serveur.vuelement.VuePersonnage;

/**
 * 
 *
 */
public class Empoisone extends Interaction<VuePersonnage> {
	
	/**
	 * 
	 * @param arene arene
	 * @param feticheur Vuepersonnage du Feticheur
	 */
	public Empoisone(Arene arene, VuePersonnage feticheur) {
		super(arene, feticheur, feticheur);
	}
	
	public void interagit(){
		// si le personnage est vivant, si c'est bien une goule
		if(defenseur.getElement().estVivant() && defenseur.getElement() instanceof Feticheur) {
			try {
				arene.ajoutePotion(new Poison(defenseur.getElement().getNomGroupe()), defenseur.getPosition());
				defenseur.setPhrase("Je lache un poison");
				logs(Level.INFO, "Poison posé avec succes");
			} catch (RemoteException e) {
				logs(Level.INFO, "\nErreur lors d'un empoisonnement : " + e.toString());
			}
		}
		else{
			logs(Level.INFO, "\nEmpoisonnement impossible");
		}
	}
}
