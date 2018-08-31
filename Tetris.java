import java.awt.event. * ;
import java.util. * ;
import javax.swing. * ;
import java.awt. * ;

//Coded by Vasuman Albert Sarvesh
//Standard Tetris game
//Normal Tetris controls, shift to save a piece and enter to drop 

public class Tetris extends JPanel implements ActionListener {
	public boolean awokened;
	public Tetris() {
		awokened = true;
	}

	// Runs the game
	public static void main(String[] args) {
		JFrame f = new JFrame("Tetris");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(320, 800);
		f.setVisible(true);

		game.init();
		f.add(game);

		// Makes the Keyboard work
		f.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}

			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					game.rotate( - 1);
					break;
				case KeyEvent.VK_DOWN:
					game.rotate( + 1);
					break;
				case KeyEvent.VK_LEFT:
					game.move( - 1);
					break;
				case KeyEvent.VK_RIGHT:
					game.move( + 1);
					break;
				case KeyEvent.VK_SPACE:
					game.dropDown();
					game.score += 1;
					break;
				case KeyEvent.VK_SHIFT:
					game.isSaved = true;
					game.temp = game.saved;
					game.saved = game.currentPc;
					game.currentPc = game.temp;
				case KeyEvent.VK_ENTER:
					enterPressed = true;

				}
			}

			public void keyReleased(KeyEvent e) { }
		});

		// Drop Pc one unit per second
		new Thread() {@Override public void run() {
				while (true) {
					try {
						if (enterPressed) {
							time = 1;
						}
						Thread.sleep(time);
						if (game.awokened == true) {
							game.dropDown();
						}
					} catch(InterruptedException e) {}
				}
			}
		}.start();
	}

	// If you go overboard, you get a GameOver sign at the bottom of the screen
	private Label gameOver = new Label("Game Over");

	private final Point[][][] tets = {
		// The woke Pc
		{
			{
				new Point(0, 1),
				new Point(1, 1),
				new Point(2, 1),
				new Point(3, 1)
			},
			{
				new Point(1, 0),
				new Point(1, 1),
				new Point(1, 2),
				new Point(1, 3)
			},
			{
				new Point(0, 1),
				new Point(1, 1),
				new Point(2, 1),
				new Point(3, 1)
			},
			{
				new Point(1, 0),
				new Point(1, 1),
				new Point(1, 2),
				new Point(1, 3)
			}
		},

		// L Flip 
		{
			{
				new Point(0, 1),
				new Point(1, 1),
				new Point(2, 1),
				new Point(2, 0)
			},
			{
				new Point(1, 0),
				new Point(1, 1),
				new Point(1, 2),
				new Point(2, 2)
			},
			{
				new Point(0, 1),
				new Point(1, 1),
				new Point(2, 1),
				new Point(0, 2)
			},
			{
				new Point(1, 0),
				new Point(1, 1),
				new Point(1, 2),
				new Point(0, 0)
			}
		},

		// L Reg 
		{
			{
				new Point(0, 1),
				new Point(1, 1),
				new Point(2, 1),
				new Point(2, 2)
			},
			{
				new Point(1, 0),
				new Point(1, 1),
				new Point(1, 2),
				new Point(0, 2)
			},
			{
				new Point(0, 1),
				new Point(1, 1),
				new Point(2, 1),
				new Point(0, 0)
			},
			{
				new Point(1, 0),
				new Point(1, 1),
				new Point(1, 2),
				new Point(2, 0)
			}
		},

		// Habdaeiei Pc
		{
			{
				new Point(0, 0),
				new Point(0, 1),
				new Point(1, 0),
				new Point(1, 1)
			},
			{
				new Point(0, 0),
				new Point(0, 1),
				new Point(1, 0),
				new Point(1, 1)
			},
			{
				new Point(0, 0),
				new Point(0, 1),
				new Point(1, 0),
				new Point(1, 1)
			},
			{
				new Point(0, 0),
				new Point(0, 1),
				new Point(1, 0),
				new Point(1, 1)
			}
		},

		// Stupid
		{
			{
				new Point(1, 0),
				new Point(2, 0),
				new Point(0, 1),
				new Point(1, 1)
			},
			{
				new Point(0, 0),
				new Point(0, 1),
				new Point(1, 1),
				new Point(1, 2)
			},
			{
				new Point(1, 0),
				new Point(2, 0),
				new Point(0, 1),
				new Point(1, 1)
			},
			{
				new Point(0, 0),
				new Point(0, 1),
				new Point(1, 1),
				new Point(1, 2)
			}
		},

		// Stupid Flip
		{
			{
				new Point(0, 0),
				new Point(1, 0),
				new Point(1, 1),
				new Point(2, 1)
			},
			{
				new Point(1, 0),
				new Point(0, 1),
				new Point(1, 1),
				new Point(0, 2)
			},
			{
				new Point(0, 0),
				new Point(1, 0),
				new Point(1, 1),
				new Point(2, 1)
			},
			{
				new Point(1, 0),
				new Point(0, 1),
				new Point(1, 1),
				new Point(0, 2)
			}
		},

		// Pyramid Thing
		{
			{
				new Point(1, 0),
				new Point(0, 1),
				new Point(1, 1),
				new Point(2, 1)
			},
			{
				new Point(1, 0),
				new Point(0, 1),
				new Point(1, 1),
				new Point(1, 2)
			},
			{
				new Point(0, 1),
				new Point(1, 1),
				new Point(2, 1),
				new Point(1, 2)
			},
			{
				new Point(1, 0),
				new Point(1, 1),
				new Point(2, 1),
				new Point(1, 2)
			}
		}
	};

	// Standard Tetris colors (researched)
	private final Color[] tetColors = {
		Color.cyan,
		Color.blue,
		Color.orange,
		Color.yellow,
		Color.green,
		Color.pink,
		Color.red
	};

	private Point PcOrigin;
	private int currentPc;
	private int rotation;
	private ArrayList < Integer > nextPcs = new ArrayList < Integer > ();

	private long score;
	private Color[][] bottom;
	private Color purple = new Color(255, 0, 255);

	Button play = new Button("PLAY");

	// Creates a border around the bottom and initializes the first dropping Pc
	private void init() {

		bottom = new Color[12][24];
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 23; j++) {
				if (i == 0 || i == 11 || j == 22) {
					bottom[i][j] = purple;
				} else {
					bottom[i][j] = Color.BLACK;
				}
			}
		}
		newPc();
	}

	// Creates new Tetris Pc
	public void newPc() {
		PcOrigin = new Point(5, 0);
		rotation = 0;
		if (nextPcs.isEmpty()) {
			Collections.addAll(nextPcs, 0, 1, 2, 3, 4, 5, 6);
			Collections.shuffle(nextPcs);
		}
		currentPc = nextPcs.get(0);
		nextPcs.remove(0);
	}

	boolean notDone = true;

	// Rotate the Pc clockwise if up key is pressed, counterclockwise if down key is pressed
	public void rotate(int i) {
		int newRotation = (rotation + i) % 4;
		if (newRotation < 0) {
			newRotation = 3;
		}
		if (!collidesAt(PcOrigin.x, PcOrigin.y, newRotation)) {
			rotation = newRotation;
		}
		repaint();
	}

	// Move the Pc left or right
	public void move(int i) {
		if (!collidesAt(PcOrigin.x + i, PcOrigin.y, rotation)) {
			PcOrigin.x += i;
		}
		repaint();
	}

	// Drops the Pc one line, or just sets it down if it can't go any further
	public void dropDown() {
		if (notDone) {
			if (!collidesAt(PcOrigin.x, PcOrigin.y + 1, rotation)) {
				PcOrigin.y += 1;
			} else {
				fixTobottom();
			}
			repaint();
		}
	}

	// Makes the piece hit the bottom 
	public void fixTobottom() {
		for (Point p: tets[currentPc][rotation]) {
			bottom[PcOrigin.x + p.x][PcOrigin.y + p.y] = tetColors[currentPc];
		}
		clearRows();
		newPc();
	}

	// Clear the rows that are full, and give points accordingly
	public void clearRows() {
		boolean gap;
		int numClears = 0;

		for (int j = 21; j > 0; j--) {
			gap = false;
			for (int i = 1; i < 11; i++) {
				if (bottom[i][j] == Color.BLACK) {
					gap = true;
					break;
				}
			}
			if (!gap) {
				deleteRow(j);
				j += 1;
				numClears += 1; 
			}
		}

		switch (numClears) {
		case 1:
			score += 100;
			break;
		case 2:
			score += 300;
			break;
		case 3:
			score += 500;
			break;
		case 4:
			score += 800;
			break;
		}
	}

	// Collision test for the dropping piece, tests to see if the piece that is dropping hits or collides with the bottom
	private boolean collidesAt(int x, int y, int rotation) {
		for (Point p: tets[currentPc][rotation]) {
			if (bottom[p.x + x][p.y + y] != Color.BLACK) {
				time = 1000;
				enterPressed = false;
				//System.out.println(y);
				if (y <= 1) {
					notDone = false;
					gameOver.setVisible(true);
					gameOver.setSize(300, 50);
					gameOver.setLocation(180, 600); // We like this line number
					gameOver.setForeground(Color.BLACK);
					gameOver.setFont(new Font("Arial", Font.PLAIN, 18));
					add(gameOver);
					//System.out.println("hi");
				}
				return true;
			}
		}
		return false;
	}

	// Called when row is full (automatically deletes)
	public void deleteRow(int row) {
		for (int j = row - 1; j > 0; j--) {
			for (int i = 1; i < 11; i++) {
				bottom[i][j + 1] = bottom[i][j];
			}
		}
	}

	int move = 0;
	int saved = ((int) Math.random() * 7);
	boolean isSaved = true;
	static int time = 1000;

	// Draw the falling Pc
	private void drawPc(Graphics g) {
		g.setColor(tetColors[currentPc]);
		for (Point p: tets[currentPc][rotation]) {
			g.fillRect((p.x + PcOrigin.x) * 26 + move, (p.y + PcOrigin.y) * 26, 25, 25);
		}
	}

	// Brings game to life
	@Override
	public void paintComponent(Graphics g) {
		// Paint the bottom
		if (game.awokened == true) {
			g.fillRect(0, move, 26 * 12, 26 * 23);
			for (int i = 0; i < 12; i++) {
				for (int j = 0; j < 23; j++) {
					g.setColor(bottom[i][j]);
					g.fillRect(26 * i + move, 26 * j, 25, 25);
				}
			}

			// Display the score
			g.setColor(Color.WHITE);
			g.drawString("" + score, 19 * 12 + move, 25);

			// Draw the currently falling Pc
			drawPc(g);

			if (isSaved) {
				drawSaved(g);
			}
		}
	}

	// Brings back the saved one
	private void drawSaved(Graphics g) {
		g.setColor(tetColors[saved]);
		for (Point p: tets[saved][0]) {
			g.fillRect((p.x + 1) * 26 + move, (p.y + 23) * 26, 25, 25);
		}
	}

	// Awokens the game
	public void actionPerformed(ActionEvent action) {
		//System.out.println("hit");
		if (action.getSource() == play) {
			play.setVisible(false);
			game.awokened = true;

		}
	}

	static final Tetris game = new Tetris();
	private int temp;
	static boolean enterPressed = false;
} // 500 lines of beauty