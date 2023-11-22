package bowling;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class PartieMultiTest {
	PartieMulti parties;
	String[] joueurs = {"j1", "j2", "j3"};

	@BeforeEach
	public void setUp() {
		parties = new PartieMulti();
		parties.demarreNouvellePartie(joueurs);
	}

	@Test
	void testPartieNondemarre() {
		PartieMulti partiesTest = new PartieMulti();
		try {
			partiesTest.enregistreLancer(7);
			fail("La partie n'a pas commencée.");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	void testDemarrerSansJoeur() {
		PartieMulti partiesTest = new PartieMulti();
		String[] j = {};
		try {
			partiesTest.demarreNouvellePartie(j);
			fail("Il faut des joueurs pour une partie.");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	void testinitiationPartie() {
		PartieMulti partiesTest = new PartieMulti();
		assertEquals("Prochain tir : joueur j1, tour n° 1, boule n° 1", parties.demarreNouvellePartie(joueurs), "partie pas correctement initialisé");
	}

	@Test
	void testApresUnStrike() {
		assertEquals("Prochain tir : joueur j2, tour n° 1, boule n° 1", parties.enregistreLancer(10));
	}

	@Test
	void testScoreUnknownPlayer() {
		try {
			parties.scorePour("j4");
			fail("Le joueur n'existe aps");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	void testScorePartieClassique() {
		parties.enregistreLancer(5);//j1
		parties.enregistreLancer(3);//j1
		parties.enregistreLancer(10);//j2
		parties.enregistreLancer(10);//j3
		parties.enregistreLancer(7);//j1
		parties.enregistreLancer(3);//j1
		parties.enregistreLancer(5);//j2
		parties.enregistreLancer(2);//j2
		parties.enregistreLancer(10);//j3
		parties.enregistreLancer(3);//j1
		parties.enregistreLancer(4);//j1
		parties.enregistreLancer(0);//j2
		parties.enregistreLancer(0);//j2
		parties.enregistreLancer(7);//j3


		assertEquals(28, parties.scorePour("j1"));
		assertEquals(24, parties.scorePour("j2"));
		assertEquals(51, parties.scorePour("j3"));
	}

	@Test
	void testPartieTerminee() {
		int cpt = 0;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 6; j++) {
				parties.enregistreLancer(1);
			}
		}
		assertEquals("Partie terminée", parties.enregistreLancer(1));

	}
}