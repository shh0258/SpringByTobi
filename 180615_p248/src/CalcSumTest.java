import java.io.IOException;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CalcSumTest {
	@Test
	public void sumOfNumbers() throws IOException {
		Calculator calculator = new Calculator();
		int sum = calculator.calcSum(getClass().getResource("number.txt").getPath());
		assertThat(sum, is(10));
	}
	
}
