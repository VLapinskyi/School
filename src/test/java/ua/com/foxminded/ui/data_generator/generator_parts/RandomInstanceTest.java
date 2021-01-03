package ua.com.foxminded.ui.data_generator.generator_parts;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Test;

class RandomInstanceTest {

	@Test
	void testGetRandomInstance() {
		assertTrue(RandomInstance.getRandomInstance() instanceof Random);
	}

}
