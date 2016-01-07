package serveur.interaction;

import java.rmi.RemoteException;
import java.util.logging.Level;

import serveur.Arene;
import serveur.element.Caracteristique;
import serveur.element.Goule;
import serveur.vuelement.VuePersonnage;
import static utilitaires.Constantes.*;

/**
 * Represente une am�lioration d'initiative et de force par une Goule en �change de vie.
 *
 */
public class Rage extends Interaction<VuePersonnage> {
	
	/**
	 * Cree une interaction de rage.
	 * @param arene arene
	 * @param goule Vuepersonnage de la Goule faisant un crise de rage
	 */
	public Rage(Arene arene, VuePersonnage goule) {
		super(arene, goule);
	}
	
	public void interagit(){
		// si le personnage est vivant, si c'est bien une goule, et si sa vie est sup�rieure � 20 points
		if(attaquant.getElement().estVivant() && attaquant.getElement() instanceof Goule) {
			try {			
				arene.incrementeCaractPersonnage(attaquant, Caracteristique.VIE, RAGE_MALUS);
				arene.incrementeCaractPersonnage(attaquant, Caracteristique.FORCE, RAGE_BONUS);
				arene.incrementeCaractPersonnage(attaquant, Caracteristique.INITIATIVE, RAGE_BONUS);
				logs(Level.INFO, "RAAAAAARG ! Je suis plus fort !");
			} catch (RemoteException e) {
				logs(Level.INFO, "\nErreur lors d'une crise rage : " + e.toString());
			}
		}
	}
}
