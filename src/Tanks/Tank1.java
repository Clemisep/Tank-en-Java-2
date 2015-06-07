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
				ImageIO.read(new File("src/Images/canon1.png")),
				// L’abscisse du milieu du tank dans l’image (à changer si l’image est modifiée) est 38
				38,
				new Position(61, 45), new Position(0, 3),
				repaintListener);
	}
	
}
