package aug5;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;

public class TypingTutor extends JFrame implements ActionListener, KeyListener {

	private JLabel lblTimer;
	private JLabel lblScore;
	private JLabel lblWord;
	private JTextField txtWord;
	private JButton btnStart;
	private JButton btnStop;

	private Timer wordtimer = null;
	private Timer clocktimer = null;
	private boolean running = false;
	private int timeRemaining = 0;
	private int score = 0;
	private String[] words = null;

	public TypingTutor(String[] args) {

		words = args;
		GridLayout layout = new GridLayout(3, 2);
		super.setLayout(layout);

		Font font = new Font("Comic Sans MS", 1, 100);

		lblTimer = new JLabel("Time");
		lblTimer.setFont(font);
		super.add(lblTimer);

		lblScore = new JLabel("Score");
		lblScore.setFont(font);
		super.add(lblScore);

		lblWord = new JLabel("");
		lblWord.setFont(font);
		super.add(lblWord);

		txtWord = new JTextField("");
		txtWord.setFont(font);
		txtWord.addKeyListener(this);
		super.add(txtWord);

		btnStart = new JButton("Start");
		btnStart.setFont(font);
		btnStart.addActionListener(this);
		super.add(btnStart);

		btnStop = new JButton("Stop");
		btnStop.setFont(font);
		btnStop.addActionListener(this);
		super.add(btnStop);

		super.setTitle("Typing Tutor");
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		super.setVisible(true);
		super.setExtendedState(MAXIMIZED_BOTH);

		setgame();
	}

	private void setgame() {

		clocktimer = new Timer(1000, this);
		clocktimer.setInitialDelay(0); // to remove initial delay upon game
										// start

		wordtimer = new Timer(3000, this);
		wordtimer.setInitialDelay(0);

		running = false;
		timeRemaining = 50;
		score = 0;

		lblTimer.setText("Time :" + timeRemaining);
		lblScore.setText("Score :" + score);
		lblWord.setText("");
		txtWord.setText("");
		btnStart.setText("Start");
		btnStop.setText("Stop");

		txtWord.setEnabled(false);
		btnStop.setEnabled(false);
	}

	@Override
	public synchronized void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnStart) {
			Handlestart();
		} else if (e.getSource() == btnStop) {
			Handlestop();
		} else if (e.getSource() == clocktimer) {
			HandleClockTimer();
		} else if (e.getSource() == wordtimer) {
			HandleWordTimer();
		}
	}

	private void HandleWordTimer() {

		String actual = txtWord.getText();
		String expected = lblWord.getText();

		if (actual.equals(expected) && expected.length() > 0) {
			score++;
		}

		lblScore.setText("Score :" + score);

		int ridx = (int) (Math.random() * words.length);
		lblWord.setText(words[ridx]);
		txtWord.setText("");
	}

	private void HandleClockTimer() {

		timeRemaining--;
		lblTimer.setText("Time :" + timeRemaining);

		if (timeRemaining == -1) {
			Handlestop();
			return;
		}

	}

	private void Handlestart() {

		if (running == false) {
			clocktimer.start();
			wordtimer.start();
			running = true;
			btnStart.setText("Pause");
			btnStop.setEnabled(true);
			txtWord.setEnabled(true);

			txtWord.setFocusCycleRoot(true); // to set the cursor to the text
												// box without clicking
			super.nextFocus();

		} else {
			clocktimer.stop();
			wordtimer.stop();
			running = false;
			btnStart.setText("Start");
			// btnStop.setEnabled(false);
			txtWord.setEnabled(false);
		}
	}

	private void Handlestop() {

		clocktimer.stop();
		wordtimer.stop();
		int choice = JOptionPane.showConfirmDialog(this, "Replay?");

		if (choice == JOptionPane.YES_OPTION) {
			setgame();
		} else if (choice == JOptionPane.NO_OPTION) {
			JOptionPane.showMessageDialog(this, "Final Score :" + score);
			super.dispose();
		} else if (choice == JOptionPane.CANCEL_OPTION) {
			if (timeRemaining == 0) {
				setgame();
			} else {
				wordtimer.start();
				clocktimer.start();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {

		String actual = txtWord.getText();
		String expected = lblWord.getText();

		if (actual.equals(expected) && expected.length() > 0) {
			score++;
			lblTimer.setText("Time :" + timeRemaining);
			lblScore.setText("Score :" + score);

			int ridx = (int) (Math.random() * words.length);
			lblWord.setText(words[ridx]);
			txtWord.setText("");

			wordtimer.restart();
		}

	}
}
