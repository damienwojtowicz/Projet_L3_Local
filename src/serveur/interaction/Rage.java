package serveur.interaction;

import java.rmi.RemoteException;
import java.util.logging.Level;

import serveur.Arene;
import serveur.element.Caracteristique;
import serveur.element.Goule;
import serveur.vuelement.VuePersonnage;
import static utilitaires.Constantes.*;

public class Rage extends Interaction<VuePersonnage> {
	
	public Rage(Arene arene, VuePersonnage attaquant, VuePersonnage defenseur) {
		super(arene, attaquant, defenseur);
	}
	
	public void interagit(){
		// si le personnage est vivant, si c'est bien une goule, et si sa vie est sup�rieure � 20 points
		if(attaquant.getElement().estVivant() && attaquant.getElement() instanceof Goule && attaquant.getElement().getCaract(Caracteristique.VIE) > 20) {
			try {			
				arene.incrementeCaractPersonnage(defenseur, Caracteristique.VIE, RAGE_MALUS);
				arene.incrementeCaractPersonnage(defenseur, Caracteristique.FORCE, RAGE_BONUS);
				arene.incrementeCaractPersonnage(defenseur, Caracteristique.INITIATIVE, RAGE_BONUS);
			} catch (RemoteException e) {
				logs(Level.INFO, "\nErreur lors d'une crise rage : " + e.toString());
			}
		}
	}
}
