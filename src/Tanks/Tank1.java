package Tanks;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Automatitem.RepaintListener;
import Principal.Equipe;
import Principal.Position;
import Principal.Sol;

public class Tank1 extends TankBasic {
	
	public Tank1(Sol sol, Equipe equipe, int abscisse, int vie, RepaintListener repaintListener) throws IOException {
		super(sol, equipe, abscisse, vie, ImageIO.read(new File("src/Images/tank1.png")),
				
				// Coordonnées des points d’appui du tank dans l’image (à changer si l’image est modifiée) : coordInfGauche, coordInfDroit
				new Position(14, 82), new Position(63, 82), repaintListener);
	}
	
}
