
/**
 * This project is a (very) simple version of the game Battleship
 * (made to familiarize myself with javax.swing).
 * 
 * There is currently one mode where the computer randomly places 5 ships on a grid: 
 * an Aircraft Carrier (5 cells), a Battleship (4 cells), a Submarine (3 cells),
 * a Destroyer (3 cells), and a Patrol Boat (2 cells); the player has to destroy 
 * all the computer's ships without knowing their position.
 * 
 * @author Clem
 *
 */
public class BattleshipMain 
{
	
	public static void main(String [] arg)
	{
		PlayerOnly game = new PlayerOnly();
	}
}
