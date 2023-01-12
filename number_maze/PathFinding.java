import java.awt.Color;

import enigma.console.TextAttributes;
import enigma.core.Enigma;

public class PathFinding {

	public int x;
	public int y;	
	//-1 bo�luk  -2 duvar  -3 denendi	
	public Stack path = new Stack(10000);//b�t�n path findingi yapmak i�in
	public Stack temp = new Stack(10000);//�izilecek yolu tutar	
	public static Stack delete = new Stack(10000);//noktalar�n silinece�i yerleri tutar
	public static Stack flag = new Stack(10000);//flag i true ya d�nd�r�lm�� noktalar� tutar
	public static Stack point_3 = new Stack(10000);//-3 lenmi� noktalar� tutar
	public int count;//bir noktadan ka� tane yol oldu�unu saymak i�in 
    public static Coordinate[][] board = new Coordinate[23][55];
    public String[] array = new String[20];//k�rm�z� say�lar�n hareket edece�i konumu tutar
    public static enigma.console.Console cn = Enigma.getConsole();
	public String move = null;//k�rm�z� say�n�n hareket edece�i kordinat� tutar
	
	public static String[] MoveCoordinate = new String[2000];
	public static int moveCount = 0;
	
	public PathFinding(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {return x;}
	public void setX(int x) {this.x = x;}
	public int getY() {return y;}
	public void setY(int y) {this.y = y;}
	

	public void path(int playerX, int playerY, int k) {//bakma s�ras� sa�,alt,sol,�st

		Boolean b = false;
		String str = x + ";" + y;//k�rm�z� say�n�n k.	
		String str2;
		int tempX = 0, tempY = 0;		
		count = 0;				
		path.push(str);//�nce ilk konumda stacke eklenir
		
		while(!(path.isEmpty())) {	
			
			//o an ki konumdan ka� farkl� y�ne gidilebiliyorsa bunlar stack e at�l�yor
			if(playerX > x && playerY > y) {//oyuncu say�s�n�n sol �st�ndeyse
				if(board[y-1][x].getCharacter().equals("-1")) {//�st
					str = x + ";" + (y-1);
					path.push(str);
					count++;				
				}if(board[y][x-1].getCharacter().equals("-1")) {//sol
					str = (x-1) + ";" + y;
					path.push(str);
					count++;				
				}if(board[y+1][x].getCharacter().equals("-1")) {//alt
					str = x + ";" + (y + 1);
					path.push(str);
					count++;				
				}if(board[y][x+1].getCharacter().equals("-1")) {//sa�
					str = (x + 1) + ";" + y;
					path.push(str);
					count++;							
				}
			}
			else if(x > playerX && playerY > y) {//oyuncu say�s�n�n sa� �st�ndeyse
				if(board[y-1][x].getCharacter().equals("-1")) {//�st
					str = x + ";" + (y-1);
					path.push(str);
					count++;				
				}if(board[y][x+1].getCharacter().equals("-1")) {//sa�
					str = (x + 1) + ";" + y;
					path.push(str);
					count++;							
				}if(board[y+1][x].getCharacter().equals("-1")) {//alt
					str = x + ";" + (y + 1);
					path.push(str);
					count++;				
				}if(board[y][x-1].getCharacter().equals("-1")) {//sol
					str = (x-1) + ";" + y;
					path.push(str);
					count++;				
				}
			}
			else if(playerX > x && y > playerY) {//oyuncu say�s�n�n sol alt�ndaysa
				if(board[y+1][x].getCharacter().equals("-1")) {//alt
					str = x + ";" + (y + 1);
					path.push(str);
					count++;				
				}if(board[y][x-1].getCharacter().equals("-1")) {//sol
					str = (x-1) + ";" + y;
					path.push(str);
					count++;				
				}if(board[y-1][x].getCharacter().equals("-1")) {//�st
					str = x + ";" + (y-1);
					path.push(str);
					count++;				
				}if(board[y][x+1].getCharacter().equals("-1")) {//sa�
					str = (x + 1) + ";" + y;
					path.push(str);
					count++;							
				}
			}
			else if(x > playerX && y > playerY) {//oyuncu say�s�n�n sa� alt�ndaysa
				if(board[y+1][x].getCharacter().equals("-1")) {//alt
					str = x + ";" + (y + 1);
					path.push(str);
					count++;				
				}if(board[y][x+1].getCharacter().equals("-1")) {//sa�
					str = (x + 1) + ";" + y;
					path.push(str);
					count++;							
				}if(board[y-1][x].getCharacter().equals("-1")) {//�st
					str = x + ";" + (y-1);
					path.push(str);
					count++;				
				}if(board[y][x-1].getCharacter().equals("-1")) {//sol
					str = (x-1) + ";" + y;
					path.push(str);
					count++;				
				}
			}
			else if(playerX == x) {
				if(playerY > y) {
					if(board[y-1][x].getCharacter().equals("-1")) {//�st
						str = x + ";" + (y-1);
						path.push(str);
						count++;				
					}if(board[y][x+1].getCharacter().equals("-1")) {//sa�
						str = (x + 1) + ";" + y;
						path.push(str);
						count++;							
					}if(board[y][x-1].getCharacter().equals("-1")) {//sol
						str = (x-1) + ";" + y;
						path.push(str);
						count++;				
					}if(board[y+1][x].getCharacter().equals("-1")) {//alt
						str = x + ";" + (y + 1);
						path.push(str);
						count++;				
					}
				}
				else {
					if(board[y+1][x].getCharacter().equals("-1")) {//alt
						str = x + ";" + (y + 1);
						path.push(str);
						count++;				
					}if(board[y][x-1].getCharacter().equals("-1")) {//sol
						str = (x-1) + ";" + y;
						path.push(str);
						count++;				
					}if(board[y][x+1].getCharacter().equals("-1")) {//sa�
						str = (x + 1) + ";" + y;
						path.push(str);
						count++;							
					}if(board[y-1][x].getCharacter().equals("-1")) {//�st
						str = x + ";" + (y-1);
						path.push(str);
						count++;				
					}
				}
			}
			else if(playerY == y) {
				if(playerX > x) {
					if(board[y][x-1].getCharacter().equals("-1")) {//sol
						str = (x-1) + ";" + y;
						path.push(str);
						count++;				
					}if(board[y+1][x].getCharacter().equals("-1")) {//alt
						str = x + ";" + (y + 1);
						path.push(str);
						count++;				
					}if(board[y-1][x].getCharacter().equals("-1")) {//�st
						str = x + ";" + (y-1);
						path.push(str);
						count++;				
					}if(board[y][x+1].getCharacter().equals("-1")) {//sa�
						str = (x + 1) + ";" + y;
						path.push(str);
						count++;							
					}
				}
				else {
					if(board[y][x+1].getCharacter().equals("-1")) {//sa�
						str = (x + 1) + ";" + y;
						path.push(str);
						count++;							
					}if(board[y+1][x].getCharacter().equals("-1")) {//alt
						str = x + ";" + (y + 1);
						path.push(str);
						count++;				
					}if(board[y-1][x].getCharacter().equals("-1")) {//�st
						str = x + ";" + (y-1);
						path.push(str);
						count++;				
					}if(board[y][x-1].getCharacter().equals("-1")) {//sol
						str = (x-1) + ";" + y;
						path.push(str);
						count++;				
					}
				}
			}	
			else {
				if(board[y][x+1].getCharacter().equals("-1")) {//sa�
					str = (x + 1) + ";" + y;
					path.push(str);
					count++;							
				}if(board[y+1][x].getCharacter().equals("-1")) {//alt
					str = x + ";" + (y + 1);
					path.push(str);
					count++;				
				}if(board[y][x-1].getCharacter().equals("-1")) {//sol
					str = (x-1) + ";" + y;
					path.push(str);
					count++;				
				}if(board[y-1][x].getCharacter().equals("-1")) {//�st
					str = x + ";" + (y-1);
					path.push(str);
					count++;				
				}
			}
			
			
			//e�er noktadan birden fazla y�ne gidilebiliyorsa flag true ya �evrilir ki bir di�er y�n denenebilsin 
			if(count > 1) {
				board[y][x].setFlag(true);	
				flag.push(x + ";" + y);
				str2 = (String)path.pop();
				temp.push(str2);
				board[y][x].setCharacter("-3");
				point_3.push(x + ";" + y);//gidilmi� noktalar� tutan stack
				
				String[] arr = str2.split(";");			
				tempX = Integer.parseInt(arr[0]); 
				tempY = Integer.parseInt(arr[1]); 
			}			
			else if(count == 1) {
				str2 = (String)path.pop();
				temp.push(str2);
				board[y][x].setCharacter("-3");
				point_3.push(x + ";" + y);
				
				String[] arr = str2.split(";");			
				tempX = Integer.parseInt(arr[0]); 
				tempY = Integer.parseInt(arr[1]); 
			}
			else if(count == 0) {		
				while(!(temp.isEmpty())) {
					String strr = (String) temp.peek();	
					String[] arr1 = strr.split(";");			
					tempX = Integer.parseInt(arr1[0]); 
					tempY = Integer.parseInt(arr1[1]);
					
					if(!(board[tempY][tempX].getFlag() == true))
						temp.pop();		
					else
						break;
				}									
				if(!(path.isEmpty())) {
					str2 = (String)path.pop();
					temp.push(str2);
					board[y][x].setCharacter("-3");
					point_3.push(x + ";" + y);
					
					String[] arr = str2.split(";");			
					tempX = Integer.parseInt(arr[0]); 
					tempY = Integer.parseInt(arr[1]); 
				}
				else {
					System.out.println();
				}								
			}						
			x = tempX;
			y = tempY;				
			count = 0;		
			
			if(playerX == x && playerY == y) {//path bulundu mu kontrol�
				temp.pop();
				b = true;
				break;
			}			
		}
		if(b == true)//path bulunduysa noktalar yaz�l�r
	      writingPoint(k);
	}
	
		
	public void writingPoint(int i) {//i ka��nc� k�rm�z� oldu�unu tutar
		int xx,yy;
		String a;			
		while(!(temp.isEmpty())) {
		    a = (String)temp.pop();
			delete.push(a);
			String[] arr = a.split(";");	
			xx = Integer.parseInt(arr[0]); 
			yy = Integer.parseInt(arr[1]); 
			cn.setTextAttributes(new TextAttributes(Color.WHITE,Color.BLACK)); 
			cn.getTextWindow().output(xx, yy, '.');
		}	
		move = (String)delete.peek();
		MoveCoordinate[i] = move;
	}
	

	public static void deletingPoint(int px, int py) {
		String a;
		int xx, yy;	
		int count = 0;
		
		while(!(delete.isEmpty())) {
			if(count == 0) {
				delete.pop();
			}

			if(delete.isEmpty())
				break;
			else
     		    a = (String)delete.pop();

			if(a == null) {
				break;
			}
				
			String[] arr = a.split(";");	
			xx = Integer.parseInt(arr[0]); 
			yy = Integer.parseInt(arr[1]);
			cn.setTextAttributes(new TextAttributes(Color.WHITE,Color.BLACK)); 
			count++;
			if(px == xx && py == yy) {//oyuncu say�s� hareket etti�i i�in onun oldu�u konuma denk gelirse onu sildirmemesi i�in
				break;
			}
			else
			   cn.getTextWindow().output(xx, yy, ' ');		
		}		
	}
	
	
	public static void reseting() {		
		String a;
		int x,y;
		while(!(flag.isEmpty())){
			a = (String)flag.pop();
			String[] arr = a.split(";");	
			x = Integer.parseInt(arr[0]); 
			y = Integer.parseInt(arr[1]);		
			board[y][x].setFlag(false);
		}
		
		while(!(point_3.isEmpty())){
			a = (String)point_3.pop();
			String[] arr = a.split(";");	
			x = Integer.parseInt(arr[0]); 
			y = Integer.parseInt(arr[1]);			
			board[y][x].setCharacter("-1");
		}		
		
	}
	
	
	//gameboarddaki her noktay� bir coordinate nesnesine �evirir
	//-1 bo�luk  -2 duvar  -3 denendi
	public static void convertToCooridinate() {
		for(int i = 0; i < 23; i++) {
			for(int j = 0; j < 55; j++) {				
				Coordinate c = new Coordinate(i, j, "");
				c.setCharacter(Numbers.gameBoard[i][j]);			
				if(Numbers.gameBoard[i][j].equals(" "))
					c.setCharacter("-1");
				else if(Numbers.gameBoard[i][j].equals("#"))
					c.setCharacter("-2");								
				board[i][j] = c;								
			}
		}
	}
	

	
}
