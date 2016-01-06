package client;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.HashMap;

import logger.LoggerProjet;
import serveur.IArene;
import serveur.element.Caracteristique;
import serveur.element.Element;
import serveur.element.Vampire;

import static utilitaires.Constantes.*;

/**
 * Strat�gie d'un Vampire
 */
public class StrategieVampire extends StrategiePersonnage {
	
	/**
	 * Cree un vampire, la console associe et sa strategie.
	 * @param ipArene ip de communication avec l'arene
	 * @param port port de communication avec l'arene
	 * @param ipConsole ip de la console du vampire
	 * @param nom le nom du vampire
	 * @param groupe le groupe du vampire
	 * @param caracts les caract�ristiques du vampire
	 * @param nbTours nombre de tours pour ce vampire (si negatif, illimite)
	 * @param position position initiale du vampire dans l'arene
	 * @param logger gestionnaire de log
	 */
	public StrategieVampire(String ipArene, int port, String ipConsole, 
			String nom, String groupe, HashMap<Caracteristique, Integer> caracts,
			int nbTours, Point position, LoggerProjet logger) {
		super(ipArene, port, ipConsole, nbTours, position, logger, new Vampire(nom, groupe,caracts));
	}
	
	/**
	 * action effectu�e si le personnage est � proximit�e d'une potion
	 * @param arene du personnage
	 * @param refRMI id du personnage
	 * @param refCible id de la potion
	 * @param voisins vision du personnage 
	 * @throws RemoteException
	 */
	protected boolean agirPotion(IArene arene, int refRMI, int refCible, HashMap<Integer, Point> voisins) throws RemoteException{
		if(arene.elementFromRef(refCible).getCaract(Caracteristique.INITIATIVE) > 0){
			// si la potion augmente l'initiative, le vampire la ramasse
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
	 * action effectu�e si le personnage est � proximit�e d'un personnage
	 * @param arene du personnage
	 * @param refRMI id du personnage
	 * @param refCible id du personnage attaqu�
	 * @param elemPlusProche personnage attaqu�
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
	 * action effectu�e si le personnage voit une potion
	 * @param arene du personnage
	 * @param refRMI id du personnage
	 * @param refCible id de la potion cibl�e
	 * @param elemPlusProche potion cibl�e
	 * @param voisins vision du personnage 
	 * @throws RemoteException
	 */
	protected boolean voitPotion(IArene arene, int refRMI, int refCible, Element elemPlusProche, HashMap<Integer, Point> voisins) throws RemoteException{
		if(elemPlusProche.getCaract(Caracteristique.INITIATIVE) > 0){
			// si la potion augmente l'initiative, le vampire se dirige vers elle
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
	 * action effectu�e si le personnage voit un personnage
	 * @param arene du personnage
	 * @param refRMI id du personnage
	 * @param refCible id du personnage cibl�
	 * @param elemPlusProche personnage cibl�
	 * @param voisins vision du personnage 
	 * @throws RemoteException
	 */
	protected boolean voitPersonnage(IArene arene, int refRMI, int refCible, Element elemPlusProche, HashMap<Integer, Point> voisins) throws RemoteException{
		if(console.getPersonnage().getCaract(Caracteristique.INITIATIVE) >= elemPlusProche.getCaract(Caracteristique.INITIATIVE)){
			// si le vampire a plus d'Initiative que le personnage cibl�, il charge ce dernier
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
