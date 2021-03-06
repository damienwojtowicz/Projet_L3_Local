package lanceur;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;

import client.*;
import logger.LoggerProjet;
import serveur.element.Caracteristique;
import utilitaires.Calculs;
import utilitaires.Constantes;

/**
 * Lance une Console avec un Element sur l'Arene. 
 * A lancer apres le serveur, eventuellement plusieurs fois.
 */
public class LancePersonnage {
	
	private static String usage = "USAGE : java " + LancePersonnage.class.getName() + " [ port [ ipArene ] ]";

	public static void lancerPerso(String[] args) {
		String groupe = "G4";
			
		// nombre de tours pour ce personnage avant d'etre deconnecte 
		// (30 minutes par defaut)
		// si negatif, illimite
		int nbTours = Constantes.NB_TOURS_PERSONNAGE_DEFAUT;
		
		// init des arguments
		int port = Constantes.PORT_DEFAUT;
		String ipArene = Constantes.IP_DEFAUT;
			
		if (args.length > 0) {
			if (args[0].equals("--help") || args[0].equals("-h")) {
				ErreurLancement.aide(usage);
			}
				
			if (args.length > 2) {
				ErreurLancement.TROP_ARGS.erreur(usage);
			}
			
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				ErreurLancement.PORT_NAN.erreur(usage);
			}
			
			if (args.length > 1) {
				ipArene = args[1];
			}
		}
		
		// creation du logger
		LoggerProjet logger = null;
		try {
			logger = new LoggerProjet(true, "personnages_groupe" + groupe);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(ErreurLancement.suivant);
		}
		
		// lancement du serveur
		try {
			String ipConsole = InetAddress.getLocalHost().getHostAddress();
			
			logger.info("Lanceur", "Creation du personnage...");
			
			// caracteristiques du personnage
			HashMap<Caracteristique, Integer> caracts = new HashMap<Caracteristique, Integer>();
			// seule la force n'a pas sa valeur par defaut (exemple)
			caracts.put(Caracteristique.FORCE, 
					Calculs.valeurCaracAleatoire(Caracteristique.FORCE)); 
				
			//Point position = Calculs.positionAleatoireArene();
			
			new StrategieDruide(ipArene, port, ipConsole, "Damien",groupe,caracts, nbTours, Calculs.positionAleatoireArene(), logger);
			new StrategieVampire(ipArene, port, ipConsole, "Hassen",groupe,caracts, nbTours, Calculs.positionAleatoireArene(), logger);
			new StrategieBerserker(ipArene, port, ipConsole, "Thibaut",groupe,caracts, nbTours, Calculs.positionAleatoireArene(), logger);
			new StrategieGoule(ipArene, port, ipConsole, "Guilhem",groupe,caracts, nbTours, Calculs.positionAleatoireArene(), logger);
			new StrategieFeticheur(ipArene, port, ipConsole, "Jack",groupe,caracts, nbTours, Calculs.positionAleatoireArene(), logger);
			
			logger.info("Lanceur", "Creation des personnages reussie");
			
		} catch (Exception e) {
			logger.severe("Lanceur", "Erreur lancement :\n" + e.getCause());
			e.printStackTrace();
			System.exit(ErreurLancement.suivant);
		}
	}
}