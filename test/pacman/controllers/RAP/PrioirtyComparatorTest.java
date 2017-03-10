package pacman.controllers.RAP;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class PrioirtyComparatorTest {
    private PriorityComparator pc;

    @BeforeEach
    public void setup(){
        pc = new PriorityComparator();
    }

    @Test
    public void compareTest(){
        assertEquals(1, pc.compare(0, 1));
        assertEquals(-1, pc.compare(1, 0));
        assertEquals(0, pc.compare(0, 0));

    }
}
