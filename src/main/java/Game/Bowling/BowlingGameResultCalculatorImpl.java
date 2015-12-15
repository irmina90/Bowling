package Game.Bowling;

import java.util.ArrayList;
import java.util.List;

public class BowlingGameResultCalculatorImpl implements BowlingGameResultCalculator {

	private static final int MAX_ROUNDS = 10;
	private List<Round> rounds = new ArrayList<Round>();

	private int getCurrentRoundId() {
		return rounds.size() - 1;
	}

	private int getPrevRoundId() {
		return getCurrentRoundId() - 1;
	}

	private int getPrevPrevRoundId() {
		return getCurrentRoundId() - 2;
	}

	private boolean tenthRounds() {
		return rounds.size() == MAX_ROUNDS;
	}

	private boolean emptyListOfRounds() {
		return rounds.isEmpty();
	}
	
	private boolean idExists(int id) {
		return id >= 0 && rounds.size() >= id;
	}

	private Round getRoundById(int roundId) {
		if (!emptyListOfRounds() && idExists(roundId)) {
			return rounds.get(roundId);
		}
		return new Round();
	}

	private Round addNewRoundToArray() {
		Round round = new Round();
		rounds.add(round);
		return round;
	}

	private Round getCurrentOrNewRound() {
		if (emptyListOfRounds() || (getRoundById(getCurrentRoundId()).isRoundFinished() && !tenthRounds())) {
			return addNewRoundToArray();
		}
		return getRoundById(getCurrentRoundId());
	}

	private void addScore(int pins){
		Round currentRound = getCurrentOrNewRound();
		currentRound.addScore(pins);
		addBonus(currentRound);
	}

	private void addBonus(Round currentRound){
		getRoundById(getPrevRoundId()).addBonus(currentRound);
		getRoundById(getPrevPrevRoundId()).addBonus(currentRound);
	}
	
	public void roll(int numberOfPins) {
		if (numberOfPins > 10)
			throw new BowlingException("Wrong number of pins");
		if (isFinished())
			throw new BowlingException("Game is over");

		addScore(numberOfPins);
	}

	public int score() {
		int result = 0;
		for (Round round : rounds) {
			result += round.getSumOfScoresAndBonus();
		}
		return result;
	}

	public boolean isFinished() {
		return tenthRounds() && getRoundById(getCurrentRoundId()).isTenthRoundFinished();
	}

}
