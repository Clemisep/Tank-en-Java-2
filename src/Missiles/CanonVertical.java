package Missiles;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CanonVertical extends CanonBasic {
	public CanonVertical(int sens) throws IOException {
		super(
				ImageIO.read(new File(CanonBarrage.tableauLienCanon[2])),
				ImageIO.read(new File(CanonBarrage.tableauLienCanon[2])),
				CanonBarrage.tableauAccrocheCanon[2],
				CanonBarrage.tableauBoutCanon[2],
				1,
				sens,
				CanonBasic.Type.VERTICAL
		);
	}
}
