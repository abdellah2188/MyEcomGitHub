package com.hamch.junit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BidonTest {

	@BeforeEach
	void setUp() throws Exception {
		System.out.println("setUp");
	}

	@AfterEach
	void tearDown() throws Exception {
		System.out.println("tearDown");
	}

	@Test
	void testKo() {
		System.out.println("testKo");
		fail("Not yet implemented");
	}
	
	@Test
	void testOk() {
		System.out.println("testOk");
	}

}
