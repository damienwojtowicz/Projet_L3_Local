package client;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.HashMap;

import logger.LoggerProjet;
import serveur.IArene;
import serveur.element.Caracteristique;
import serveur.element.Goule;
import serveur.element.Element;
import utilitaires.GenererCaracts;
import static utilitaires.Constantes.*;

/**
 * Stratégie d'une Goule
 */
public class StrategieGoule extends StrategiePersonnage {
	
	/**
	 * Cree une Goule, la console associe et sa strategie.
	 * @param ipArene ip de communication avec l'arene
	 * @param port port de communication avec l'arene
	 * @param ipConsole ip de la console de la Goule
	 * @param nom le nom de la Goule
	 * @param groupe le groupe de la Goule
	 * @param caracts les caractéristiques de la Goule
	 * @param nbTours nombre de tours pour cette Goule (si negatif, illimite)
	 * @param position position initiale de la Goule dans l'arene
	 * @param logger gestionnaire de log
	 */
	public StrategieGoule(String ipArene, int port, String ipConsole, 
			String nom, String groupe, HashMap<Caracteristique, Integer> caracts,
			int nbTours, Point position, LoggerProjet logger) {
		super(ipArene, port, ipConsole, nbTours, position, logger, new Goule(nom, groupe,GenererCaracts.statsPerso("Goule")));
	}
	
	/**
	 * action effectuée si le personnage est à proximitée d'une potion
	 * @param arene du personnage
	 * @param refRMI id du personnage
	 * @param refCible id de la potion
	 * @param voisins vision du personnage 
	 * @throws RemoteException
	 */
	@Override
	protected boolean agirPotion(IArene arene, int refRMI, int refCible, HashMap<Integer, Point> voisins) throws RemoteException{
		// les goules ne peuvent pas ramasser de potion, on les ignore
		voisins.remove(refCible);;
		return false;
	}
	
	/**
	 * action effectuée si le personnage voit une potion
	 * @param arene du personnage
	 * @param refRMI id du personnage
	 * @param refCible id de la potion ciblée
	 * @param elemPlusProche potion ciblée
	 * @param voisins vision du personnage 
	 * @throws RemoteException
	 */
	@Override
	protected boolean voitPotion(IArene arene, int refRMI, int refCible, Element elemPlusProche, HashMap<Integer, Point> voisins) throws RemoteException{
		// les goules ne peuvent pas ramasser de potion, ils les ignorent
		voisins.remove(refCible);;
		return false;
	}
	
	/**
	 * action effectuée si le personnage voit un personnage
	 * @param arene du personnage
	 * @param refRMI id du personnage
	 * @param refCible id du personnage ciblé
	 * @param elemPlusProche personnage ciblée
	 * @param voisins vision du personnage 
	 * @throws RemoteException
	 */
	protected boolean voitPersonnage(IArene arene, int refRMI, int refCible, Element elemPlusProche, HashMap<Integer, Point> voisins) throws RemoteException{
		if(this.timerCapacite > 0){
			// si sa capacité n'est pas prête elle charge (comportement par defaut)
			return super.voitPersonnage(arene, refRMI, refCible, elemPlusProche, voisins);
		}
		else{
			// sinon elle tente de lancer une Rage
			if(arene.elementFromRef(refRMI).getCaract(Caracteristique.VIE) > GOULE_SEUIL_VIE){
				this.timerCapacite = RAGE_TIMER;
				voisins.clear();
				return arene.lancerRage(refRMI);
			}
			else{
				return super.voitPersonnage(arene, refRMI, refCible, elemPlusProche, voisins);
			}
		}
	}
	
	/**
	 * action effectuée si le personnage ne voit rien
	 * @param arene du personnage
	 * @param refRMI id du personnage
	 * @param voisins vision du personnage 
	 * @throws RemoteException
	 */
	protected boolean agirRien(IArene arene, int refRMI, HashMap<Integer, Point> voisins) throws RemoteException{
		if(this.timerCapacite > 0){
			return super.agirRien(arene, refRMI, voisins);
		}
		else{
			if(arene.elementFromRef(refRMI).getCaract(Caracteristique.VIE) > GOULE_SEUIL_VIE){
				this.timerCapacite = RAGE_TIMER;
				voisins.clear();
				return arene.lancerRage(refRMI);
			}
			else{
				return super.agirRien(arene, refRMI, voisins);
			}
		}
	}
}
