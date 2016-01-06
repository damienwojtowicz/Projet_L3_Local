package client;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.HashMap;

import logger.LoggerProjet;
import serveur.IArene;
import serveur.element.Caracteristique;
import serveur.element.Berserker;
import serveur.element.Element;
import static utilitaires.Constantes.*;

/**
 * Stratégie d'un Berserker
 */
public class StrategieBerserker extends StrategiePersonnage {
	
	/**
	 * Cree un berserker, la console associe et sa strategie.
	 * @param ipArene ip de communication avec l'arene
	 * @param port port de communication avec l'arene
	 * @param ipConsole ip de la console du berserker
	 * @param nom le nom du berserker
	 * @param groupe le groupe du berserker
	 * @param caracts les caractéristiques du berserker
	 * @param nbTours nombre de tours pour ce berserker (si negatif, illimite)
	 * @param position position initiale du berserker dans l'arene
	 * @param logger gestionnaire de log
	 */
	public StrategieBerserker(String ipArene, int port, String ipConsole, 
			String nom, String groupe, HashMap<Caracteristique, Integer> caracts,
			int nbTours, Point position, LoggerProjet logger) {
		super(ipArene, port, ipConsole, nbTours, position, logger, new Berserker(nom, groupe,caracts));
	}
	
	/**
	 * action effectuée si le personnage est à proximitée d'une potion
	 * @param arene du personnage
	 * @param refRMI id du personnage
	 * @param refCible id de la potion
	 * @param voisins vision du personnage 
	 * @throws RemoteException
	 */
	protected boolean agirPotion(IArene arene, int refRMI, int refCible, HashMap<Integer, Point> voisins) throws RemoteException{
		if(arene.elementFromRef(refCible).getCaract(Caracteristique.VIE) > 0 
				&& console.getPersonnage().getCaract(Caracteristique.VIE) < BERSERK_SEUIL_SOIN){
			// si la potion soigne et le berserker est blessé on la ramasse
			console.setPhrase("Je ramasse une potion");
			arene.ramassePotion(refRMI, refCible);
			voisins.clear();
			return true;
		}
		else{
			//sinon on l'ignore
			voisins.remove(refCible);;
			return false;
		}
	}
	
	/**
	 * action effectuée si le personnage est à proximitée d'un personnage
	 * @param arene du personnage
	 * @param refRMI id du personnage
	 * @param refCible id du personnage attaqué
	 * @param elemPlusProche personnage attaqué
	 * @param voisins vision du personnage 
	 * @throws RemoteException
	 */
	protected boolean agirPersonnage(IArene arene, int refRMI, int refCible, Element elemPlusProche, HashMap<Integer, Point> voisins) throws RemoteException{
		console.setPhrase("Je fais un duel avec " + elemPlusProche.getNom());
		arene.lanceAttaque(refRMI, refCible);
		voisins.clear();
		return true;
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
	protected boolean voitPotion(IArene arene, int refRMI, int refCible, Element elemPlusProche, HashMap<Integer, Point> voisins) throws RemoteException{
		if(elemPlusProche.getCaract(Caracteristique.VIE) > 0 
				&& arene.elementFromRef(refRMI).getCaract(Caracteristique.VIE) < BERSERK_SEUIL_SOIN){
			// si la potion soigne et le berserker est blessé on se dirige vers elle
			console.setPhrase("Je vais vers mon voisin " + elemPlusProche.getNom());
			arene.deplace(refRMI, refCible);
			voisins.clear();
			return true;
		}else{
			// sinon on l'ignore
			voisins.remove(refCible);
			return false;
		}
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
		if(console.getPersonnage().getCaract(Caracteristique.VIE) >= BERSERK_SEUIL_ATK){
			// si le berserker a suffisament de vie, il charge le personnage le plus proche
			console.setPhrase("Je vais vers mon voisin " + elemPlusProche.getNom());
			arene.deplace(refRMI, refCible);
			voisins.clear();
			return true;
		}
		else{
			// sinon il l'ignore
			voisins.remove(refCible);
			return false;
		}
	}
	
}
