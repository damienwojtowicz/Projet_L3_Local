package client;


import java.awt.Point;
import java.rmi.RemoteException;
import java.util.HashMap;

import client.controle.Console;
import logger.LoggerProjet;
import serveur.IArene;
import serveur.element.Element;
import serveur.element.Personnage;
import serveur.element.Potion;
import utilitaires.Calculs;
import utilitaires.Constantes;

/**
 * Strategie d'un personnage. 
 */
public class StrategiePersonnage {
	
	/**
	 * Console permettant d'ajouter une phrase et de recuperer le serveur 
	 * (l'arene).
	 */
	protected Console console;

	/**
	 * Cree un personnage, la console associe et sa strategie.
	 * @param ipArene ip de communication avec l'arene
	 * @param port port de communication avec l'arene
	 * @param ipConsole ip de la console du personnage
	 * @param nbTours nombre de tours pour ce personnage (si negatif, illimite)
	 * @param position position initiale du personnage dans l'arene
	 * @param logger gestionnaire de log
	 * @param perso le personnage liée à la stratégie
	 */
	public StrategiePersonnage(String ipArene, int port, String ipConsole, 
			int nbTours, Point position, LoggerProjet logger, Personnage perso) {
		
		logger.info("Lanceur", "Creation de la console...");
		
		try {
			console = new Console(ipArene, port, ipConsole, this, 
					perso, 
					nbTours, position, logger);
			logger.info("Lanceur", "Creation de la console reussie");
			
		} catch (Exception e) {
			logger.info("Personnage", "Erreur lors de la creation de la console : \n" + e.toString());
			e.printStackTrace();
		}
	}

	// TODO etablir une strategie afin d'evoluer dans l'arene de combat
	// une proposition de strategie (simple) est donnee ci-dessous
	/** 
	 * Decrit la strategie.
	 * Les methodes pour evoluer dans le jeu doivent etre les methodes RMI
	 * de Arene et de ConsolePersonnage. 
	 * @param voisins element voisins de cet element (elements qu'il voit)
	 * @throws RemoteException
	 */
	public void executeStrategie(HashMap<Integer, Point> voisins) throws RemoteException {
		// arene
		IArene arene = console.getArene();
		
		// reference RMI de l'element courant
		int refRMI = 0;
		
		// position de l'element courant
		Point position = null;
		
		boolean aJoue = false;
		try {
			refRMI = console.getRefRMI();
			position = arene.getPosition(refRMI);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		while(!voisins.isEmpty()){
			int refCible = Calculs.chercheElementProche(position, voisins);
			int distPlusProche = Calculs.distanceChebyshev(position, arene.getPosition(refCible));

			Element elemPlusProche = arene.elementFromRef(refCible);
			if(distPlusProche <= Constantes.DISTANCE_MIN_INTERACTION) { // si suffisamment proches
				// j'interagis directement
				if(elemPlusProche instanceof Potion) { // potion
					// ramassage
					aJoue=agirPotion(arene,refRMI,refCible,voisins);

				} else { // personnage
					// duel
					aJoue=agirPersonnage(arene, refRMI, refCible, elemPlusProche,voisins);
				}
			}
			else { // si voisins, mais plus eloignes
				// je vais vers le plus proche
				if(elemPlusProche instanceof Potion){
					aJoue=voitPotion(arene, refRMI, refCible, elemPlusProche,voisins);
				}
				else{
					aJoue=voitPersonnage(arene, refRMI, refCible, elemPlusProche, voisins);
				}
			}
		}
		if(!aJoue) { // je n'ai pas de voisins, j'erre
			aJoue=agirRien(arene, refRMI,voisins); 
		}
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
		console.setPhrase("Je ramasse une potion");
		arene.ramassePotion(refRMI, refCible);
		voisins.clear();
		return true;
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
		console.setPhrase("Je vais vers mon voisin " + elemPlusProche.getNom());
		arene.deplace(refRMI, refCible);
		voisins.clear();
		return true;
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
		console.setPhrase("Je vais vers mon voisin " + elemPlusProche.getNom());
		arene.deplace(refRMI, refCible);
		voisins.clear();
		return true;
	}
	
	/**
	 * action effectuée si le personnage ne voit rien
	 * @param arene du personnage
	 * @param refRMI id du personnage
	 * @param voisins vision du personnage 
	 * @throws RemoteException
	 */
	protected boolean agirRien(IArene arene, int refRMI, HashMap<Integer, Point> voisins) throws RemoteException{
		console.setPhrase("J'erre...");
		arene.deplace(refRMI, 0); 
		voisins.clear();
		return true;
	}
	
	
}
