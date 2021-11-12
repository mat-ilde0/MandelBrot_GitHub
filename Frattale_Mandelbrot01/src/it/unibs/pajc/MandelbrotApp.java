package it.unibs.pajc;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeListener;
import java.util.EventObject;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MandelbrotApp {

	private JFrame frame;
	public Mandelbrot model;
	private MandelbrotPnl pnlMandelbrot;
	private MandelbrotController controller;
	private JSlider slider;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MandelbrotApp window = new MandelbrotApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MandelbrotApp() {
		model = new Mandelbrot();
		model.addChangeListener(this::modelUpdated);    //si mette in ascolto degli eventi del model stesso
		controller = new MandelbrotController(model);
		
		initialize();
	}
	
		/*
		 * questo è il metodo in cui si va ad aggiornare il mandelbrot. 
		 */
		public void update(EventObject e) {
			controller.update(pnlMandelbrot.getViewPort(), slider.getValue());
		}
		
	/*
	 * Una volta che viene comunicato che i dati sono stati modificati allora viene detto alla view di ridisegnarsi.
	 * Quando il model si è aggiornato, se sono nel thread della gestione eventi allora aggiorno il data all'interno del pannello altrimenti lo metto in coda
	 */
	public void modelUpdated(ChangeEvent e) {
		
		Runnable task = () -> pnlMandelbrot.setData(model.getData().clone());  //così sono sicuro che il pannello lavori su una copia di dati
		
		if(EventQueue.isDispatchThread()) {
			pnlMandelbrot.setData(model.getData());
		}else {
			SwingUtilities.invokeLater(task);
			
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pnlMandelbrot = new MandelbrotPnl();
		frame.getContentPane().add(pnlMandelbrot, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		
		slider = new JSlider ();
		slider.setMaximum(2000);
		slider.setMinimum(50);
		panel.add(slider);
		
		JButton btnNewButton = new JButton("New Butto");
		panel.add(btnNewButton);
		
		/*
		 * bisogna fare l'handler di un click, del movomento di un bottone e del cambiamento di una proprietà
		 */
		/*ActionListener action_listener = e -> model.eval(pnlMandelbrot.getViewPort(), slider.getValue());
		ChangeListener change_listener = e ->  model.eval(pnlMandelbrot.getViewPort(), slider.getValue());
		PropertyChangeListener prop_change_listener = e ->  model.eval(pnlMandelbrot.getViewPort(), slider.getValue());*/
		
		btnNewButton.addActionListener(this::update);
	
		
		slider.addChangeListener(this::update);
		
		pnlMandelbrot.addPropertyChangeListener(MandelbrotPnl.VIEWPORT, this::update);
	}
	

}
