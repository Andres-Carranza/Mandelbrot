import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class Mandelbrot extends JPanel implements KeyListener{
	private double x = -650;
	private double y = -450;
	private double scale = 100.;
	private int iterations = 100;
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		BufferedImage screen = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

		for(int x = 0; x< screen.getWidth(); x++) {
			for(int y=0; y< screen.getHeight(); y++ ) {
				screen.setRGB(x, y,converges(2,iterations,(x+this.x)/scale,(y+this.y)/scale));
			}
		}

		g.drawImage(screen, 0, 0, this);
		g.drawString(iterations+"" ,50,50);

	}

	private int converges(int limit, int iterations, double x, double y) {
		ComplexNumber c = new ComplexNumber(0,0);
		ComplexNumber c0 = new ComplexNumber(x,y);			

		for(int i =0; i< iterations; i++) {
			if(c.abs()>limit) {
				double percent = 1.0* i/iterations;
				
				int ri = 0;
				int rf = 0;
				int r = (int) ((rf - ri) * percent);

				int gi = 0;
				int gf =255;
				int g = (int) ((gf - gi) * percent);

				int bi = 255;
				int bf =0;
				int b = (int) ((bf- bi) * percent);
				
				return new Color(r + ri,g+gi,b+bi).getRGB();
			}

			c.multiply(c);
			c.add(c0);
		}

		return 0;
	}


	public static void main( String[] args) {
		JFrame frame = new JFrame("Mandelbrot");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);

		Mandelbrot panel = new Mandelbrot();
		frame.setFocusable(true);
		frame.addKeyListener(panel);
		frame.add(panel);

		frame.setVisible(true);
		//panel.converges(2, 10, 0, 1);
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			System.exit(0);

		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			x+=20;
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			x-=20;

		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			y+=20;
		if(e.getKeyCode() == KeyEvent.VK_UP)
			y-=20;

		Point p = MouseInfo.getPointerInfo().getLocation();
		//System.out.println((p.x/scale+x)+","+(p.y/scale+y));

		if(e.getKeyCode() == KeyEvent.VK_A) {


			scale*=.95;

			x+= p.x;
			x*=.95;
			x-=p.x;

			y+= p.y;
			y*=.95;
			y-=p.y;
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			scale*=1.05;

			x+= p.x;
			x*=1.05;
			x-=p.x;

			y+= p.y;
			y*=1.05;
			y-=p.y;
		}

		if(e.getKeyCode() == KeyEvent.VK_Q)
			iterations--;
		if(e.getKeyCode() == KeyEvent.VK_W)
			iterations++;
		
		repaint();

	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}



}
