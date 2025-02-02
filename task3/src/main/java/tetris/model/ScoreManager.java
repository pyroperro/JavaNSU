package tetris.model;

public class ScoreManager {
    private int score = 0;
    private int level = 1;
    private int linesCleared = 0;

    public void updateScore(int clears) {
        switch (clears) {
            case 1:
                score += 40 * level;
                break;
            case 2:
                score += 100 * level;
                break;
            case 3:
                score += 300 * level;
                break;
            case 4:
                score += 1200 * level;
                break;
            default:
                break;
        }
        linesCleared += clears;

        if (linesCleared >= level * 10) {
            level++;
        }
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

}
