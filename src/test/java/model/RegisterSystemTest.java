package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

//@Disabled ("Ei vielä tehty")
class RegisterSystemTest {

	@Test
	void testRegister() {
		Kayttaja kayttaja = new Kayttaja();
		kayttaja.setSähköposti("user@mailcom");
		assertEquals("user@mailcom", kayttaja.getSähköposti(), "Wrong email");
		}
}
