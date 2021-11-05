package it.unibs.pajc;

/**
 * definizione di un tipo di dato complesso
 * @author User
 *
 */

public class Complex {

	double re;
	double im;
	
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	public Complex sum(Complex c) {
		return new Complex(this.re + c.getRe(), this.im + c.getIm());
	}
	
	//eleva al quadrato il numero
	public Complex sqr() {
		return new Complex(re*re -im*im, 2*re*im);
	}
	
	/*
	 * calcola la norma del numero complesso -> non interessa calcolarne la radice
	 */
	public double module2() {
		return re*re + im*im;
	}

	public double getRe() {
		return re;
	}

	public double getIm() {
		return im;
	}
	
	
}
