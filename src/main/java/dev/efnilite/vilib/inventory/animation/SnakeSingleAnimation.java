package dev.efnilite.vilib.inventory.animation;

import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * An animation which snakes from the top left to the middle.
 * <p>
 * ---------   x--------   x--------   x--xxxxxx
 * --------- > x-------- > x-------x > x-------x > etc.
 * ---------   xxxx-----   xxxxxxxxx   xxxxxxxxx
 * <p>
 * Expected duration: 4 ticks
 * Expected time: 0.2s
 *
 * @author Efnilite
 */
public final class SnakeSingleAnimation extends MenuAnimation {

    private static final int DURATION_TICKS = 4;

    private int amountPerTick;
    private int rows;
    private int currentSlot = 0;
    private final Vector heading = new Vector(0, 0, -1);
    private final List<Integer> path = new ArrayList<>();

    @Override
    public void init(int rows) {
        this.rows = rows;
        amountPerTick = (rows * 9 - 1) / DURATION_TICKS;

        path.add(0);
        for (int i = 0; i < rows * 9 - 1; i++) { // loop amount of slots times
            int slot = getNextSlot();
            path.add(slot);
        }

        int i = 0;
        while (path.size() > 0) {
            add(i, getFromPath());
            i++;
        }
    }

    private List<Integer> getFromPath() {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < amountPerTick; i++) {
            if (path.size() == 0) {
                break;
            }
            result.add(path.get(0));
            path.remove(0);
        }
        return result;
    }

    private int getNextSlot() {
        int horizontal = (int) heading.getX();
        int vertical = (int) heading.getZ();

        int next;
        if (horizontal != 0) { // get next horizontal
            next = currentSlot + horizontal;
        } else { // get next vertical
            next = currentSlot - (vertical * 9);
        }

        if (available(next)) { // check if slot is available or existent
            currentSlot = next;
            return currentSlot;
        } else {
            heading.rotateAroundY(-90); // if not, rotate vector and try again
            return getNextSlot();
        }
    }

    private boolean available(int slot) {
        if (slot > rows * 9 - 1 || slot < 0) {
            return false;
        }
        return !path.contains(slot);
    }
}