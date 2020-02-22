package Persistence;


import Utils.NotifyMeException;

//given that we have more than one type of Persistence and we may extend this in the future, I thought this would be a good opportunity to implement the factory pattern.
//In this factory pattern, PersistenceFactory is the Factory class. The class is driven by a PersistenceType which is defined as enums.
//Based on the value of the enum the factory will either generate an instance of the DatabasePersistence Class or the FilePersistence class at run time,
public class PersistenceFactory {
    //The buildNotification method will be used to build the type of Persistence instance the user has opted for.
    public static Persistence buildPersistenceLayer(PersistenceType persistenceType) {


        Persistence persistence = null;
    switch (persistenceType) {
        //The the type is set to Database, create a Database Persistence Object
            case DATABASE:
                persistence = new DatabasePersistence(PersistenceType.DATABASE);

                break;
        //The the type is set to File, create a File Persistence Object
            case FILE:
                persistence = new FilePersistence(PersistenceType.FILE);
                break;
            //Else throw an error and restart
                default:
                try {
                    throw new NotifyMeException("Invalid option. Restarting the process");
                } catch (NotifyMeException e) {
                    e.printStackTrace();
                }
                break;

        }
        return persistence;
    }


}
