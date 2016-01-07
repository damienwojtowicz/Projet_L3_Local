package serveur.interaction;

import java.util.HashMap;
import java.util.logging.Level;

import serveur.Arene;
import serveur.element.Caracteristique;
import serveur.element.Druide;
import serveur.vuelement.VuePersonnage;
import serveur.vuelement.VuePotion;
import utilitaires.Constantes;
import static utilitaires.Constantes.*;

/**
 * Represente l'amélioration d'une potion par un Druide.
 *
 */
public class Concocter extends Interaction<VuePotion> {

	/**
	 * Cree une interaction de concoctage.
	 * @param arene arene
	 * @param druide Vuepersonnage du druide améliorant la potion
	 * @param potion potion a améliorer
	 */
	public Concocter(Arene arene, VuePersonnage druide, VuePotion potion) {
		super(arene, druide, potion);
	}

	@Override
	public void interagit() {
		logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + " essaye de concocter " + 
				Constantes.nomRaccourciClient(defenseur));
		
		// si le personnage est vivant et est bien un druide
		if(attaquant.getElement().estVivant() && attaquant.getElement() instanceof Druide) {

			// caracteristiques de la potion
			HashMap<Caracteristique, Integer> valeursPotion = defenseur.getElement().getCaracts();
			
			for(Caracteristique c : valeursPotion.keySet()) {
				arene.incrementeCaractPotion(defenseur, c, CONCOCTER_BONUS);
			}
			
			logs(Level.INFO, "Potion améliorée !");
			
		} else {
			logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + " ou " + 
					Constantes.nomRaccourciClient(defenseur) + " est deja mort... Rien ne se passe");
		}
	}
}
