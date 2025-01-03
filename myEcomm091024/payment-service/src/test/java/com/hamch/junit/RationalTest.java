package com.hamch.junit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RationalTest {

	@Test
    public void testAddition() {
        
        // On prépare le scénario de test
        Rational r1 = new Rational( 1, 3);
        Rational r2 = new Rational( 2, 1);
        Rational result = r1.add( r2 );
        
        // On vérifie les résultats
        assertEquals( 7 /* Valeur attendue */, result.getNumerator() /* Valeur constatée */);
        assertEquals( 3 /* Valeur attendue */, result.getDenominator() /* Valeur constatée */);
        
    }

	@Test
    public void testSimplify() {
    
        // On prépare le scénario de test
        Rational r = new Rational( 5*7*11*13, 11*13*17 );
        r.simplify();

        // On vérifie les résultats
        assertEquals( 35, r.getNumerator() );
        assertEquals( 17, r.getDenominator() );
    
    }
	
	 @Test
	    public void testBadDenominator() {
	        assertThrows( RuntimeException.class, () -> {
	            new Rational( 1, 0 );
	        });
	    }
}
