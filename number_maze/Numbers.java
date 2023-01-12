import enigma.console.TextAttributes;
import enigma.core.Enigma;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Numbers {
	static enigma.console.Console cn = Enigma.getConsole("Number Maze", 80, 24, 20, 0);
	static Queue inputList = new Queue(10000);//ben de çok büyük olunca hata veriyor*****
	static String[][] gameBoard = new String[23][55];
	static TextAttributes green = new TextAttributes(Color.GREEN, Color.BLACK);
	static TextAttributes red = new TextAttributes(Color.RED, Color.BLACK);
	static TextAttributes yellow = new TextAttributes(Color.YELLOW, Color.BLACK);
	static TextAttributes blue = new TextAttributes(Color.BLUE, Color.BLACK);
	static TextAttributes defaultColor = new TextAttributes(Color.white, Color.BLACK);
	static String[] yellowCoordinate = new String[20000];
	static int y1 = 0;
	static String[] redCoordinate = new String[20000];
	static int r1;

	public static void fileRead() throws IOException {

		File file = new File("maze.txt");
		BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
		String line;
		int index = 0;

		while ((line = read.readLine()) != null) {
			for (int i = 0; i < 55; i++) {
				gameBoard[index][i] = line.charAt(i) + "";
			}
			index++;
		}
		read.close();
		randomCoordinate25Numbers();
		writegameBoard();
		fillNumberList(inputList);
		displayNumberList(inputList);
		screen();
	}
	

	public static void writegameBoard() {
		// oyun basý 1 kere gameboard yaz

		for (int i = 0; i < gameBoard.length; i++) {
			for (int j = 0; j < gameBoard[0].length; j++) {
				if (!gameBoard[i][j].equals("#")) {
					if (gameBoard[i][j].equals("1") || gameBoard[i][j].equals("2") || gameBoard[i][j].equals("3")) {
						cn.setTextAttributes(green);
						System.out.print(gameBoard[i][j]);
						cn.setTextAttributes(defaultColor);
					}else if (gameBoard[i][j].equals("4") || gameBoard[i][j].equals("5") || gameBoard[i][j].equals("6")) {
						cn.setTextAttributes(yellow);
						System.out.print(gameBoard[i][j]);
                        String str = j + ";" + i + ";" + gameBoard[i][j];
                        yellowCoordinate[y1] = str;//sarý sayýlarýn koordinatlarýný tutar
                        y1++;
						cn.setTextAttributes(defaultColor);
					}else if (gameBoard[i][j].equals("7") || gameBoard[i][j].equals("8") || gameBoard[i][j].equals("9")) {
						cn.setTextAttributes(red);
						System.out.print(gameBoard[i][j]);
						String str = j + ";" + i + ";" + gameBoard[i][j];
                        redCoordinate[r1] = str;//kýrmýzý sayýlarýn koordinatlarýný tutar
                        r1++;
						cn.setTextAttributes(defaultColor);
					}
					else {
						System.out.print(gameBoard[i][j]);
					}
				} else {

					System.out.print(gameBoard[i][j]);
				}
			}
			System.out.println();
		}
	}

	
	
	public static void randomCoordinate25Numbers() {
		// oyun baþý 25 random gameboarda ekle
		Random rnd = new Random();

		int x = rnd.nextInt(55);
		int y = rnd.nextInt(23);

		for (int i = 0; i < 25; i++) {
			while (!gameBoard[y][x].equals(" ")) {
				x = rnd.nextInt(55);
				y = rnd.nextInt(23);
			}
			int random_num = generateRandomNumber();
			gameBoard[y][x] = String.valueOf(random_num);
		}
	}

	
	
	public static void randomCoordinateJustaNumber() {
		Random rnd = new Random();
		int x = rnd.nextInt(55);
		int y = rnd.nextInt(23);
		while (!gameBoard[y][x].equals(" ")) {
			x = rnd.nextInt(55);
			y = rnd.nextInt(23);
		}
		gameBoard[y][x] = String.valueOf(generateRandomNumber());
	}

	
	
	public static int generateRandomNumber() {
		// random 1 sayý üret
		Random rnd = new Random();
		int[] high = { 1, 2, 3 };
		int[] mid = { 4, 5, 6 };
		int[] low = { 7, 8, 9 };
		int number;
		int possibility = rnd.nextInt(101);
		int index = rnd.nextInt(3);

		if (possibility <= 5) {
			number = low[index];
		} else if (possibility >= 6 && possibility <= 25) {
			number = mid[index];
		} else {
			number = high[index];
		}
		return number;
	}

	
	
	public static void fillNumberList(Queue q) {
		for (int i = 0; i < 10; i++) {
			q.enqueue(generateRandomNumber());
		}
	}

	
	
	public static void displayNumberList(Queue q) {
		enigma.console.Console cn = Enigma.getConsole();
		clearNumberList();
		cn.getTextWindow().setCursorPosition(58, 1);
		System.out.print("<<<<<<<<<<<<<<<<<<<");
		cn.getTextWindow().setCursorPosition(58, 2);

		for (int i = 0; i < q.size(); i++) {
			System.out.print(q.peek() + " ");
			q.enqueue(q.dequeue());
		}

		cn.getTextWindow().setCursorPosition(58, 3);
		System.out.print("<<<<<<<<<<<<<<<<<<<");

	}

	
	
	public static void clearNumberList() {
		enigma.console.Console cn = Enigma.getConsole();
		cn.getTextWindow().setCursorPosition(58, 2);

		for (int i = 58; i <= 76; i += 2) {
			cn.getTextWindow().setCursorPosition(i, 2);
			System.out.print(" ");
		}
	}

	
	
	public static void screen() {
		cn.getTextWindow().setCursorPosition(58, 6);
		System.out.print(" BackPacks");
		for (int i = 7; i < 15; i++) {
			cn.getTextWindow().setCursorPosition(58, i);
			System.out.print("|   |  |   |");
		}
		cn.getTextWindow().setCursorPosition(58, 15);
		System.out.print("+---+  +---+");
		cn.getTextWindow().setCursorPosition(58, 16);
		System.out.print("Left   Right");
		cn.getTextWindow().setCursorPosition(58, 17);
		System.out.print("  Q      W");
		cn.getTextWindow().setCursorPosition(58, 19);
		System.out.print("Score:");
		cn.getTextWindow().setCursorPosition(58, 21);
		System.out.print("Time:");
	}
}
