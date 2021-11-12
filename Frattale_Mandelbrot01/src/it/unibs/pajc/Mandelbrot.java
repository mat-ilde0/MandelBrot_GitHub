package it.unibs.pajc;

import java.awt.geom.Rectangle2D;

/*
 * classe che rappresenta il model
 */
public class Mandelbrot extends BaseModel{
	
	/*
	 * matrice che memorizza se il tal punto fa convergere la successione o no
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
	 * 
	 * Per evitare ch edue thread eseguano lo stesso codice questo deve essere sincronizzato → non posso dichiarare eval() come un metodo sincronyzed → bisogna trovare un altro modo.
	 * Il problema sta nel fatto che in eval si usa data[][] mentre lo si sta elaborando. Quello che si puù fare è lavorare su una copia di data[][]
	 */
	public void eval(Rectangle2D.Double view ,int resolution) {
		double [][] data= new double[resolution][resolution]; 

		
		double dx = (view.getMaxX() - view.getMinX()) / resolution;   //rappresenta il passo orizzontale
		double dy = (view.getMaxY() - view.getMinY()) / resolution;
		
		double xmin = view.getMinX();
		double ymin = view.getMinY();
		
		//cicli per recuperare informazioni
		for(int i = 0; i<resolution; i++) {
			for(int j = 0; j<resolution; j++) {
				//gli dico di terminare l'attuale elaborazione
				if(Thread.currentThread().isInterrupted())
					return;
				data[i][j] = fMandelbrot(new Complex(j * dx + xmin, i * dy + ymin));
			}
		}
		//solo in questo blocco vado ad aggiornare il data
		synchronized (this) {
			this.data = data;
			fireValuesChange();  //altrimenti non viene notificato a tutti il cambiamento
			
		}
		
	}
	
	/*
	 * si preoccupa di ricevere un punto del piano complesso e determina se ,applicando l'algoritmo che genera la soluzione,
	 * la successione converge o no restituendo un valore.
	 * 
	 */
	double fMandelbrot(Complex c) {
		Complex f = new Complex(0, 0);   //corrisponde a z_0 = 0;

        for (int iterations = 100; iterations >= 0; iterations--) {
            f = f.sqr().sum(c);

            /*
             * ora mi chiedo se il nuovo valoe di f ottenuto è limitato o no -> mi chiedo se è maggiore di un certo valore piccolopiccolopiccolo 
             * e se è così allora sicuramente non converge -> più sarà veloce nel divergere e minore sarà il valore di iterations restituito: capisco velocemente
             * che sta divergendo.
			 * 
             */
            if (f.module2() > 1e5) {
                return iterations;
            }
        }
        return 0;
	}
	
	/*
	 * metodo che consente di riempire la tabella con i risultati trovati 
	 */
	public void fillData() {
		
	}
	
}
