package lanceur;

import java.io.IOException;

import logger.LoggerProjet;
import serveur.IArene;
import serveur.element.Caracteristique;
import serveur.element.PotionSpecialisee;
import utilitaires.Calculs;
import utilitaires.Constantes;

public class LancePotion {
	
	private static String usage = "USAGE : java " + LancePotion.class.getName() + " [ port [ ipArene ] ]";

	public static void main(String[] args) {
		String groupe = "G4";
		for(int i = 0 ; i<9 ; i++){
			
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
			
			switch(i%3){
			case 0:
				//potion de vie
				executerPotion(Caracteristique.VIE,groupe,ipArene,port);
				break;
			case 1:
				//potion de force
				executerPotion(Caracteristique.FORCE,groupe,ipArene,port);
				break;
			case 2:
				//potion d'initiative
				executerPotion(Caracteristique.INITIATIVE,groupe,ipArene,port);
				break;
			}
		}
	}
	/**
	 * Créé une potion spécialisée sur une arène selon une caracteristique principale
	 * @param c la caracteristique principale
	 * @param groupe le groupe de la potion
	 * @param ipArene l'adresse ip de l'arène
	 * @param port le port de connexion
	 */
	private static void executerPotion(Caracteristique c,String groupe,String ipArene,int port){
		// creation du logger
		LoggerProjet logger = null;
		
		try {
			logger = new LoggerProjet(true, "potion_"+utilitaires.GenererCaracts.nommerPotion(c)+groupe);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(ErreurLancement.suivant);
			}
	
		// lancement de la potion
		try {
			IArene arene = (IArene) java.rmi.Naming.lookup(Constantes.nomRMI(ipArene, port, "Arene"));
			
			logger.info("Lanceur", "Lancement de la potion sur le serveur...");
		
			// ajout de la potion
			arene.ajoutePotion(new PotionSpecialisee(groupe,c), Calculs.positionAleatoireArene());
			logger.info("Lanceur", "Lancement de la potion reussi");
		
		} catch (Exception e) {
			logger.severe("Lanceur", "Erreur lancement :\n" + e.getCause());
			e.printStackTrace();
			System.exit(ErreurLancement.suivant);
		}
	}
}
