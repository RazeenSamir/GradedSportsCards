import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

public class Testing {
    private CollectionManager manager;
    private GradedSportsCard card1;
    private GradedSportsCard card2;
    private GradedSportsCard card3;

    @BeforeEach
    void setUp() {
        manager = new CollectionManager();
        card1 = new GradedSportsCard(9, "Basketball", "Michael Jordan");
        card2 = new GradedSportsCard(8, "Football", "Tom Brady");
        card3 = new GradedSportsCard(10, "Baseball", "Babe Ruth");
    }

    @Test
    void testConstructor() {
        assertEquals("", manager.toString());
    }

    @Test
    void testConstructorWithScanner() {
        String data = "10\nBaseball\nBabe Ruth\n";
        Scanner scanner = new Scanner(data);
        CollectionManager managerWithInput = new CollectionManager(scanner);
        assertTrue(managerWithInput.contains(new GradedSportsCard(10, "Baseball", "Babe Ruth")));
    }

    @Test
    void testAdd() {
        manager.add(card1);
        assertTrue(manager.contains(card1));
    }

    @Test
    void testContains() {
        manager.add(card1);
        manager.add(card2);
        assertTrue(manager.contains(card1));
        assertFalse(manager.contains(new GradedSportsCard(7, "Hockey", "Wayne Gretzky")));
    }

    @Test
    void testToString() {
        manager.add(card1);
        manager.add(card2);
        manager.add(card3);

        String result = manager.toString();
        assertTrue(result.contains("Michael Jordan"));
        assertTrue(result.contains("Tom Brady"));
        assertTrue(result.contains("Babe Ruth"));
    }
    
    @Test
    void testSave() throws FileNotFoundException {
        manager.add(card1);
        manager.add(card2);
        
        File file = new File("test_output.txt");
        PrintStream printStream = new PrintStream(file);
        manager.save(printStream);
        
        Scanner scanner = new Scanner(file);
        boolean michaelJordanFound = false;
        boolean tomBradyFound = false;
    
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains("Michael Jordan")) {
                michaelJordanFound = true;
            }
            if (line.contains("Tom Brady")) {
                tomBradyFound = true;
            }
        }
        assertTrue(michaelJordanFound);
        assertTrue(tomBradyFound);
    }

    @Test
    void testFilter() {
        manager.add(card1);
        manager.add(card2);
        manager.add(card3);
        
        List<GradedSportsCard> filtered = manager.filter(10);
        assertEquals(1, filtered.size());
    }

    @Test
    void testGradedSportsCardConstructor() {
        GradedSportsCard card = new GradedSportsCard(7, "Hockey", "Wayne Gretzky");
        assertEquals(7, card.getGrade());
        assertEquals("Hockey", card.toString().contains("Hockey") ? "Hockey" : "");
        assertEquals("Wayne Gretzky", card.toString().contains("Wayne Gretzky") ? "Wayne Gretzky" : "");
    }

    @Test
    void testGetGrade() {
        assertEquals(9, card1.getGrade());
    }

    @Test
    void testGradedSportsCardEquals() {
        GradedSportsCard duplicate = new GradedSportsCard(9, "Basketball", "Michael Jordan");
        assertTrue(card1.equals(duplicate));
        assertFalse(card1.equals(card2));
    }

    @Test
    void testGradedSportsCardCompareTo() {
        assertTrue(card1.compareTo(card2) < 0);
        assertTrue(card3.compareTo(card1) < 0);
        assertEquals(0, card1.compareTo(new GradedSportsCard(9, "Basketball", "Michael Jordan")));
    }
    
    @Test
    void testGradedSportsCardHashCode() {
        int hash1 = card1.hashCode();
        int hash2 = card1.hashCode();
        int hash3 = card2.hashCode();
        
        assertEquals(hash1, hash2);
        assertNotEquals(hash1, hash3);
    }
}
