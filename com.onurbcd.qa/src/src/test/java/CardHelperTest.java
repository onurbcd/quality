package src.test.java;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.onurbcd.qa.helper.CardHelper;

public class CardHelperTest {

	@Test
	void organizeTest() {
		CardHelper.organize(Collections.emptyList(), 8);
	}
}
