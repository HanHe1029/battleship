package battleship;

import javax.swing.*;

import java.awt.*;

public class HanComponent extends JComponent {
	private Player player;
	public HanComponent(Player p) {
		super();
		player = p;
	}

	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 345, 345);
		for (int i = 0; i<10; i++)
		{
			String name = "";
			for (int j = 0; j < 10; j++)
			{
				if ((player.getOceanGrid().board[i+1][j+1])==' ')
				{
					name = "water.png";
				}
				else{
					if ((player.getOceanGrid().board[i+1][j+1])=='X')
					{
						name = "hit_ship.png";
					}
					else
					{
						if ((player.getOceanGrid().board[i+1][j+1])=='O')
						{
							name = "computer_shot_miss.png";
						}
						else
							name = "not_hit_ship.png";
					}
				}
				ImageIcon image = new ImageIcon(name);
				g.drawImage(image.getImage(),j*35,i*35,null);
			}
		}
	}

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setPreferredSize(new Dimension(400,400));
		HanComponent h = new HanComponent(new Player(""));
		h.setOpaque(true);
		JPanel bottomGridPanel = new JPanel();
		//bottomGridPanel.setPreferredSize(new Dimension(345,345));
		bottomGridPanel.setBackground(Color.CYAN);
		bottomGridPanel.add(h);
	
		f.add(bottomGridPanel);
		f.setVisible(true);
	}
	
	
	
	
	
}
