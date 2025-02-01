package tetris.model;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class TetrominoBag {
    private List<TetrominoType> bag;

    public TetrominoBag() {
        bag = new ArrayList<>();
        refillBag();
    }

    private void refillBag() {
        if (bag.size() <= 3) {
            ArrayList<TetrominoType> secondBag = new ArrayList<>();
            Collections.addAll(secondBag, TetrominoType.values());
            Collections.shuffle(secondBag);
            bag.addAll(secondBag);
        }
    }

    public TetrominoType getNextPiece() {
        if (bag.size() <= 3) {
            refillBag();
        }
        return bag.removeFirst();
    }

    public List<TetrominoType> previewNext(int count) {
        return bag.subList(0, Math.min(count, bag.size()));
    }
}
