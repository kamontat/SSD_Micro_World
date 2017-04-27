import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Game extends JFrame {
	
	public static final int SIZE = 1000;
	
	private JPanel panel;
	private List<IUnit> units = new ArrayList<IUnit>();
	private Thread thread;
	private boolean running;
	
	public Game() {
		initGameData();
		panel = new JPanel() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				drawBackground(g);
				drawUnits(g);
			}
		};
		panel.setPreferredSize(new Dimension(SIZE, SIZE));
		add(panel);
		pack();
	}
	
	private void initGameData() {
		for (int i = 0; i < 20; i++) {
			IUnit u;
			if ((int) Math.ceil(Math.random() * 100) == 35) {
				u = new UnitAdapter(new LegacyUnit(), 50);
			} else {
				u = new Unit("U" + i);
			}
			units.add(u);
		}
	}
	
	private void start() {
		running = true;
		thread = new Thread() {
			@Override
			public void run() {
				super.run();
				while (running) {
					for (int i = 0; i < units.size(); i++) {
						for (int j = i + 1; j < units.size(); j++) {
							if (units.get(i).isHit(units.get(j))) {
								units.get(i).redirection();
								units.get(j).redirection();
							}
						}
					}
					for (IUnit unit : units) {
						unit.move();
					}
					units.removeIf(IUnit::dead);
					if (units.isEmpty()) System.exit(0);
					panel.repaint();
					try {
						Thread.sleep(1000 / 60);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		thread.start();
	}
	
	private void drawBackground(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, SIZE, SIZE);
	}
	
	private void drawUnits(Graphics g) {
		for (IUnit unit : units) {
			if (unit.dead()) continue;
			
			// Circle
			g.setColor(unit.getClass().equals(UnitAdapter.class) ? Color.BLUE: Color.BLACK);
			g.fillOval(unit.getX(), unit.getY(), Unit.SIZE, Unit.SIZE);
			// Name
			g.setColor(Color.gray);
			g.drawString(unit.getName() + "", unit.getX() - 2, unit.getY() - 5);
			// Hp
			g.setColor(Color.red);
			g.drawString("HP:" + unit.getHealth(), unit.getX() - 8, unit.getY() + 25);
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Game game = new Game();
			game.setVisible(true);
			game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			game.start();
		});
	}
	
}
