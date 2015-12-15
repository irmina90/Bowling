package Game.Bowling;

import java.util.ArrayList;
import java.util.List;

public class Round {
	private static final int MAX_PINS = 10;
	private static final int MAX_ROLLS = 2;
	private static final int MAX_ROLLS_IN_TENTH_ROUND = 3;
	private List<Integer> scores = new ArrayList<Integer>();
	private List<Integer> bonus = new ArrayList<Integer>();

	private int getFirstScore() {
		return (!roundIsEmpty()) ? scores.get(0) : 0;
	}

	private int getSecondScore() {
		return (roundHasMaxRolls()) ? scores.get(1) : 0;
	}

	private int getSum(List<Integer> list) {
		int result = 0;
		for (int item : list) {
			result += item;
		}
		return result;
	}

	private boolean isSpare() {
		return getSum(scores) == MAX_PINS && roundHasMaxRolls();
	}

	private boolean isStrike() {
		if (!roundIsEmpty())
			return scores.get(0).equals(MAX_PINS);
		else
			return false;
	}

	private boolean roundIsEmpty() {
		return scores.isEmpty();
	}

	private boolean bonusIsEmpty() {
		return bonus.isEmpty();
	}

	private boolean roundHasMaxRolls() {
		return scores.size() == MAX_ROLLS;
	}

	private boolean bonusHasMaxPoints() {
		return bonus.size() == MAX_ROLLS;
	}

	private void addBonusPoints(int points) {
		bonus.add(points);
	}

	private int selectPointsFromNextRound(Round nextRound) {
		if (nextRound.roundHasMaxRolls()) {
			return nextRound.getSecondScore();
		}
		return nextRound.getFirstScore();
	}

	private boolean isBonusFinished() {
		return (isSpare() && !bonusIsEmpty()) || (isStrike() && bonusHasMaxPoints());
	}

	public void addBonus(Round nextRound) {
		if (!isBonusFinished() && !nextRound.roundIsEmpty()) {
			if (isStrike()) {
				addBonusPoints(selectPointsFromNextRound(nextRound));
			} else if (isSpare()) {
				addBonusPoints(nextRound.getFirstScore());
			}
		}
	}

	public void addScore(int noOfPins) {
		scores.add(noOfPins);
	}

	public int getSumOfScoresAndBonus() {
		return getSum(scores) + getSum(bonus);
	}

	public boolean isRoundFinished() {
		return roundHasMaxRolls() || isStrike();
	}

	public boolean isTenthRoundFinished() {
		return scores.size() == MAX_ROLLS_IN_TENTH_ROUND || (roundHasMaxRolls() && !isStrike() && !isSpare());
	}
}
