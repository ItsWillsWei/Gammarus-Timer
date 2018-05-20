import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class GammarusFrame extends JPanel{
	
	private Dimension FRAME_SIZE = new Dimension(1000,700);
	private boolean started = false;
	private long startTime = 0;
	private long lastTime = 0;
	private long[] times = {0,0};
	private int timeLimit = 0;
	private boolean isLeft = false;
	private boolean timeStarted = false;
	
	private Color[] COLOURS = {new Color(255,230,120), new Color(240,255,150), new Color(0, 255, 0, 55), new Color(255,0,0, 15)};
	
	private JPanel left, right, center, bottom;
	private JLabel desc;
	
	GammarusFrame(){
		super(new BorderLayout());
		setPreferredSize(FRAME_SIZE);
		
		//Create content panels
		left = createLeft();
		right = createRight();
		center = new JPanel();
		center.setLayout(new GridLayout(0,2));
		center.add(left);
		center.add(right);
		
		//Create bottom panels
		bottom = createBottom();
		
		add(center, BorderLayout.CENTER);
		add(bottom, BorderLayout.PAGE_END);
		center.addKeyListener(new KeyHandler());
		center.requestFocusInWindow();
		
	}
	
	public void focus(){
		center.requestFocusInWindow();
	}
	
	/**
	 * 
	 * @return
	 */
	public JPanel createLeft(){
		JPanel left = new JPanel();
		left.setBackground(Color.GRAY.brighter());
		
		desc = new JLabel("Desc");
		left.add(desc);
		return left;
	}
	
	/**
	 * 
	 * @return
	 */
	public JPanel createRight(){
		JPanel right = new JPanel();
		right.setBackground(Color.DARK_GRAY.brighter());
		return right;
	}
	
	/**
	 * 
	 * @return
	 */
	public JPanel createBottom(){
		JPanel bottom = new JPanel();
		bottom.setBackground(Color.LIGHT_GRAY);
		
		bottom.setLayout(new GridLayout(0,4));
		//Create buttons
		JLabel timeLabel = new JLabel("Time (s): ");
		
		JTextField timeText = new JTextField();
		timeText.setText("120");
		timeText.setHorizontalAlignment(SwingConstants.LEFT);
		
		//START BUTTON
		JButton startButton = new JButton("Start! =)");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Check info
				String time = timeText.getText();
				if(started){
					JOptionPane.showMessageDialog(null, "Timer is already on.", "Already started!", JOptionPane.ERROR_MESSAGE);
				}
				else if (!isNumber(time))
				{
					//display fail message
					JOptionPane.showMessageDialog(null, "That ain't a number foo!", "Check yo numbers mate!", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					desc.setText("Desc");
					left.repaint(0);
					started = true;
					timeLimit = Integer.parseInt(time);
					//startTime = System.currentTimeMillis();
					//lastTime = startTime;
					times[0] = 0;
					times[1] = 0;
					focus();
					new Thread(new TimerThread()).start();
				}
			}
		});
		
		//CANCEL BUTTOM
		JButton cancelButton = new JButton("!!Cancel!!");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// CANCEL
				started = false;
			}
		});
		
		bottom.add(timeLabel);
		bottom.add(timeText);
		bottom.add(startButton);
		bottom.add(cancelButton);
		return bottom;
	}
	
	/**
	 * Checks to see if the string is composed of an integer digits
	 * @param test
	 * @return
	 */
	public boolean isNumber(String test){
		for(int character = 0; character < test.length(); character++){
			if(!Character.isDigit(test.charAt(character)))
					return false;
		}
		return true;
	}
	
	public void changeColour(JPanel panel, Color colour){
		panel.setBackground(colour);
		panel.repaint();
	}
	
	private class TimerThread implements Runnable{
		//private boolean timerOn;
		TimerThread(){
			//timerOn = true;
		}
		
		public void run(){
			while(started){
				try{
				Thread.sleep(10);
				
				//STOP if the timer is over the specified seconds
				if(timeStarted && System.currentTimeMillis() - startTime > timeLimit *1000){
					started = false;
					timeStarted = false;
					Thread.sleep(10);
					
					//Add the remaining time
					if(isLeft){
						times[0] += timeLimit*1000 - times[0] - times[1];
					}
					else{
						times[1] += timeLimit*1000 - times[0] - times[1];
					}
					System.out.println("Left : " + times[0]/1000.0);
					System.out.println("Right : " + times[1]/1000.0);
					desc.setText("Left: " + times[0]/1000.0 + " Right: " + times[1]/1000.0);
					left.repaint(0);
				}
				
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
		}
	}
	
	/**
	 * Handles key events
	 * @author Will
	 *
	 */
	private class KeyHandler extends KeyAdapter{
		public void keyPressed(KeyEvent event){
			if(started){
				
				if(event.getKeyCode() == KeyEvent.VK_LEFT){
					System.out.println("L");
					if (!timeStarted){
						timeStarted = true;
						startTime = System.currentTimeMillis();
						lastTime = startTime;
						isLeft = true;
						System.out.println("Start");
						changeColour(left, COLOURS[0]);
					}
					else if(!isLeft)
					{
						isLeft = true;
						long now = System.currentTimeMillis();
						times[1] += now -lastTime;
						lastTime = now;
					}
				}
				else if(event.getKeyCode() == KeyEvent.VK_RIGHT){
					System.out.println("R");
					if (!timeStarted){
						timeStarted = true;
						startTime = System.currentTimeMillis();
						lastTime = startTime;
						isLeft = false;
						System.out.println("Started");
						changeColour(left, COLOURS[0]);
					}
					else if(isLeft){
						isLeft = false;
						long now = System.currentTimeMillis();
						times[0] += now - lastTime;
						lastTime = now;
					}
				}
			}
		}
		
	}
}
