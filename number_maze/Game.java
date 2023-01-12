import enigma.core.Enigma;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import enigma.console.TextAttributes;
import java.awt.Color;
import java.util.Random;

public class Game {
	public enigma.console.Console cn = Enigma.getConsole();
	public KeyListener klis;
	static Stack leftStack = new Stack(8);
	static Stack rightStack = new Stack(8);
	int leftStackX = 60, leftStackY = 14, rightStackX = 67, rightStackY = 14, topRightX = 58, topRightY = 2;// 60,13
	static int playerNumber = new Random().nextInt(9) + 1;// player number random gelir // ->left baccking orta koordinatlarý
	public int score = 0;
	public int lastScore = 0;
	public boolean flag2 = false;// skor artýþý olmuþ mu kontrolü
	public int count = 0;// süre için count
	public int gameTime = 0;
	public boolean flag = true;// oyuncu rakamý 1 mi diye kontrol
	public boolean flagRed = false;
	public boolean pressed = false;// kullanýcýnýn tuþa basýp basmadýðýný anlamak için(sadece bir kere gerekiyor kýrmýzý sayýlarýn yol bulmasýnda)

	public int keypr; // key pressed?
	public int rkey; // key (for press/release)

	Game() throws Exception {
		klis = new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				if (keypr == 0) {
					keypr = 1;
					rkey = e.getKeyCode();
				}
			}

			public void keyReleased(KeyEvent e) {
			}
		};

		cn.getTextWindow().addKeyListener(klis);
		// --------------------------------------------------------------------------------------

		displayScore(score);
		int px = 15, py = 5; // p harfinin konulduðu koordinat = gameBoard[5][15]
		cn.setTextAttributes(new TextAttributes(Color.blue, Color.BLACK));
		cn.getTextWindow().setCursorPosition(px, py);
		System.out.print(playerNumber);
		cn.setTextAttributes(new TextAttributes(Color.WHITE, Color.BLACK));

		while (true) {

			PathFinding.convertToCooridinate();// her döngüde tekrar yapmasý lazým//kýrmýzý sayýlar için
			if (keypr == 1) {// if keyboard button pressed
				pressed = true;
				PathFinding.deletingPoint(px, py);
				if (playerNumber == 1)// sayý 1 ise hareket edememe kontrolü
					flag = false;

				//////////////////////////
				if (rkey == KeyEvent.VK_LEFT && isBlank(Numbers.gameBoard[py][px - 1]) && flag == true) {// solda boþluk varsa
					cn.getTextWindow().output(px, py, ' ');
					Numbers.gameBoard[py][px] = " ";
					px--;
					Numbers.gameBoard[py][px] = String.valueOf(playerNumber);
				} else if (rkey == KeyEvent.VK_LEFT && isNumber(Numbers.gameBoard[py][px - 1])
						&& Integer.parseInt(Numbers.gameBoard[py][px - 1]) <= playerNumber && flag == true) { // benim sayým soldaki sayýdan büyükse
					cn.getTextWindow().output(px, py, ' '); // sayýyý yiyip stacke atar
					if (leftStack.isFull()) {
						leftStack.pop();
						leftStack.push(Integer.parseInt(String.valueOf(Numbers.gameBoard[py][px - 1].charAt(0))));
					} else {
						leftStack.push(Integer.parseInt(String.valueOf(Numbers.gameBoard[py][px - 1].charAt(0))));
					}

					Score();
					yellowCollect(px - 1, py,
							Integer.parseInt(String.valueOf(Numbers.gameBoard[py][px - 1].charAt(0))));
					redCollect(px - 1, py, Integer.parseInt(String.valueOf(Numbers.gameBoard[py][px - 1].charAt(0))));
					Numbers.gameBoard[py][px] = " ";
					px--;
					Numbers.gameBoard[py][px] = String.valueOf(playerNumber);
					displayStack(leftStack, 60);
				}

				else if (rkey == KeyEvent.VK_LEFT && isNumber(Numbers.gameBoard[py][px - 1])
						&& Integer.parseInt(Numbers.gameBoard[py][px - 1]) > playerNumber) { // benim sayým soldaki sayýdan küçükse
					gameOver();
					break;
				}

				//////////////////////////
				if (rkey == KeyEvent.VK_RIGHT && isBlank(Numbers.gameBoard[py][px + 1]) && flag == true) {
					cn.getTextWindow().output(px, py, ' ');
					Numbers.gameBoard[py][px] = " ";
					px++;
					Numbers.gameBoard[py][px] = String.valueOf(playerNumber);
				} else if (rkey == KeyEvent.VK_RIGHT && isNumber(Numbers.gameBoard[py][px + 1])
						&& Integer.parseInt(Numbers.gameBoard[py][px + 1]) <= playerNumber && flag == true) {// benim sayým saðdaki sayýdan büyüks
					cn.getTextWindow().output(px, py, ' ');
					if (leftStack.isFull()) {
						leftStack.pop();
						leftStack.push(Integer.parseInt(String.valueOf(Numbers.gameBoard[py][px + 1].charAt(0))));

					} else {
						leftStack.push(Integer.parseInt(String.valueOf(Numbers.gameBoard[py][px + 1].charAt(0))));

					}
					Score();
					yellowCollect(px + 1, py,
							Integer.parseInt(String.valueOf(Numbers.gameBoard[py][px + 1].charAt(0))));
					redCollect(px + 1, py, Integer.parseInt(String.valueOf(Numbers.gameBoard[py][px + 1].charAt(0))));
					Numbers.gameBoard[py][px] = " ";
					px++;
					Numbers.gameBoard[py][px] = String.valueOf(playerNumber);
					displayStack(leftStack, 60);
				} else if (rkey == KeyEvent.VK_RIGHT && isNumber(Numbers.gameBoard[py][px + 1])
						&& Integer.parseInt(Numbers.gameBoard[py][px + 1]) > playerNumber && flag == true) {// benim sayým saðdaki sayýdan küçükse
					gameOver();
					break;
				}

				//////////////////////////
				if (rkey == KeyEvent.VK_UP && isBlank(Numbers.gameBoard[py - 1][px]) && flag == true) {
					cn.getTextWindow().output(px, py, ' ');
					Numbers.gameBoard[py][px] = " ";
					py--;
					Numbers.gameBoard[py][px] = String.valueOf(playerNumber);
				} else if (rkey == KeyEvent.VK_UP && isNumber(Numbers.gameBoard[py - 1][px])
						&& Integer.parseInt(Numbers.gameBoard[py - 1][px]) <= playerNumber && flag == true) {
					cn.getTextWindow().output(px, py, ' ');
					if (leftStack.isFull()) {
						leftStack.pop();
						leftStack.push(Integer.parseInt(String.valueOf(Numbers.gameBoard[py - 1][px].charAt(0))));

					} else {
						leftStack.push(Integer.parseInt(String.valueOf(Numbers.gameBoard[py - 1][px].charAt(0))));

					}
					Score();
					yellowCollect(px, py - 1,
							Integer.parseInt(String.valueOf(Numbers.gameBoard[py - 1][px].charAt(0))));
					redCollect(px, py - 1, Integer.parseInt(String.valueOf(Numbers.gameBoard[py - 1][px].charAt(0))));
					Numbers.gameBoard[py][px] = " ";
					py--;
					Numbers.gameBoard[py][px] = String.valueOf(playerNumber);
					displayStack(leftStack, 60);
				} else if (rkey == KeyEvent.VK_UP && isNumber(Numbers.gameBoard[py - 1][px])
						&& Integer.parseInt(Numbers.gameBoard[py - 1][px]) > playerNumber && flag == true) {
					gameOver();
					break;
				}

				//////////////////////////
				if (rkey == KeyEvent.VK_DOWN && isBlank(Numbers.gameBoard[py + 1][px]) && flag == true) {
					cn.getTextWindow().output(px, py, ' ');
					Numbers.gameBoard[py][px] = " ";
					py++;
					Numbers.gameBoard[py][px] = String.valueOf(playerNumber);
				} else if (rkey == KeyEvent.VK_DOWN && isNumber(Numbers.gameBoard[py + 1][px])
						&& Integer.parseInt(Numbers.gameBoard[py + 1][px]) <= playerNumber && flag == true) {
					cn.getTextWindow().output(px, py, ' ');
					if (leftStack.isFull()) {
						leftStack.pop();
						leftStack.push(Integer.parseInt(String.valueOf(Numbers.gameBoard[py + 1][px].charAt(0))));

					} else {
						leftStack.push(Integer.parseInt(String.valueOf(Numbers.gameBoard[py + 1][px].charAt(0))));

					}
					Score();
					yellowCollect(px, py + 1,
							Integer.parseInt(String.valueOf(Numbers.gameBoard[py + 1][px].charAt(0))));
					redCollect(px, py + 1, Integer.parseInt(String.valueOf(Numbers.gameBoard[py + 1][px].charAt(0))));
					Numbers.gameBoard[py][px] = " ";
					py++;
					Numbers.gameBoard[py][px] = String.valueOf(playerNumber);
					displayStack(leftStack, 60);
				} else if (rkey == KeyEvent.VK_DOWN && isNumber(Numbers.gameBoard[py + 1][px])
						&& Integer.parseInt(Numbers.gameBoard[py + 1][px]) > playerNumber && flag == true) {
					gameOver();
					break;
				}

				if (rkey == KeyEvent.VK_W) {// from left backpack to right backpack
					if (!(leftStack.isEmpty()) && !rightStack.isFull()) {
						rightStack.push(leftStack.pop());
						Score();
						Numbers.gameBoard[py][px] = String.valueOf(playerNumber);
					}
				}
				if (rkey == KeyEvent.VK_Q) {// from right backpack to left backpack
					if (!(rightStack.isEmpty()) && !leftStack.isFull()) {
						leftStack.push(rightStack.pop());
						Score();
						Numbers.gameBoard[py][px] = String.valueOf(playerNumber);
					}
				}

				char rckey = (char) rkey;
				cn.getTextWindow().setCursorPosition(px, py);
				cn.setTextAttributes(new TextAttributes(Color.blue, Color.BLACK));

				if (gameTime > 4 && playerNumber == 1 && flag2 == false)
					System.out.print(Numbers.gameBoard[py][px]);
				else
					System.out.print(Numbers.gameBoard[py][px]);

				cn.setTextAttributes(new TextAttributes(Color.WHITE, Color.BLACK));
				keypr = 0; // last action

				redNumbers(px, py);
			}

			// Timing Part
			if (count != 0 && count % 5 == 0 && pressed == true) {
				redNumbersMove(px, py);
			}
			cn.getTextWindow().setCursorPosition(66, 21);
			
			if (count % 10 == 0) {// 1 saniye de artar
				System.out.println(gameTime);
				gameTime++;
			}
			if (count % 40 == 0 && count != 0 && playerNumber == 1) {
				playerNumber = 2;// player number 1 ise ilk 4 saniye hareket edemiyor
				flag = true;
			}
			if (count % 50 == 0 && count != 0)
				placeNewNumbers();// her 5 saniyede yeni computer number ýn gelmesi
			if (count % 20 == 0 && count != 0)// sarý renkli sayýlar her 2 saniye de bir hareket eder
				yellowNumbersMoving();
			Thread.sleep(100);// 0,1 saniye bekle
			count++;
		}
	}

	public void redNumbersMove(int px, int py) {
		String str1, num;
		int xx = 0, yy = 0;
		int xx2 = 0, yy2 = 0;

		for (int i = 0; i < 20; i++) {
			str1 = Numbers.redCoordinate[i];
			String temp = PathFinding.MoveCoordinate[i];

			if (str1 == null || str1.equals("-1") || temp == null)
				break;
			String[] str = str1.split(";");// koordinatýnýn x ve y si str[0] ve str[1]
			xx = Integer.parseInt(str[0]);
			yy = Integer.parseInt(str[1]);
			num = str[2];
			String[] str2 = temp.split(";");
			xx2 = Integer.parseInt(str2[0]);
			yy2 = Integer.parseInt(str2[1]);
			cn.getTextWindow().output(xx, yy, ' ');
			Numbers.gameBoard[yy][xx] = " ";
			cn.getTextWindow().setCursorPosition(xx2, yy2);
			Numbers.gameBoard[yy2][xx2] = num;
			cn.setTextAttributes(Numbers.red);
			System.out.println(num);
			Numbers.redCoordinate[i] = temp + ";" + num;
			if (xx2 == px && yy2 == py) {
				gameOver();
				break;
			}
		}
		cn.setTextAttributes(Numbers.defaultColor);
	}

	// path finding
	public void redNumbers(int px, int py) {
		String str1, num;
		int xx = 0, yy = 0;
		int xx2 = 0, yy2 = 0;

		for (int i = 0; i < 20; i++) {
			str1 = Numbers.redCoordinate[i];

			if (str1 == null || str1.equals("-1"))
				break;

			String[] str = str1.split(";");// koordinatýnýn x ve y si str[0] ve str[1]
			xx = Integer.parseInt(str[0]);
			yy = Integer.parseInt(str[1]);
			num = str[2];
			PathFinding a = new PathFinding(xx, yy);
			a.path(px, py, i);
		}
		PathFinding.reseting();
	}

	// yenilen kýrmýzý sayýnýn oyun alanýndan, kooridinatýnýn silinmesi
	public void redCollect(int x, int y, int num) {
		String str1 = x + ";" + y + ";" + num;
		for (int i = 0; i < 20; i++) {
			if (str1.equalsIgnoreCase(Numbers.redCoordinate[i])) {
				Numbers.redCoordinate[i] = "-1";
				break;
			}
		}
	}

	// yenilen sarý sayýnýn oyun alanýndan, kooridinatýnýn silinmesi
	public void yellowCollect(int x, int y, int num) {
		String str1 = x + ";" + y + ";" + num;
		for (int i = 0; i < 20; i++) {
			if (str1.equalsIgnoreCase(Numbers.yellowCoordinate[i])) {
				Numbers.yellowCoordinate[i] = "-1";// yenildiðini anlamak için döngüde -1 verdim.
				break;
			}
		}
	}

	// inputlistten gelen yeni sarý sayýlar hareket etmiyor
	public void yellowNumbersMoving() {
		String str1;
		int xx = 0, yy = 0;

		for (int i = 0; i < 20; i++) {
			String direction[] = new String[4];
			int k = 0;
			str1 = Numbers.yellowCoordinate[i];
			if (str1 == null || str1.equals("-1"))
				break;
			String[] str = str1.split(";");// koordinatýnýn x ve y si str[0] ve str[1]
			xx = Integer.parseInt(str[0]);
			yy = Integer.parseInt(str[1]);
			String num = str[2];

			// Gidebileceði yönleri tutuyor
			if (Numbers.gameBoard[yy - 1][xx].equals(" ")) {// up
				direction[k] = "u";
				k++;
			}
			if (Numbers.gameBoard[yy][xx + 1].equals(" ")) {// right
				direction[k] = "r";
				k++;
			}
			if (Numbers.gameBoard[yy + 1][xx].equals(" ")) {// down
				direction[k] = "d";
				k++;
			}
			if (Numbers.gameBoard[yy][xx - 1].equals(" ")) {// left
				direction[k] = "l";
				k++;
			}

			int c = new Random().nextInt(k);// bir yön seçer

			if (direction[c].equals("u")) {
				cn.getTextWindow().output(xx, yy, ' ');
				Numbers.gameBoard[yy][xx] = " ";
				cn.getTextWindow().setCursorPosition(xx, yy - 1);
				Numbers.gameBoard[yy - 1][xx] = num;// **
				cn.setTextAttributes(Numbers.yellow);
				System.out.print(num);
				cn.setTextAttributes(Numbers.defaultColor);
				Numbers.yellowCoordinate[i] = xx + ";" + (yy - 1) + ";" + num;
			} else if (direction[c].equals("r")) {
				cn.getTextWindow().output(xx, yy, ' ');
				Numbers.gameBoard[yy][xx] = " ";
				cn.getTextWindow().setCursorPosition(xx + 1, yy);
				Numbers.gameBoard[yy][xx + 1] = num;// **
				cn.setTextAttributes(Numbers.yellow);
				System.out.print(num);
				cn.setTextAttributes(Numbers.defaultColor);
				Numbers.yellowCoordinate[i] = (xx + 1) + ";" + yy + ";" + num;
			} else if (direction[c].equals("d")) {
				cn.getTextWindow().output(xx, yy, ' ');
				Numbers.gameBoard[yy][xx] = " ";
				cn.getTextWindow().setCursorPosition(xx, yy + 1);
				Numbers.gameBoard[yy + 1][xx] = num;// **
				cn.setTextAttributes(Numbers.yellow);
				System.out.print(num);
				cn.setTextAttributes(Numbers.defaultColor);
				Numbers.yellowCoordinate[i] = xx + ";" + (yy + 1) + ";" + num;
			} else if (direction[c].equals("l")) {
				cn.getTextWindow().output(xx, yy, ' ');
				Numbers.gameBoard[yy][xx] = " ";
				cn.getTextWindow().setCursorPosition(xx - 1, yy);
				Numbers.gameBoard[yy][xx - 1] = num;// **
				cn.setTextAttributes(Numbers.yellow);
				System.out.print(num);
				cn.setTextAttributes(Numbers.defaultColor);
				Numbers.yellowCoordinate[i] = (xx - 1) + ";" + yy + ";" + num;
			}
		}
	}

	// döngünün içinde her if in altýnda ayný iþlem yapýldýðý için tek prosedür
	public void Score() {

		score += findScore(leftStack, rightStack);
		displayScore(score);
		clearStack(60);
		clearStack(67);
		displayStack(leftStack, 60);
		displayStack(rightStack, 67);
	}

	public boolean isNumber(String str) {
		try {
			int d = Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public boolean isBlank(String str) {
		return str.equals(" ");
	}

	public void placeNewNumbers() { // sað üstten sil.
		Random rnd = new Random();
		int popped = (int) Numbers.inputList.peek();

		int x = rnd.nextInt(55);
		int y = rnd.nextInt(23);
		while (!isBlank(Numbers.gameBoard[y][x])) {
			x = rnd.nextInt(55);
			y = rnd.nextInt(23);
		}

		cn.getTextWindow().setCursorPosition(x, y);
		if (popped <= 3) {
			cn.setTextAttributes(Numbers.green);
			System.out.print(popped);
			cn.setTextAttributes(Numbers.defaultColor);
		} else if (popped > 3 && popped <= 6) {
			cn.setTextAttributes(Numbers.yellow);
			System.out.print(popped);
			String str = x + ";" + y + ";" + popped;
			Numbers.yellowCoordinate[Numbers.y1] = str;// sarý sayýlarýn koordinatlarýný tutar
			Numbers.y1++;
			cn.setTextAttributes(Numbers.defaultColor);
		} else {
			cn.setTextAttributes(Numbers.red);
			System.out.print(popped);
			String str = x + ";" + y + ";" + popped;
			Numbers.redCoordinate[Numbers.r1] = str;// kýrmýzý sayýlarýn koordinatlarýný tutar
			Numbers.r1++;
			cn.setTextAttributes(Numbers.defaultColor);
		}

		Numbers.gameBoard[y][x] = String.valueOf(popped);
		Numbers.inputList.dequeue();
		Numbers.inputList.enqueue(Numbers.generateRandomNumber());
		Numbers.displayNumberList(Numbers.inputList);

	}

	public static void displayScore(int score) {
		enigma.console.Console cn = Enigma.getConsole();
		cn.getTextWindow().setCursorPosition(66, 19);
		cn.getTextWindow().output("" + score);
	}

	public static void clearStack(int x) {
		enigma.console.Console cn = Enigma.getConsole();
		int count = 14;
		for (int i = 0; i < 8; i++) {
			cn.getTextWindow().setCursorPosition(x, count);
			cn.getTextWindow().output(" ");
			count--;
		}
	}

	public static void displayStack(Stack s1, int x) {
		enigma.console.Console cn = Enigma.getConsole();
		Stack temp1 = new Stack(10);
		int count = 14;

		while (!s1.isEmpty()) {
			temp1.push(s1.pop());
		}
		while (!temp1.isEmpty()) {
			cn.getTextWindow().setCursorPosition(x, count);
			cn.getTextWindow().output("" + (int) temp1.peek());
			s1.push(temp1.pop());
			count--;
		}
	}

	public static int scoreFactor(int number) {
		int score_factor;
		if (number == 1 || number == 2 || number == 3)
			score_factor = 1;
		else if (number == 4 || number == 5 || number == 6)
			score_factor = 5;
		else
			score_factor = 25;
		return score_factor;
	}

	public static int findScore(Stack s1, Stack s2) {
		Stack temp1 = new Stack(10);
		Stack temp2 = new Stack(10);
		int score = 0;
		int size1 = s1.size();
		int size2 = s2.size();

		if (size1 == size2) {
			while (!s1.isEmpty() && !s2.isEmpty()) {
				if ((int) s1.peek() == (int) s2.peek()) {
					int num = (int) s1.peek();
					int score_factor = scoreFactor(num);

					s1.pop();
					s2.pop();

					score += num * score_factor;
					playerNumber++;
					if (playerNumber == 10) {
						playerNumber = 1;
					}
				} else {
					temp1.push(s1.pop());
					temp2.push(s2.pop());
				}
			}
			while (!temp1.isEmpty()) {
				s1.push(temp1.pop());
			}
			while (!temp2.isEmpty()) {
				s2.push(temp2.pop());
			}
		} else if (size1 > size2) {
			while (size1 != size2) {
				temp1.push(s1.pop());
				size1--;
			}
			while (!s1.isEmpty() && !s2.isEmpty()) {
				if ((int) s1.peek() == (int) s2.peek()) {
					int num = (int) s1.peek();
					int score_factor = scoreFactor(num);
					s1.pop();
					s2.pop();
					score += num * score_factor;
					playerNumber++;
					if (playerNumber == 10) {
						playerNumber = 1;
					}
				} else {
					temp1.push(s1.pop());
					temp2.push(s2.pop());
				}
			}
			while (!temp1.isEmpty()) {
				s1.push(temp1.pop());
			}
			while (!temp2.isEmpty()) {
				s2.push(temp2.pop());
			}

		} else {
			while (size1 != size2) {
				temp2.push(s2.pop());
				size2--;
			}
			while (!s1.isEmpty() && !s2.isEmpty()) {
				if ((int) s1.peek() == (int) s2.peek()) {
					int num = (int) s1.peek();
					int score_factor = scoreFactor(num);
					s1.pop();
					s2.pop();
					score += num * score_factor;
					playerNumber++;
					if (playerNumber == 10) {
						playerNumber = 1;
					}
				} else {
					temp1.push(s1.pop());
					temp2.push(s2.pop());
				}
			}
			while (!temp1.isEmpty()) {
				s1.push(temp1.pop());
			}
			while (!temp2.isEmpty()) {
				s2.push(temp2.pop());
			}
		}
		return score;
	}

	public void gameOver() {
		cn.setTextAttributes(new TextAttributes(Color.magenta, Color.black));
		cn.getTextWindow().setCursorPosition(5, 5);
		System.out.println("    _____          __  __ ______    ______      ________ _____    ");
		cn.getTextWindow().setCursorPosition(5, 6);
		System.out.println("   / ____|   /\\   |  \\/  |  ____|  / __ \\ \\    / /  ____|  __ \\   ");
		cn.getTextWindow().setCursorPosition(5, 7);
		System.out.println("  | |  __   /  \\  | \\  / | |__    | |  | \\ \\  / /| |__  | |__) |  ");
		cn.getTextWindow().setCursorPosition(5, 8);
		System.out.println("  | | |_ | / /\\ \\ | |\\/| |  __|   | |  | |\\ \\/ / |  __| |  _  /   ");
		cn.getTextWindow().setCursorPosition(5, 9);
		System.out.println("  | |__| |/ ____ \\| |  | | |____  | |__| | \\  /  | |____| | \\ \\   ");
		cn.getTextWindow().setCursorPosition(5, 10);
		System.out.println("   \\_____/_/    \\_\\_|  |_|______|  \\____/   \\/   |______|_|  \\_\\  ");
		cn.getTextWindow().setCursorPosition(10, 11);
		System.out.println("                                                                  ");

	}
}