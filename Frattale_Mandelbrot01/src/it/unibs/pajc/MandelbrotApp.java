package it.unibs.pajc;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.geom.Rectangle2D;

import javax.swing.*;

public class MandelbrotApp {

	private JFrame frame;

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
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MandelbrotPnl pnlMandelbrot = new MandelbrotPnl();
		frame.getContentPane().add(pnlMandelbrot, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		
		JSlider slider = new JSlider ();
		slider.setMaximum(2000);
		slider.setMinimum(50);
		panel.add(slider);
		
		JButton btnNewButton = new JButton("New Butto");
		panel.add(btnNewButton);
		
		btnNewButton.addActionListener(e -> {
			pnlMandelbrot.model.eval(new Rectangle2D.Double(-2, -1, 3, 2), 100);
			pnlMandelbrot.repaint();
		});
		
		
		slider.addChangeListener(e -> {
			pnlMandelbrot.model.eval(new Rectangle2D.Double(-2, -1, 3, 2), 100);
			pnlMandelbrot.repaint();
		});
	}

}
