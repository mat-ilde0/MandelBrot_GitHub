package it.unibs.pajc;

import java.awt.geom.Rectangle2D;

/*
 * classe che rappresenta il model
 */
public class Mandelbrot {
	
	/*
	 * matrice che memorizza se il tal punto fa confergere la successione o no
	 */
	
	private double data[][];
	
	public double[][] getData() {
		return data;
	}
	
	/*
	 * metodo che consente di stabilire la convergenza, con parametro la risoluzione su cui si vuole lavorare.
	 * Ho anche bisogno di determinare lo spazio su cui si vuole andare a lavorare -> si deve mappare una griglia, si passa anche
	 * il rettangolo su cui si vuole fare l'analisi e per farlo si può usare un oggetto di awt : i Rectangle2D che definiscono un oggetto di
	 * tipo rettangolare.
	 */
	public void eval(Rectangle2D.Double view ,int resolution) {
		data= new double[resolution][resolution]; 

		
		double dx = (view.getMaxX() - view.getMinX()) / resolution;
		double dy = (view.getMaxY() - view.getMinY()) / resolution;
		
		double xmin = view.getMinX();
		double ymin = view.getMinY();
		
		//cicli per recuperare informazioni
		for(int i = 0; i<resolution; i++) {
			for(int j = 0; j<resolution; j++) {
				data[i][j] = fMandelbrot(new Complex(j * dx + xmin, i * dy + ymin));
			}
		}
		
	}
	
	/*
	 * si preoccupa di ricevere un punto del piano complesso e determina se ,applicando l'algoritmo che genera la soluzione,
	 * la successione converge o no restituendo un valore.
	 * 
	 */
	double fMandelbrot(Complex c) {
		return 0;
	}
	
	/*
	 * metodo che consente di riempire la tabella con i risultati trovati 
	 */
	public void fillData() {
		
	}
	
}
