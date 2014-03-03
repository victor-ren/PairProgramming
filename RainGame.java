//UIUC CS125 SPRING 2014 MP. File: RainGame.java, CS125 Project: PairProgramming, Version: 2014-02-24T21:11:30-0600.295816000
/**
 * @author jzhng125
 */
public class RainGame {

	public static void main(String[] args) {
		// To get points type your netids above (CORRECTLY-Please double check your partner correctly spells your netid correctly!!)
		// Your netid is the unique part of your @illinois email address
		// Do not put your name or your UIN. 
		// REMEMBER TO COMMIT this file...
	
		int x=0, y=0, dx=0, dy=0, score = 0, level = 1, singlescore = 0;
		String text = "";
		long startTime =System.currentTimeMillis();
		
		Zen.setFont("Helvetica-32");
		while (Zen.isRunning()) {

			if (text.length() == 0) {
				x = (int) (Math.random()*200);
				y = (int) (Math.random()*200);
				dx = 2*level;
				dy = 2*level;
				text = "" + (int) (Math.random() * 999);
			}
			Zen.setColor(255, 0, 255);
			Zen.fillRect(0, 0, Zen.getZenWidth(), Zen.getZenHeight());

			Zen.setColor(0, 255, 0);
			Zen.drawText(text, x, y);
			
			Zen.drawText("Level: "+level,10,30);
			Zen.drawText("Score: "+score,10,60);
			Zen.flipBuffer();
			
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
				if(c == text.charAt(0)){
					text = text.substring(1,text.length()); // all except first character
					singlescore++;
				}
				else if(c != text.charAt(0)){
					text = text.substring(1,text.length()); // all except first character
					singlescore--;
				}
			}
			if(x>(Zen.getZenWidth()+9*text.length()) || y>(Zen.getZenHeight()+9*text.length())){
				x = (int) (Math.random()*200);
				y = (int) (Math.random()*200);
			    singlescore -= text.length();}	
			score += singlescore;
			if(score%10 == 0 && singlescore > 0){
				level++;}
			else if((score%10+singlescore)<0 && singlescore < 0 ){
				level--;}
			Zen.sleep(50);// sleep for 90 milliseconds

		}
	}

}
