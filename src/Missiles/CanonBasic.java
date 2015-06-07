package Missiles;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import Principal.ComposableElementsGraphiques;
import Principal.Position;
import Principal.Sol;

public class CanonBasic implements Canon {
	
	public static final String[] tableauLienCanon = {"src/Images/canon1.png"};
	public static final Position[] tableauAccrocheCanon = {new Position(0, 37)};
	
	private Position accrocheCanon;
	private BufferedImage imageCanon;
	private int munitions;
	private BufferedImage imageIcone;
	double angleCanon = 0;
	
	/**
	 * 
	 * @param image Image du canon.
	 * @param imageIcone Icône représentant le canon.
	 * @param accrocheCanon Position du point d'accroche du canon dans l'image du canon.
	 * @param munitions Nombre de munitions au départ (-1 pour illimité).
	 */
	public CanonBasic(BufferedImage image, BufferedImage imageIcone, Position accrocheCanon, int munitions) {
		this.imageCanon = image;
		this.imageIcone = imageIcone;
		this.accrocheCanon = accrocheCanon;
		this.munitions = munitions;
	}

	@Override
	public long bougerCanon(int sens) {
		double nouvelAngle = angleCanon + (double) sens / 20;
		
		if(nouvelAngle <= -1.4)
			angleCanon = -1.4;
		else if(nouvelAngle >= 1.4)
			angleCanon = 1.4;
		else
			angleCanon = nouvelAngle;
		return 50;
	}

	@Override
	public void afficherIcone(Graphics g, Position position) {
		g.drawImage(imageIcone, position.recX(), position.recY(), null);
	}

	@Override
	public void afficherCanon(Graphics g, Position position) {
		AffineTransform tx = AffineTransform.getRotateInstance(angleCanon, accrocheCanon.recX(), accrocheCanon.recY());
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		g.drawImage(op.filter(imageCanon, null), position.recX()-accrocheCanon.recX(), position.recY()-accrocheCanon.recY(), null);
	}

	@Override
	public boolean tirer(Sol sol, Position position, double vitesse, ComposableElementsGraphiques composable) {
		// TODO Auto-generated method stub
		return false;
	}
}
