package Missiles;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import Principal.ComposableElementsGraphiques;
import Principal.Position;
import Principal.Sol;
import Tanks.EcouteurTir;

public class CanonBasic implements Canon {
	
	public static final String[] tableauLienCanon = {"src/Images/canon1.png"};
	public static final Position[] tableauAccrocheCanon = {new Position(45, 38)};
	public static final Position[] tableauBoutCanon = {new Position(85, 38)};
	
	private static final double angleOuverture = 1.4;
	
	private final Position accrocheCanon;
	private BufferedImage imageCanon;
	private int munitions;
	private BufferedImage imageIcone;
	private double angleCanon;
	private final int sens;
	private final Position boutCanon;
	
	/**
	 * 
	 * @param image Image du canon.
	 * @param imageIcone Icône représentant le canon.
	 * @param accrocheCanon Position du point d'accroche du canon dans l'image du canon.
	 * @param boutCanon Position du bout du canon, là d'où sort le projectile.
	 * @param munitions Nombre de munitions au départ (-1 pour illimité).
	 * @param sens Le sens du canon : 1 = vers la droite, -1 = vers la gauche.
	 */
	public CanonBasic(BufferedImage image, BufferedImage imageIcone, Position accrocheCanon, Position boutCanon, int munitions, int sens) {
		this.imageCanon = image;
		this.imageIcone = imageIcone;
		this.accrocheCanon = accrocheCanon;
		this.boutCanon = boutCanon;
		this.munitions = munitions;
		this.sens = sens;
		
		if(sens == 1)
			angleCanon = 0;
		else
			angleCanon = Math.PI;
	}

	@Override
	public long bougerCanon(int sens) {
		
		double nouvelAngle = angleCanon + (double) sens / 20 * this.sens;
		
		if(this.sens == 1) {
			
			if(nouvelAngle <= -angleOuverture)
				angleCanon = -angleOuverture;
			else if(nouvelAngle >= angleOuverture)
				angleCanon = angleOuverture;
			else
				angleCanon = nouvelAngle;
		} else {
			
			if(nouvelAngle <= Math.PI - angleOuverture)
				angleCanon = Math.PI - angleOuverture;
			else if(nouvelAngle >= Math.PI + angleOuverture)
				angleCanon = Math.PI + angleOuverture;
			else
				angleCanon = nouvelAngle;
		}
		
		return 50;
	}

	@Override
	public void afficherIcone(Graphics g, Position position) {
		g.drawImage(imageIcone, position.recX(), position.recY(), null);
	}

	@Override
	public void afficherCanon(Graphics g, Position position) {
		/*AffineTransform tx = AffineTransform.getRotateInstance(angleCanon, accrocheCanon.recX(), accrocheCanon.recY());
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		System.out.println(position.recX()-accrocheCanon.recX());
		System.out.println(position.recY()-accrocheCanon.recY());
		g.drawImage(op.filter(imageCanon, null), position.recX()-accrocheCanon.recX(), position.recY()-accrocheCanon.recY(), null);*/
		
		AffineTransform tx = new AffineTransform();
		tx.rotate(angleCanon, accrocheCanon.recX(), accrocheCanon.recY());
		AffineTransformOp opRotated = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		BufferedImage nouvIm = opRotated.filter(imageCanon, null);
		g.drawImage(nouvIm, position.recX()-accrocheCanon.recX(), position.recY()-accrocheCanon.recY(), null);
	}
	
	@Override
	public boolean tirer(Sol sol, Position position, double vitesse, ComposableElementsGraphiques composable, EcouteurTir ecouteurTir) {
		munitions = Math.max(-1, munitions-1);
		if(munitions == 0) return false; // S'il n'y a plus de munitions, on ne peut pas tirer.
		
		int dx = boutCanon.recX()-accrocheCanon.recX();
		int dy = boutCanon.recY()-accrocheCanon.recY();
		
		double angle = angleCanon;
		
		double DX = dy;
		double DY = dx;
		
		double X = DX*Math.cos(angle)+DY*Math.sin(angle);
		double Y = -DX*Math.sin(angle)+DY*Math.cos(angle);
		
		double x = Y;
		double y = X;
		
		
		//int boutCanonX = (int) (position.recX()/*-accrocheCanon.recX()*/-dy*Math.sin(angleCanon)+dx*Math.cos(angleCanon));
		//int boutCanonY = (int) (position.recY()/*-accrocheCanon.recY()*/-dy*Math.sin(angleCanon)+dx*Math.cos(angleCanon));
		
		int boutCanonX = (int) (position.recX()+x);
		int boutCanonY = (int) (position.recY()+y);
		
		Missile missile = new Missile(
				composable,
				sol,
				new Position(boutCanonX, boutCanonY),
				angleCanon, vitesse, ecouteurTir
				);
		
		// TODO S'occuper du tir
		return true;
	}
}
