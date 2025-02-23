package com.hamch.junit;

public class Rational {

    // --- Nos deux attributs ---
    private int numerator;
    private int denominator;
    
    // --- Quatre constructeurs (avec surcharges et délégations) ---
    public Rational() {
        this( 0, 1 );           // Une délégation de constructeur
    }
    
    public Rational( int numerator ) {
        this( numerator, 1 );   // Une autre
    }
    
    public Rational( int numerator, int denominator ) {
        this.setNumerator( numerator );
        this.setDenominator( denominator );
        this.simplify();
    }
    
    public Rational( double value ) {
        this.denominator = 1;
        // On teste s'il y a des chiffres après la virgule
        while( value != (int) value ) {
            // on multiplie chacune des deux parties par 10
            value *= 10;
            this.denominator *= 10;
        }
        // value devient notre numérateur
        this.numerator = (int) value;
        this.simplify();
    }
    
    // --- Nos deux propriétés (getters/setters) ---
    public int getNumerator() {
        return numerator;
    }
    
    public void setNumerator( int numerator ) {
        this.numerator = numerator;
    }
    
    
    public int getDenominator() {
        return denominator;
    }
    
    public void setDenominator( int denominator ) {
        if ( denominator == 0 ) throw new RuntimeException( "denominator cannot be zero" );
        this.denominator = denominator;
    }
    
    // Quelques méthodes de la classe
    
    public Rational add( Rational r2 ) {
        int numerator = this.numerator * r2.denominator + this.denominator * r2.numerator;
        int denominator = this.denominator * r2.denominator;
        return new Rational( numerator, denominator );
    }
    
    public boolean eq( Rational r2 ) {
        return this.numerator * r2.denominator == this.denominator * r2.numerator;
    }
    
    // Pour les détails sur l'algorithme d'Euclide pour le calcul du PGCD
    // https://fr.wikipedia.org/wiki/Algorithme_d%27Euclide#Description_de_l'algorithme
    public void simplify() {
        int a;
        int b;
        
        if ( this.numerator > this.denominator ) {
            a = this.numerator;
            b = this.denominator;
        } else {
            a = this.denominator;
            b = this.numerator;
        }
        
        int rest;
        while( (rest = a % b) != 0 ) {
            a = b;
            b = rest;
        }
        
        this.numerator /= b;
        this.denominator /= b;
    }    
    
    
    @Override 
    public String toString() {
        return String.format( "[%d/%d]", this.numerator, this.denominator );
    }
    
}