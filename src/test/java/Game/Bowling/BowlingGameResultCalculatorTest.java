package Game.Bowling;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class BowlingGameResultCalculatorTest {

	private BowlingGameResultCalculator calculator;

	void rollManySamePins(int noOfRoll, int pins, BowlingGameResultCalculator calculator) {
		while (noOfRoll > 0) {
			calculator.roll(pins);
			noOfRoll--;
		}
	}

	@Before
	public void setUp() {
		this.calculator = new BowlingGameResultCalculatorImpl();
	}

	@Test
	public void shouldReturnZeroAtBeginning() {
		// when
		int result = calculator.score();
		// then
		assertThat(result).isEqualTo(0);
	}

	@Test
	public void shouldReturnSixWhenThrownSixPins() {
		// given
		calculator.roll(6);
		// when
		int result = calculator.score();
		// then
		assertThat(result).isEqualTo(6);
	}

	@Test
	public void shouldReturnSixWhenThrownFourAndTwoPins() {
		// given
		calculator.roll(4);
		calculator.roll(2);
		// when
		int result = calculator.score();
		// then
		assertThat(result).isEqualTo(6);
	}

	@Test
	public void shouldReturnThirteenAfterThreeRolls() {
		// given
		calculator.roll(2);
		calculator.roll(7);
		calculator.roll(4);
		// when
		int result = calculator.score();
		// then
		assertThat(result).isEqualTo(13);
	}

	@Test
	public void shouldReturnEighteenAfterTwoRounds() {
		// given
		calculator.roll(2);
		calculator.roll(7);
		calculator.roll(4);
		calculator.roll(5);
		// when
		int result = calculator.score();
		// then
		assertThat(result).isEqualTo(18);
	}

	@Test
	public void shouldReturnNullAfterTwentyRollsWithNoPins() {
		// given
		rollManySamePins(20, 0, calculator);
		// when
		int result = calculator.score();
		// then
		assertThat(result).isEqualTo(0);
	}

	@Test
	public void shouldReturnThirtyAfterTwoStrike() {
		// given
		rollManySamePins(2, 10, calculator);
		// when
		int result = calculator.score();
		// then
		assertThat(result).isEqualTo(30);
	}

	@Test
	public void shouldReturnEightyFourWithStrikeBonus() {
		// given
		rollManySamePins(18, 3, calculator);
		rollManySamePins(3, 10, calculator);
		// when
		int result = calculator.score();
		// then
		assertThat(result).isEqualTo(84);
	}

	@Test
	public void shouldReturnOneHundredEightWithoutStrikeBonus() {
		// given
		rollManySamePins(16, 4, calculator);
		rollManySamePins(2, 10, calculator);
		calculator.roll(6);
		calculator.roll(2);
		// when
		int result = calculator.score();
		// then
		assertThat(result).isEqualTo(108);
	}

	@Test
	public void shouldReturnOneHundredFiftyAfterTwentyOneRollsWithFivePins() {
		rollManySamePins(21, 5, calculator);
		assertThat(calculator.score()).isEqualTo(150);
	}
	
	@Test
	public void shouldReturn300WhenPerfectGame() {
		// given
		rollManySamePins(12, 10, calculator);
		// when
		int result = calculator.score();
		// then
		assertThat(result).isEqualTo(300);
	}

	@Test(expected = BowlingException.class)
	public void testBowlingExceptionIfNoOfPinsIsWrong() {
		calculator.roll(12);
	}

	@Test(expected = BowlingException.class)
	public void testBowlingExceptionIfTooMuchRolls() {
		rollManySamePins(22, 5, calculator);
	}
}
