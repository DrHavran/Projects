public class Settings {
    /**
     * Values that should be changed based on the dataset provided.
     *  [Name] - name of the column that contains the name of the entry
     *  [Class] - name of the column where the classification of the entry is
     *  [Dataset] - name of the dataset
     */
    public static String name = "Name";
    public static String type = "Class";
    public static String dataset = "animal";

    /**
     * Values that effect how the algorithm works
     *  [MinLeafs] - Used when the classification is numerical, since leafs can't be fully clean
     *  [Bagging] - Allows entry sets for each root to be random, best compared with more trees
     *  [Trees] - how many trees get generated (more trees mean bigger accuracy)
     */
    public static int minLeafs = 5;
    public static boolean bagging = true;
    public static int trees = 5;
}