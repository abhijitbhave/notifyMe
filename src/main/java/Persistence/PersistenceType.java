package Persistence;

//An enum class defining the various types of Persistence Types supported by the Application.
//While today we are supporting only Database and File we can easily extend this to other persistence types by expanding on enum
// constants as well as making few changes to the Factory class.

public enum PersistenceType {
    DATABASE, FILE;
}
