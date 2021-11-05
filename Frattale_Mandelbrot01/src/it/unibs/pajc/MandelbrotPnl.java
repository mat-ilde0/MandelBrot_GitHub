package it.unibs.pajc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class MandelbrotPnl extends JPanel {
	
	private double data[][];
	public void setData(double data[][]) {
		this.data = data;
		repaint();
	}
	
	/**
	 * Create the panel.
	 */
	public MandelbrotPnl() {

	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		if(data == null)
			return;
		
		//ricavo il numero di elementi contenuti nella matrice
		int sx = data.length;      
		int sy = data[0].length;
		
		/*
		 * trasformazione di scala.
		 * 
		 */
		g2.scale((double)getWidth()/sx,  (double)getHeight()/sy);
		
		//disegno array
		double vmax = data[0][0];
		for(int i = 0; i<sx; i++) {
			for(int j = 0; j<sx; j++) {
				if(vmax < data[i][j])
					vmax = data[i][j];
			}
		}
		
		for(int i = 0; i<sx; i++) {
			for(int j = 0; j<sx; j++) {
				/*
				 * devo determinare il colore dell'elemento -> prelevo il dato e mi serve conoscere quanto vale l'elemento di valore massimo
				 * che dipende dal numero di iterazioni che faccio per verificare la convergenza dell'elemento
				 */
				
				double v = data[i][j];
				
				int gray = (int)(v/vmax * 255);
				Color c = new Color(gray, gray, gray);
				
				g2.setColor(c);
				g2.fillRect(j, i, 1, 1);
			}
		}
		
	}

}
