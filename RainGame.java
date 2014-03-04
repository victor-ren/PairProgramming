import java.awt.Graphics2D;
import java.awt.Image;
import sun.audio.*;
import java.io.*;

//UIUC CS125 SPRING 2014 MP. File: RainGame.java, CS125 Project: PairProgramming, Version: 2014-02-24T21:11:30-0600.295816000
/**
 * @author jzhng125,weiren2
 */

public class RainGame {
	
	public static AudioStream as;

	@SuppressWarnings("resource")
	public static void playLoop(String Filename) throws IOException{ 

		InputStream in = new FileInputStream(Filename); 

		AudioStream as = new AudioStream(in);
		//AudioPlayer.player.start(as);
		
		AudioData data = as.getData(); 
		ContinuousAudioDataStream gg= new ContinuousAudioDataStream (data); 
		AudioPlayer.player.start(gg);
		
		}
	
	public static void play(String Filename) throws IOException{
		InputStream in = new FileInputStream(Filename);

		AudioStream as = new AudioStream(in);
		AudioPlayer.player.start(as);
	}
	

	public static void main(String[] args) throws IOException {
		// To get points type your netids above (CORRECTLY-Please double check your partner correctly spells your netid correctly!!)
		// Your netid is the unique part of your @illinois email address
		// Do not put your name or your UIN. 
		// REMEMBER TO COMMIT this file...
	
		int x=0, y=0, dx=0, dy=0, score = 0, level = 1, prelevel = 1, singlescore = 0, cx=-200, cy=465, dcx=14, lifecount = 3;
		String text = "";
		boolean begin = false, gameover = false, congratulate = false;
		
		playLoop("bgm.wav");
		Zen.setFont("Helvetica-32");
		
		while (Zen.isRunning()) {
			
			while (!begin){
				Zen.setColor(0, 191, 255);
				Zen.drawText("Which level would you like begin?(1-9)",40,230);
				Zen.flipBuffer();
				String chooselevel = Zen.getEditText();
				
				for(int i=1;i<=9;i++){
					if(chooselevel.equals(i+"")){
					level = i;
					score = (i)*5-5;
					begin = true;
					}
				}
				Zen.setEditText("");
			}
			
			while(begin){
			
				if(!gameover){
					Zen.setColor(0,0,0);
					Zen.fillRect(0, 0, Zen.getZenWidth(), Zen.getZenHeight());

					
					Zen.setColor(255, 255, 0);
					Zen.drawText(text, x, y);
					
			
					Zen.drawText("Level: "+level,10,30);
					Zen.drawText("Score: "+score,10,60);
					Zen.drawText("Life: ",10,90);
					Image image = Zen.getCachedImage("life.png");
					Graphics2D g = Zen.getBufferGraphics();
					if(lifecount == 3){
					g.drawImage(image, 70, 68, null);
					g.drawImage(image, 110, 68, null);
					g.drawImage(image, 150, 68, null);
					}
					if(lifecount == 2){
						g.drawImage(image, 70, 68, null);
						g.drawImage(image, 110, 68, null);
						}
					if(lifecount == 1){
						g.drawImage(image, 70, 68, null);
					}
					Zen.flipBuffer();
			
			

					if (text.length() == 0) {
						x = (int) (Math.random()*280)+20;
						y = (int) (Math.random()*190)+100;
						
						if (Math.random()>0.5 && level>2) 
							dx = level;
						else if (Math.random()>0.5 && level>2){
							x = (int) (Math.random()*300)+300;
							//y = (int) (Math.random()*250)+250;
							dx = -level;
						}else
							dx=0;
						
						if (Math.random()>0.5 && level>2) 
							dy = level;
						else if (Math.random()>0.5 && level>2){
							//x = (int) (Math.random()*280)+20;
							y = (int) (Math.random()*250)+250;
							dy = -level;
						}
						else
							dy=0;
						
						if (dx==0 && dy==0){
							dx=level;
							dy=level;
						}
						text = "" + (int) (Math.random() * 999);
					}
			
			
					if (level>0 && level<10) {
						x += dx;
						y += dy;
					}else if(level>9 && level%((int)(Math.random()*5)+1)==0){
						if (dx!=0) x += dx+(int)(Math.random()*5);
						else x +=dx;
						
						if (dy!=0) y += dy+(int)(Math.random()*5);
						else y +=dy;
					}else{
						x += dx;
						y += dy;
					}
			
			// Find out what keys the user has been pressing.
					String user = Zen.getEditText();
			// Reset the keyboard input to an empty string
			// So next iteration we will only get the most recently pressed keys.
					Zen.setEditText("");
			
					singlescore = 0;
					for(int i=0;i < user.length();i++) {
						char c = user.charAt(i);
						if(!text.equals("") && !user.equals("")&&c == text.charAt(0)){
							text = text.substring(1,text.length()); // all except first character
							singlescore++;
						}
						else if(!text.equals("") && !user.equals("") && c != text.charAt(0)){
							text = text.substring(1,text.length()); // all except first character
							singlescore--;
						}
					}
					
					if(x>(Zen.getZenWidth()+9*text.length()) || y>(Zen.getZenHeight()+9*text.length())){
						x = (int) (Math.random()*200)+60;
						y = (int) (Math.random()*200)+60;
						singlescore -= text.length();
					}else if (x<(-9*text.length()) || y<(-9*text.length())){
						x = (int) (Math.random()*200)+300;
						y = (int) (Math.random()*200)+200;
						singlescore -= text.length();
					}
			
					if(score%5 == 4 && singlescore > 0){
						level++;
						play("plus.wav");
						prelevel = level-1;
					}
					else if((score%5+singlescore)<0 && singlescore < 0 ){
						level--;
						play("minus.wav");
						prelevel = level+1;
						lifecount--;
					}
					
					Zen.sleep(50);// sleep for 90 milliseconds
					score += singlescore;
			
					if(score<0 || level <=0 || lifecount == 0){
						gameover = true;
					}
					
					if(level%5 == 0 && level != 0 && prelevel < level && text.equals("")){
						congratulate = true;
						prelevel = level;
					}
					
					while(congratulate && cx<(Zen.getZenWidth()+50)){
						Zen.setColor(255, 255, 0);
						Zen.drawText("Level: "+level,10,30);
						Zen.drawText("Score: "+score,10,60);
						Zen.setFont("Helvetica-64");
						Zen.drawText("GG, Keep Up!", cx, cy);
						Zen.setFont("Helvetica-32");
						Zen.drawText("Life: ",10,90);
						Graphics2D g1 = Zen.getBufferGraphics();
						if(lifecount == 3){
							g1.drawImage(image, 70, 68, null);
							g1.drawImage(image, 110, 68, null);
							g1.drawImage(image, 150, 68, null);
							}
						else if(lifecount == 2){
								g1.drawImage(image, 70, 68, null);
								g1.drawImage(image, 110, 68, null);
								}
						else if(lifecount == 1){
								g1.drawImage(image, 70, 68, null);
							}
						cx+=dcx;	
						Zen.flipBuffer();
						Zen.sleep(30);
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
				Zen.setFont("Helvetica-80");
				Zen.drawText("Game Over",110,120);
				play("over.wav");
				Zen.flipBuffer();

            }
		}
	}

}
}
