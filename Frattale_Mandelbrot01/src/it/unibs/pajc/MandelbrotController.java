package it.unibs.pajc;

import java.awt.EventQueue;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MandelbrotController {
	
	Mandelbrot model;
	ExecutorService executor = Executors.newFixedThreadPool(5);

	public MandelbrotController(Mandelbrot model) {
		this.model = model;
	}
	
	/*
	 * metodo che viene richiaamto da un altro controller per aggiornare. 
	 * Se voglio il model.eval non venga eseguito sul thread principale ma su un thread a parte -> creo un ExecutorService che andrà a gestire tutti i task.
	 * Per sottomettere il calcolo del frattale a thread diversi posso passare dalla risoluzione minima e poi faccio un ciclo in cui viene preso il task model.eval e inccremento la risoluzione
	 * finche arrivo alla risoluzione massima.
	 * Anche se facendo così potrei superare la reisoluzione massima, moltiplicando r.
	 * Quando passo dei parametri ad una lambda questi devono essere final o effective final -> questo non mi permette di passare r.
	 * Per risolverlo dichiaro una variabile temporanea nel ciclo e la passo come parametro.
	 * 
	 * Questo metodo sottomette un certo numero di task all'executor service -> una volta che sottometto un task magari gli altri sono ancora in esecuzione: adesso non ci sono più conflitti ma se 
	 * cambio risoluzione, i task che venivano fatti prima con la vecchia risoluzione possono essere buttati.
	 * 
	 * I Future oltre che farci capire che un task è terminato permettono di far terminare il task. E' necessario registrare tutti gli oggetti Future.
	 * 
	 */
	
	ArrayList<Future<?>> pendingTask = new ArrayList<>();
	
	public void update(Rectangle2D.Double viewport, int resolution) {
		
		for(Future<?> f: pendingTask) {
			if(!f.isDone())
				//manda un messaggio al thread che indica che appena il thread può deve terminare l'esecuzione, se ne deve preoccupare il task di capire se qualcuno gli ha detto di smettere di fare calcoli
				/*
				 * se voglio permettere che qualcuno interrompa l'esecuzione lo si inserisce da codice e in particolare nelle parti di loop in cui il thread rimane bloccato più spesso
				 */
				f.cancel(true);
		}
		
		int r = 10;   //risoluzione
		
		do {
			r*=5;
			int tmp = Math.min(r, resolution);   //variabile temporanea, minimo per evitare che superi la risoluzione massima
			pendingTask.add(executor.submit(() -> model.eval(viewport, tmp)));
			
		}while(r < resolution);
		
		
	}
	
}

