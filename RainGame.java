//UIUC CS125 SPRING 2014 MP. File: RainGame.java, CS125 Project: PairProgramming, Version: 2014-02-24T21:11:30-0600.295816000
/**
 * @author jzhng125,weiren2
 */
public class RainGame {

	public static void main(String[] args) {
		// To get points type your netids above (CORRECTLY-Please double check your partner correctly spells your netid correctly!!)
		// Your netid is the unique part of your @illinois email address
		// Do not put your name or your UIN. 
		// REMEMBER TO COMMIT this file...
	
		int x=0, y=0, dx=0, dy=0, score = 0, level = 1, prelevel = 1, singlescore = 0, cx=-200, cy=465, dcx=14;
		String text = "";
		boolean begin = false, gameover = false, congratulate = false;
		
		Zen.setFont("Helvetica-32");
		
		while (Zen.isRunning()) {
			while (!begin){
				Zen.setColor(0, 191, 255);
				Zen.drawText("Which level would you like begin?(1-9)",50,230);
				Zen.flipBuffer();
			String chooselevel = Zen.getEditText();
			for(int i=1;i<=9;i++){
				if(chooselevel.equals(i+"")){
					level = i;
					score = i*10-10;
					begin = true;
				}
			}
			Zen.setEditText("");
			}
			while(begin){
			
            if(!gameover){
			Zen.setColor(0,0,0);
			Zen.fillRect(0, 0, Zen.getZenWidth(), Zen.getZenHeight());

			Zen.setColor(64, 224, 208);
			Zen.drawText(text, x, y);
			
			Zen.drawText("Level: "+level,10,30);
			Zen.drawText("Score: "+score,10,60);
			Zen.flipBuffer();
			
			

			if (text.length() == 0) {
				x = (int) (Math.random()*200);
				y = (int) (Math.random()*200);
				dx = 1;
				dy = 1;
				text = "" + (int) (Math.random() * 999);
			}
			
			
			x += dx;
			y += dy;
			
			// Find out what keys the user has been pressing.
			String user = Zen.getEditText();
			// Reset the keyboard input to an empty string
			// So next iteration we will only get the most recently pressed keys.
			Zen.setEditText("");
			
			singlescore = 0;
			for(int i=0;i < user.length();i++) {
				char c = user.charAt(i);
				if(!user.equals("") && c == text.charAt(0)){
					text = text.substring(1,text.length()); // all except first character
					singlescore++;
				}
				else if(!user.equals("") && c != text.charAt(0)){
					text = text.substring(1,text.length()); // all except first character
					singlescore--;
				}
			}
			if(x>(Zen.getZenWidth()+9*text.length()) || y>(Zen.getZenHeight()+9*text.length())){
				x = (int) (Math.random()*200);
				y = (int) (Math.random()*200);
			    singlescore -= text.length();}	
			
			if(score%10 == 9 && singlescore > 0){
				level++;
				prelevel = level-1;}
			else if((score%10+singlescore)<0 && singlescore < 0 ){
				level--;
				prelevel = level+1;}
			Zen.sleep(50);// sleep for 90 milliseconds
			score += singlescore;
			
			if(score<0 || level <=0){
				gameover = true;
			}
			if(level%10 == 0 && level != 0 && prelevel < level){
				congratulate = true;
				prelevel = level;
			}
			while(congratulate && cx<(Zen.getZenWidth()+50)){
			Zen.setColor(64, 224, 208);
			Zen.drawText("Level: "+level,10,30);
			Zen.drawText("Score: "+score,10,60);
			Zen.setFont("Helvetica-64");
			Zen.drawText("GG, Keep Up!", cx, cy);
			Zen.setFont("Helvetica-32");
			cx+=dcx;
			Zen.flipBuffer();
			Zen.sleep(40);
			}
            
			if(cx>=(Zen.getZenWidth()+50)){
				cx=-200;
				congratulate = false;
			}
			}
 
			else{
            
			Zen.setColor(0, 0, 0);
			Zen.fillRect(0, 0, Zen.getZenWidth(), Zen.getZenHeight());
			
			Zen.setColor(255, 0, 0);
			Zen.drawText("Game Over",235,240);
			Zen.flipBuffer();
            }
			}
		}

	}

	}

