package Missiles;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CanonBarrage extends CanonBasic {
	public CanonBarrage(int sens) throws IOException {
		super(
				ImageIO.read(new File(CanonBarrage.tableauLienCanon[1])),
				ImageIO.read(new File(CanonBarrage.tableauLienCanon[1])),
				CanonBarrage.tableauAccrocheCanon[1],
				CanonBarrage.tableauBoutCanon[1],
				5,
				sens,
				CanonBasic.Type.BARRAGE
		);
	}
}
