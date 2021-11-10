package it.unibs.pajc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class MandelbrotPnl extends JPanel implements MouseListener{
	
	private double data[][];
	public void setData(double data[][]) {
		this.data = data;
		repaint();
	}
	
	//----VIEW PORT------
	/*
	 * devo poterla leggere e modificare
	 */
	public static final String VIEWPORT = "VIEWPORT";
	private Rectangle2D.Double viewport; 
	
	public Rectangle2D.Double getViewPort(){
		return viewport;
	}
	
	public void setViewPort(Rectangle2D.Double viewport) {
		Rectangle2D.Double oldValue = this.viewport;
		this.viewport = viewport;
		firePropertyChange(VIEWPORT, oldValue, this.viewport);
	}
	
	/*metodi che permettono di fare lo zoomIn (prendo le coordinate e le moltiplico per un certo fattore) e lo zoomOut
	 * Devo specificare il punto rispetto a cui voglio fare lo zoom.
	 * Per fare lo zoom in dovrò ricarcolare l'estensione della viewport
	 */
	public void zoomIn(Point2D.Double p, double zoom) {
		double xs = viewport.getWidth() / zoom;
		double ys = viewport.getHeight() / zoom;
		
		setViewPort(new Rectangle2D.Double(p.x - xs /2, p.y - ys /2, xs, ys));
	}
	//E' lo zoomIn con il reproco del fattore
	public void zoomOut(Point2D.Double p, double zoom) {
		zoomIn(p, 1./zoom);
	}
	
	public void resetViewPort() {
		setViewPort(new Rectangle2D.Double(-2, -1, 3, 2));
	}
	
	
	/**
	 * Create the panel.
	 */
	public MandelbrotPnl() {
		resetViewPort();
		addMouseListener(this);
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
		
		//Cerco valore massimo ottenuto nella tabella dei valori
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
	
	private Point2D.Double getViewPortPoint(Point p){
		double x = viewport.getWidth() /getWidth() * p.x + viewport.getMinX();
		double y = viewport.getHeight() /getHeight() * p.y + viewport.getMinY();
		
		return new Point2D.Double(x, y);
		
	}

	//---METODI DI MOUSELISTENER---	
	@Override
	public void mouseClicked(MouseEvent e) {
		Point2D.Double p = getViewPortPoint(e.getPoint());
		zoomIn(p, 10);
		//e.isAltDown();
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
