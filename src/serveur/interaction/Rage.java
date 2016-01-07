package serveur.interaction;

import java.rmi.RemoteException;
import java.util.logging.Level;

import serveur.Arene;
import serveur.element.Caracteristique;
import serveur.element.Goule;
import serveur.vuelement.VuePersonnage;
import static utilitaires.Constantes.*;

/**
 * Represente une amélioration d'initiative et de force par une Goule en échange de vie.
 *
 */
public class Rage extends Interaction<VuePersonnage> {
	
	/**
	 * Cree une interaction de rage.
	 * @param arene arene
	 * @param goule Vuepersonnage de la Goule faisant un crise de rage
	 */
	public Rage(Arene arene, VuePersonnage goule) {
		super(arene, goule, goule);
	}
	
	public void interagit(){
		// si le personnage est vivant, si c'est bien une goule
		if(defenseur.getElement().estVivant() && defenseur.getElement() instanceof Goule) {
			try {
				
				arene.incrementeCaractPersonnage(defenseur, Caracteristique.VIE, RAGE_MALUS);
				arene.incrementeCaractPersonnage(defenseur, Caracteristique.FORCE, RAGE_BONUS);
				arene.incrementeCaractPersonnage(defenseur, Caracteristique.INITIATIVE, RAGE_BONUS);
				defenseur.setPhrase("RAAAH ! je suis plus fort !");
				logs(Level.INFO, "crise de rage réussie");
			} catch (RemoteException e) {
				logs(Level.INFO, "\nErreur lors d'une crise rage : " + e.toString());
			}
		}
		else{
			logs(Level.INFO, "\nCrise de rage impossible");
		}
	}
}
