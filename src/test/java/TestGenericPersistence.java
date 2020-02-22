import static org.junit.Assert.assertTrue;
import Persistence.*;
import org.junit.Test;

public class TestGenericPersistence {


    //Testing the Generic message builder method. The idea here is that since the method accepts a Generic datatype, as long as the datatype has a
    // toString method supported, the messageBuilder method should work and return the required String.
    //Run the test for Email and SMS route leveraging Factory design pattern.
    Persistence persistence = null;

    @Test
    public void testGenericDatabasePersistence() {
        persistence = new DatabasePersistence(PersistenceType.DATABASE);
        assertTrue(persistence instanceof DatabasePersistence);
    }

    @Test
    public void testGenericFilePersistence() {
        persistence = new FilePersistence(PersistenceType.FILE);
        assertTrue(persistence instanceof FilePersistence);
    }

}
