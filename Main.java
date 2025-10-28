import java.util.Random;
import java.util.Scanner;
import static java.lang.Math.abs;

public class Main {
    public static boolean isMoveCorrect(int y, int x, int currentY, int currentX) {
        y--;
        x--;
        if (abs(abs(currentX-x) - abs(currentY - y)) == 1) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Monster monster = new Monster();
        Castle castle = new Castle();
        Person person = new Person();
        Random rand = new Random();
        Scanner sc = new Scanner(System.in);

        System.out.println("Привет, будешь играть? (Ответь \"Да\" или \"Нет\") ");
        String ans;
        boolean isReady = false;
        boolean isCorrectAns = false;
        while (!isCorrectAns){
            ans = sc.next();
            switch (ans) {
                case ("Да"): {
                    System.out.println("Ура!");
                    isReady = true;
                    isCorrectAns = true;
                    break;
                } case ("Нет"): {
                    System.out.print("Жаль, приходи еще!");
                    isCorrectAns = true;
                    break;
                } default: {
                    System.out.println("Некорректный ответ. Поробуйте еще разок \"Да\" или \"Нет\".");
                }
            }
        }
        int boardSize=1;
        if (isReady){
            // играем
            monster.difficultGame= 0;// должно быть 0!!!
            System.out.println("Выбери уровень сложности от 1 до 3");
            while (monster.difficultGame < 1 || monster.difficultGame > 3) {
                monster.difficultGame = sc.nextInt();
                if (monster.difficultGame <= 3 && monster.difficultGame >= 1) {
                    System.out.println("Уровень сложности: " + monster.difficultGame);
                } else {
                    System.out.println("Выбери уровень от 1 до 3!");
                }
            }
            switch (monster.difficultGame){
                case(1):{
                    monster.maxDemon=1;
                    monster.maxTroll=2;
                    monster.maxZombi=3;
                    boardSize=5;
                    person.HP=4;
                    break;
                } case(2):{
                    monster.maxDemon=2;
                    monster.maxTroll=3;
                    monster.maxZombi=4;
                    boardSize=8;
                    person.HP=3;
                    break;
                } case(3):{
                    monster.maxDemon=3;
                    monster.maxTroll=4;
                    monster.maxZombi=5;
                    boardSize=11;
                    person.HP=3;
                    break;
                } default: {}
            }

            System.out.println("Перед началом игры, изучи врагов с которыми тебе возможно прийдется столкнутся" + "\n" +
                    "😈 - демон, его нельзя победить, снимает все сердца" + "\n" +
                    "🧌 - тролль, он дает сложные задания, снимает по два сердца" + "\n" +
                    "🧟 - зомби, он дает простые задания, снимает по одному сердцу");
            System.out.println("Размер поля " + boardSize + "x" + boardSize);
            System.out.println("У тебя " + person.HP + " сердца(-ец)");
        }else {
            return;
        }
            castle.image = "\uD83C\uDFF0";
            person.image = "\uD83E\uDD77"; //🥷
            monster.demon = "\uD83D\uDE08"; //😈
            monster.zombi = "\uD83E\uDDDF\u200D"; //🧟‍
            monster.troll = "\uD83E\uDDCC"; //🧌
            String space = "  ";
            castle.x = 0;
            castle.y = 0;
            person.x = 0;
            person.y = boardSize - 1;

            String[][] board = new String[boardSize][boardSize];
        //заполнение поля пробелами
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = space;
            }
        }

            // генерация сетки
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < monster.maxDemon; j++) {
                int x = rand.nextInt(boardSize-1);
                board[i][x]=monster.demon;
            }for (int j = 0; j < monster.maxTroll; j++) {
                int x = rand.nextInt(boardSize-1);
                board[i][x]=monster.troll;
            }for (int j = 0; j < monster.maxZombi; j++) {
                int x = rand.nextInt(boardSize-1);
                board[i][x]=monster.zombi;
            }
        }
            castle.x = rand.nextInt(boardSize - 1);
            person.x = rand.nextInt(boardSize - 1);
            board[castle.y][castle.x] = castle.image;
            board[person.y][person.x] = person.image;

            // основной блок 'игры'
            while (person.HP > 0 && (person.x != castle.x || person.y != castle.y)) {
                for (int y = 0; y < boardSize; y++) {
                    System.out.print(" +");
                    for (int i = 0; i < boardSize; i++) {
                        System.out.print(" —— +");
                    }
                    System.out.println();
                    System.out.print(" | ");
                    // выводит перемычку
                    // цикл выводит основную часть сетки
                    for (int x = 0; x < boardSize; x++) {
                        System.out.print(board[y][x] + " | ");
                        if (x + 1 == boardSize)
                            System.out.print("\n");
                    }

                }
                System.out.print(" +");
                for (int i = 0; i < boardSize; i++) {
                    System.out.print(" —— +");
                }
                System.out.println();
                System.out.println("Введи координаты передвижения(ряд и столбец):  ");
                int y = sc.nextInt();
                int x = sc.nextInt();
                while(!isMoveCorrect(y-1,x-1,person.y, person.x)){
                    board[person.y][person.x] = space;
                    board[y-1][x-1] = person.image;
                    person.y = y-1;
                    person.x = x-1;
                    if(!(isMoveCorrect(y-1,x-1,person.y, person.x))){
                        System.out.println("Некорректные координаты! Введите повторно!");
                        y = sc.nextInt();
                        x = sc.nextInt();
                    }
                }
                board[person.y][person.x]=space;
                person.x=x-1;
                person.y=y-1;

                //ход

                if (board[person.y][person.x] == monster.zombi){
                    while (!monster.taskZombi() && person.HP > 0){
                        person.HP--;
                        System.out.println("У тебя " + person.HP + " сердца(-ец)");
                    }
                }else if (board[person.y][person.x] == monster.troll){
                    while(!monster.taskTroll() && person.HP > 0){
                        person.HP-=2;
                        System.out.println("У тебя " + person.HP + " сердца(-ец)");
                    }
                }else if (board[person.y][person.x] == monster.demon){
                    person.HP=0;
                    System.out.println("Тебя съел демон");
                    System.out.println("У тебя " + person.HP + " сердца(-ец)");
                }
                if (person.HP > 0){
                    board[person.y][person.x]=person.image;
                }
            }if (castle.x==person.x && castle.y == person.y)
                board[castle.y][castle.x]= castle.image;
            for (int y = 0; y < boardSize; y++) {
                System.out.print(" +");
                for (int i = 0; i < boardSize; i++) {
                    System.out.print(" —— +");
                }// выводит перемычку
                System.out.println();
                System.out.print(" | ");

                // цикл выводит основную часть сетки
                for (int x = 0; x < boardSize; x++) {
                    System.out.print(board[y][x] + " | ");
                    if (x + 1 == boardSize)
                        System.out.print("\n");
                }
            }
            System.out.print(" +");
            for (int i = 0; i < boardSize; i++) {
                System.out.print(" —— +");
            }
            System.out.println();
            if (person.HP <= 0){
                System.out.println("Твой персонаж умер, ты проиграл :(");
            }else{
                System.out.println("Ты победил!");
            }


    }
}
