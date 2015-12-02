package lanceur;

import java.io.IOException;
import java.util.HashMap;

import logger.MyLogger;
import modele.Caracteristique;
import serveur.IArene;
import utilitaires.Calculs;

public class LancePotion {
	
	private static String usage = "USAGE : java " + LancePotion.class.getName() + " [ port [ nom_arene ] ]";

	public static void main(String[] args) {
		// init des variables
		String nom = "Anduril";
		String groupe = "B" + Calculs.randomNumber(0,99); // REMPLACER CETTE LIGNE PAR VOTRE NUMERO DE GROUPE
					// vous ne pourrez pas participer au tournoi si ce n'est pas fait
		
		// init des arguments
		int port = 5099;
		String ipArene = "localhost";
		
		if (args.length > 0) {
			if (args[0].equals("--help") || args[0].equals("-h")) {
				Erreur.help(usage);
			}
			
			if (args.length > 2) {
				Erreur.TOO_MUCH_ARGS.erreur(usage);
			}
			
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				Erreur.PORT_NAN.erreur(usage);
			}
			
			if (args.length == 2) {
				ipArene = args[1];
			}
		}
		
		// creation du logger
		MyLogger logger = null;
		try {
			logger = new MyLogger(true, "potion_"+nom+groupe);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(Erreur.suivant);
		}
		
		// lancement de la potion
		try {
			IArene arene = (IArene) java.rmi.Naming.lookup("rmi://"+ipArene+":"+port+"/Arene");

			logger.info("lanceur", "Lancement de la potion sur le serveur...");
			
			// caracteristiques de la potion
			HashMap<Caracteristique, Integer> caractsPotion = new HashMap<Caracteristique, Integer>();
			caractsPotion.put(Caracteristique.VIE, Calculs.randomNumber(-100, 100));
			caractsPotion.put(Caracteristique.FORCE, Calculs.randomNumber(-100, 100));
			caractsPotion.put(Caracteristique.INITIATIVE, Calculs.randomNumber(-100, 100));
			
			// ajout de la potion
			arene.ajouterPotion(nom, groupe, caractsPotion);
			logger.info("lanceur", "Lancement de la potion reussi");
			
		} catch (Exception e) {
			logger.severe("lanceur", "Erreur lancement :\n"+e.getCause());
			e.printStackTrace();
			System.exit(Erreur.suivant);
		}
	}
}
