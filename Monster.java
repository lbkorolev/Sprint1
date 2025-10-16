import java.util.Random;
import java.util.Scanner;

public class Monster {
    String demon;
    String zombi;
    String troll;
    int maxDemon;
    int maxZombi;
    int maxTroll;

    Random rand = new Random();
    Scanner sc = new Scanner(System.in);
    int difficultGame;
    public boolean taskZombi(){
        System.out.println("Реши пример:");
        int x = rand.nextInt(50*difficultGame);
        int y = rand.nextInt(50*difficultGame);
        System.out.println(x + " + " + y + " ?");
        if (sc.nextInt() == x+y) {
            System.out.println("Ты победил монстра!");
            return true;
        }
        else
            System.out.println("Неправильно!");
        return false;
    }
    public boolean taskTroll(){
        System.out.println("Реши пример:");
        int x = rand.nextInt(10*difficultGame);
        int y = rand.nextInt(10*difficultGame);
        System.out.println(x + " * " + y + " ?");
        if (sc.nextInt() == x*y) {
            System.out.println("Ты победил монстра!");
            return true;
        }
        System.out.println("Неправильно!");
        return false;
    }
}
