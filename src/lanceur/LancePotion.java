package lanceur;

import java.io.IOException;
import java.util.HashMap;

import logger.LoggerProjet;
import serveur.IArene;
import serveur.element.Caracteristique;
import serveur.element.Potion;
import utilitaires.Calculs;
import utilitaires.Constantes;

public class LancePotion {
	
	private static String usage = "USAGE : java " + LancePotion.class.getName() + " [ port [ ipArene ] ]";

	public static void main(String[] args) {
		String[] nom = {"Anduril", "Fine Lame", "Excalibur"};
		
		for(int i = 0 ; i<nom.length ; i++){
			String groupe = "G4"; 
			
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
				logger = new LoggerProjet(true, "potion_"+nom[i]+groupe);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(ErreurLancement.suivant);
			}
			
			// lancement de la potion
			try {
				IArene arene = (IArene) java.rmi.Naming.lookup(Constantes.nomRMI(ipArene, port, "Arene"));
	
				logger.info("Lanceur", "Lancement de la potion sur le serveur...");
				
				// caracteristiques de la potion
				HashMap<Caracteristique, Integer> caractsPotion = new HashMap<Caracteristique, Integer>();
				
				Caracteristique vie = Caracteristique.VIE;
				Caracteristique force = Caracteristique.FORCE;
				Caracteristique init = Caracteristique.INITIATIVE;
				int vieMax = vie.getMax();
				int forMax = force.getMax();
				int initMax = init.getMax();
				
				switch(i){
				case 0:
					//potion de vie
					caractsPotion.put(vie, Calculs.restreintNombre((int)(0.5 * vieMax), vieMax, (int)(1.5 * Calculs.valeurCaracAleatoire(vie))));
					caractsPotion.put(force, Calculs.restreintNombre(-(int)(0.5 * forMax),(int)(0.5 * forMax), Calculs.valeurCaracAleatoirePosNeg(force)));
					caractsPotion.put(init, Calculs.restreintNombre(-(int)(0.5 * initMax),(int)(0.5 * initMax), Calculs.valeurCaracAleatoirePosNeg(init)));
					break;
				case 1:
					//potion d'initiative
					caractsPotion.put(vie, Calculs.restreintNombre(-(int)(0.5 * vieMax),(int)(0.5 * vieMax), Calculs.valeurCaracAleatoirePosNeg(vie)));
					caractsPotion.put(force, Calculs.restreintNombre(-(int)(0.5 * forMax),(int)(0.5 * forMax), Calculs.valeurCaracAleatoirePosNeg(force)));
					caractsPotion.put(init, Calculs.restreintNombre((int)(0.5 * initMax), initMax, (int)(1.5 * Calculs.valeurCaracAleatoire(init))));
					break;
				case 2:
					//potion de force
					caractsPotion.put(vie, Calculs.restreintNombre(-(int)(0.5 * vieMax),(int)(0.5 * vieMax), Calculs.valeurCaracAleatoirePosNeg(vie)));
					caractsPotion.put(force, Calculs.restreintNombre((int)(0.5 * forMax), forMax, (int)(1.5 * Calculs.valeurCaracAleatoire(force))));
					caractsPotion.put(init, Calculs.restreintNombre(-(int)(0.5 * initMax),(int)(0.5 * initMax), Calculs.valeurCaracAleatoirePosNeg(init)));
					break;
				}
				
				// ajout de la potion
				arene.ajoutePotion(new Potion(nom[i], groupe, caractsPotion), Calculs.positionAleatoireArene());
				logger.info("Lanceur", "Lancement de la potion reussi");
				
			} catch (Exception e) {
				logger.severe("Lanceur", "Erreur lancement :\n" + e.getCause());
				e.printStackTrace();
				System.exit(ErreurLancement.suivant);
			}
		}
	}
}
