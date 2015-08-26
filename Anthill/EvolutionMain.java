
/**
 * The context of this exercise is to study the development of an anthill 
 * in an environment without predators by developing simulation software with Java.
 * 
 * The anthill must be only composed of:
 * - Ant larvae: non­adult ants;
 * - Male ants: adult ants whose job is to fertilize the queen;
 * - Worker ants: adult ants whose job is to protect and feed the anthill;
 * - Queen ants: adult ants whose job is to lay larvae.
 * 
 * All ants, adult or larva, must have:
 * - An identifier, which is its birth rank (1 for the first ant, 2 for second one, …);
 * - An age, which is the number of days since its birth;
 * - A maximum age, whose value is in function of his caste: 
 * 		- 50 days for queens and workers;
 * 		- 20 days for males;
 * 		- 10 days for larvae.
 * 
 * Each type of ant must be represented by a Java class.
 * 
 * An anthill represents an ant society. It must be represented by a Java class too.
 * This class must provide a constructor with four parameters:
 * - the number of queens;
 * - the number of males;
 * - the number of workers;
 * - the number of larvae.
 * These numbers must be used to initialize the anthill.
 * 
 * Each day, the anthill must evolve following these rules: 
 * - All ants become older.
 * - If an adult ant becomes too old, it dies.
 * - If a larva becomes old enough, it becomes an adult.
 * - Each queen lays ten larvae if at least one male is still alive.
 * -  A larva has a 2/20 chance to be a future male and a 1/20 chance to be a future queen 
 * (the type of adult larvae it will become is decided at birth). 
 * 
 * Just after the launch of this simulation, the user must choose how  many 
 * queens, workers, males and larvae he wants in the anthill.
 * To jump to the next day, the user must just have to press enter. 
 * He must also be able to enter a number to jump several days at once.
 * At each jump, you must display some statistics about the anthill to the user:
 * - the age of the anthill,
 * - the population of each ant type,
 * - the number of new birth during the last jump,
 * - the number of dead during the last jump.
 * 
 *  * @author Clem
 *
 */
public class EvolutionMain
{
	public static void main(String [] arg)
	{
		EvolutionGUI window = new EvolutionGUI();
		window.setVisible(true);
	}
}
