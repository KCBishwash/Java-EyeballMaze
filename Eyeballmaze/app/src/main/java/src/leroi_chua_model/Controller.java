package src.leroi_chua_model;

import java.util.Scanner;

public class Controller {

	public String[][][] myLevels =  {
							{{"SB   ", "FYX *", "FG   ", "FB  *", "SR  *"},
							 {"CR   ", "DG   ", "CY   ", "CG   ", "CY   "},
							 {"DR   ", "CG   ", "FY   ", "DB  *", "DR  *"},
							 {"     ", "DY @*", "     ", "     ", "     "}},

							{{"SB   ", "FY  *", "FG   ", "FB  *", "SR  *"},
							 {"CR   ", "DGX  ", "CY   ", "CG   ", "CY   "},
							 {"DR   ", "CG   ", "FY   ", "DB  *", "DR  *"},
							 {"     ", "DY @*", "     ", "     ", "     "}},
							
							{{"SBX  ", "FY  *", "FG   "},
							 {"CR   ", "DG   ", "CY   "},
							 {"SR   ", "SY   ", "FY   "},
							 {"     ", "DY @*", "     "}}};
	
	public int[] getUserMove() {
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter a row number: ");	
		int userRow = Integer.parseInt(in.nextLine());
		System.out.println("Please enter a column number: ");	
		int userColumn = Integer.parseInt(in.nextLine());
		int[] userPosition = {userColumn, userRow};
		return userPosition;		
	}	
	
	public void playGame(){
		int counter = 1;		
		for (String[][] aLevelMap : myLevels) {
			String levelName = "level " + counter;
			Level level = new Level(levelName, aLevelMap);
			level.buildMyLevel();			
			boolean levelPassed = false;
			while (levelPassed != true) {
				int[] userMove = this.getUserMove();
				levelPassed = level.updateMyLevel(userMove);							
			}
			counter+=1;	
			System.out.println("\nCongrats on winning: " + levelName  + "\n" + "\nHere comes level " + counter);
		}
	}	
}

