package client;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.HashMap;

import logger.LoggerProjet;
import serveur.IArene;
import serveur.element.Caracteristique;
import serveur.element.Element;
import serveur.element.Vampire;
import utilitaires.GenererCaracts;

/**
 * Stratégie d'un Vampire
 */
public class StrategieVampire extends StrategiePersonnage {
	
	/**
	 * Cree un vampire, la console associe et sa strategie.
	 * @param ipArene ip de communication avec l'arene
	 * @param port port de communication avec l'arene
	 * @param ipConsole ip de la console du vampire
	 * @param nom le nom du vampire
	 * @param groupe le groupe du vampire
	 * @param caracts les caractéristiques du vampire
	 * @param nbTours nombre de tours pour ce vampire (si negatif, illimite)
	 * @param position position initiale du vampire dans l'arene
	 * @param logger gestionnaire de log
	 */
	public StrategieVampire(String ipArene, int port, String ipConsole, 
			String nom, String groupe, HashMap<Caracteristique, Integer> caracts,
			int nbTours, Point position, LoggerProjet logger) {
		super(ipArene, port, ipConsole, nbTours, position, logger, new Vampire(nom, groupe,GenererCaracts.statsPerso("Vampire")));
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
		if(arene.elementFromRef(refCible).getCaract(Caracteristique.INITIATIVE) > 0){
			// si la potion augmente l'initiative, le vampire la ramasse
			return super.agirPotion(arene,refRMI,refCible,voisins);
		}
		else{
			//sinon on l'ignore
			voisins.remove(refCible);;
			return false;
		}
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
		if(elemPlusProche.getCaract(Caracteristique.INITIATIVE) > 0){
			// si la potion augmente l'initiative, le vampire se dirige vers elle
			return super.voitPersonnage(arene, refRMI, refCible, elemPlusProche, voisins);
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
	 * @param elemPlusProche personnage ciblé
	 * @param voisins vision du personnage 
	 * @throws RemoteException
	 */
	protected boolean voitPersonnage(IArene arene, int refRMI, int refCible, Element elemPlusProche, HashMap<Integer, Point> voisins) throws RemoteException{
		if(console.getPersonnage().getCaract(Caracteristique.INITIATIVE) >= elemPlusProche.getCaract(Caracteristique.INITIATIVE)){
			// si le vampire a plus d'Initiative que le personnage ciblé, il charge ce dernier
			return super.voitPersonnage(arene, refRMI, refCible, elemPlusProche, voisins);
		}
		else{
			// sinon il l'ignore
			voisins.remove(refCible);
			return false;
		}
	}
}
