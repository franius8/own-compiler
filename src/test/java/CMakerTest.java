import Makers.CMaker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CMakerTest {

    @Test
    @DisplayName("Correctly generates a single int")
    void generateInt() {
        CMaker test = new CMaker("5");
        assertEquals("#include <stdio.h> \nint main() {5; return 0; }", test.makeC());
    }

    @Test
    @DisplayName("Correctly generates an assign expression")
    void generatesAssign() {
        CMaker test = new CMaker("a = 2");
        assertEquals("#include <stdio.h> \nint main() {int a=2; return 0; }", test.makeC());
    }
}