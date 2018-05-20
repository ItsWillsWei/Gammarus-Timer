import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;


public class GammarusMain extends JFrame{
	private static Dimension MIN_SIZE = new Dimension(500, 300);
	
	public GammarusMain(){
		// Create a new frame
		JFrame frame = new JFrame("Chemventory");
		frame.setIconImage(Toolkit.getDefaultToolkit()
				.getImage("BiologyFrog.png"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GammarusFrame contentPane = new GammarusFrame();
		contentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(contentPane);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(MIN_SIZE);

		// frame should adapt to the panel size
		frame.pack();
		frame.setVisible(true);
	}
	public static void main(String[] args) {
		new GammarusMain();
	}

}
