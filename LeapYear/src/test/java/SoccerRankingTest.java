
import org.junit.*;

import org.junit.rules.TemporaryFolder;

import java.io.*;


public class SoccerRankingTest {

    private static SoccerRanking soccerRanking;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();


    @Before
    public void setup() throws IOException {
    }

    @Test
    public void successTestCase1() {
        try {
            final InputStream original = System.in;
            final FileInputStream fips = new FileInputStream(new File("src/test/resources/t1.txt"));
            System.setIn(fips);
            SoccerRanking ranking = new SoccerRanking();
            ranking.run();
            System.setIn(original);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assert (false);
        }

    }
    @Test
    public void failTestCase() {
        try {
            final InputStream original = System.in;
            final FileInputStream fips = new FileInputStream(new File("src/test/resources/t2.txt"));
            System.setIn(fips);
            SoccerRanking ranking = new SoccerRanking();
            ranking.run();
            System.setIn(original);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assert(false);
        }
    }

    /*
    @AfterClass
    public void tearDown() {
    }
     
    @BeforeClass
    static void setUp() { 
    }
    */
}