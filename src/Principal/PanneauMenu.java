package Principal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PanneauMenu extends PanneauBase {

	public PanneauMenu(Dimension taille) {
		super(taille);
		setFocusable(true);
		requestFocusInWindow();
		y = 0;
		
		addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_DOWN) {
					y++;
					repaint();
				} else if(e.getKeyCode() == KeyEvent.VK_UP) {
					y--;
					repaint();
				}
			}
			
			public void keyReleased(KeyEvent e) {}
			
			public void keyTyped(KeyEvent e) {}
		});
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, getWidth(), getHeight());
		//g.drawImage(new ImageIcon("bouton.bmp").getImage(), 20, 20, null);
		/*try {
			g.drawImage(ImageIO.read(new File("bouton.bmp")), 0, 0, null);
		} catch(IOException e) {
			e.printStackTrace();
		}*/
		System.out.println("Dessin avec y =" + y);
		g.setColor(Color.RED);
		g.fillRect(10,15*y,10,10);
		
	}
	
	private int y;
	
	private static final long serialVersionUID = 1L;
}
