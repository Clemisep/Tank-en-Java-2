package Missiles;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CanonNormal extends CanonBasic {
	public CanonNormal(int sens) throws IOException {
		super(
				ImageIO.read(new File(CanonBarrage.tableauLienCanon[0])),
				ImageIO.read(new File(CanonBarrage.tableauLienCanon[0])),
				CanonBarrage.tableauAccrocheCanon[0],
				CanonBarrage.tableauBoutCanon[0],
				-1,
				sens,
				CanonBasic.Type.NORMAL
		);
	}
}
