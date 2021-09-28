import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;

public class Yahtzee_GUI extends JFrame{
	
	JPanel scorebox, upper_scorebox, lower_scorebox, score_type, score_points, main_panel, roll_area, score_area, utilities, side_panel;
	JButton roll_button, score, blank;
	JButton [] dice = new JButton[5];
	String [] score_upper = {"Aces", "Twos", "Threes", "Fours", "Fives", "Sixes"};
	String [] score_lower = {"3 of a Kind", "4 of a Kind", "Full House", "Small Straight", "Large Straight", "Yahtzee", "Chance", };
	String [] score_total = {"Lower Total", "Upper Total", "Grand Total"};
	
	JTextField[] upper_score_fields = new JTextField[score_upper.length];
	//JLabel [] upper_score_labels = new JLabel[score_upper.length];
	JTextField[] lower_score_fields = new JTextField[score_lower.length];
	//JLabel [] lower_score_labels = new JLabel[score_lower.length];
	JTextField[] total_score_fields = new JTextField[score_total.length];
	JLabel [] total_score_labels = new JLabel[score_total.length];
	
	JRadioButton[] upper_scoring_options = new JRadioButton[score_upper.length];
	JRadioButton[] lower_scoring_options = new JRadioButton[score_lower.length];
	
	ButtonGroup scoring_group = new ButtonGroup();
	
	Font number = new Font("Verdana", Font.BOLD, 16);
	Font text = new Font("Verdana", Font.PLAIN, 10);
	
	int roll_number;
	int[] rolls = new int[5];
	int[] die_count = new int[6];
	
	//constructor
	public Yahtzee_GUI(){
		this.setTitle("Yahtzee");
		this.setSize(400, 600);
		this.setLayout(new BorderLayout(1,2));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		
		main_panel = new JPanel(new BorderLayout());
		side_panel = new JPanel(new BorderLayout());
		utilities = new JPanel(new GridLayout(2, 1));
		roll_area = new JPanel(new GridLayout(1, 5));
		scorebox = new JPanel(new GridLayout(2, 1));
		upper_scorebox = new JPanel(new GridLayout(6, 3));
		lower_scorebox = new JPanel(new GridLayout(7, 3));
		score_area = new JPanel(new GridLayout(1, 3));
		
		//add 5 dice
		for(int i = 0; i < 5; i++) {
			dice[i] = new JButton("0");
			dice[i].setEnabled(false);
			dice[i].setFont(number);
			roll_area.add(dice[i]);
			
		}
		roll_area.setBorder(BorderFactory.createTitledBorder("Rolls"));

		roll_button = new JButton ("ROLL DICE");
		roll_button.setFont(text);
		roll_button.addActionListener(new RollListener());
		blank = new JButton ();
		blank.setEnabled(false);

		score = new JButton ("SCORE POINTS");
		score.setFont(text);
		
		utilities.add(roll_button, BorderLayout.NORTH);
	
		utilities.add(score, BorderLayout.SOUTH);
		
		//upper section
		build_upper_scorebox();
		upper_scorebox.setBorder(BorderFactory.createTitledBorder("Upper Section"));

		//lower section
		build_lower_scorebox();
		lower_scorebox.setBorder(BorderFactory.createTitledBorder("Lower Section"));
		
		scorebox.add(upper_scorebox);
		scorebox.add(lower_scorebox);
		
		build_total_scorebox();
		score_area.setBorder(BorderFactory.createTitledBorder("Scoring"));
		
		main_panel.add(roll_area, BorderLayout.NORTH);
		main_panel.add(scorebox, BorderLayout.CENTER);
		main_panel.add(score_area, BorderLayout.SOUTH);
		side_panel.add(blank, BorderLayout.CENTER);
		side_panel.add(utilities, BorderLayout.SOUTH);
		
		this.add(main_panel, BorderLayout.CENTER);
		this.add(side_panel, BorderLayout.EAST);
		this.setVisible(true);
	}
	
	private void build_upper_scorebox() {
		
		for (int i = 0; i < score_upper.length; i++) {
			
			upper_scoring_options[i] = new JRadioButton(score_upper[i]);
			scoring_group.add(upper_scoring_options[i]);
			
			//upper_score_labels[i] = new JLabel(score_upper[i]);
			upper_score_fields[i] = new JTextField("0");
			upper_score_fields[i].setColumns(3);
			upper_score_fields[i].setFont(number);
			upper_score_fields[i].setEditable(false);
			upper_score_fields[i].setBackground(Color.white);
			
			//upper_scorebox.add(upper_score_labels[i]);
			upper_scorebox.add(upper_score_fields[i]);
			upper_scorebox.add(upper_scoring_options[i]);
		}
		
	}
	
	private void build_lower_scorebox() {

		for (int i = 0; i < score_lower.length; i++) {
			lower_scoring_options[i] = new JRadioButton(score_lower[i]);
			scoring_group.add(lower_scoring_options[i]);
			
			//lower_score_labels[i] = new JLabel(score_lower[i]);
			lower_score_fields[i] = new JTextField();
			lower_score_fields[i].setColumns(3);
			lower_score_fields[i].setFont(number);
			lower_score_fields[i].setEditable(false);
			lower_score_fields[i].setBackground(Color.white);
			
			//lower_scorebox.add(lower_score_labels[i]);
			lower_scorebox.add(lower_score_fields[i]);
			lower_scorebox.add(lower_scoring_options[i]);
		}
		
	}
	
	private void build_total_scorebox() {
		
		for (int i = 0; i < score_total.length; i++) {
			JPanel score_base = new JPanel(new BorderLayout());
			total_score_labels[i] = new JLabel(score_total[i]);
			total_score_fields[i] = new JTextField();
			total_score_fields[i].setColumns(3);
			total_score_fields[i].setFont(number);
			total_score_fields[i].setEditable(false);
			total_score_fields[i].setBackground(Color.white);
			
			score_base.add(total_score_labels[i], BorderLayout.NORTH);
			score_base.add(total_score_fields[i], BorderLayout.SOUTH);
			score_area.add(score_base);
		}
	}
	
	private class RollListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
			// TODO Auto-generated method stub
			for(int i = 0; i < 5; i++) {
				Random rnd = new Random();
				roll_number = rnd.nextInt(6) + 1;
				rolls[i] = roll_number;
				die_count[roll_number - 1]++;		//account for zero index
				
			}
			Arrays.sort(rolls);
			
			for(int i = 0; i < 5; i++) {
				dice[i].setText(Integer.toString(rolls[i]));
			}
			
			upper_score_fields[0].setText(Integer.toString(die_count[0]));
			upper_score_fields[1].setText(Integer.toString(die_count[1] * 2));
			upper_score_fields[2].setText(Integer.toString(die_count[2] * 3));
			upper_score_fields[3].setText(Integer.toString(die_count[3] * 4));
			upper_score_fields[4].setText(Integer.toString(die_count[4] * 5));
			upper_score_fields[5].setText(Integer.toString(die_count[5] * 6));
			
			for(int i = 0; i < 6; i++) {
				die_count[i] = 0;		//cleanse
				
			}
		}

	}
	
	public static void main(String[] args) {
		new Yahtzee_GUI();
	}
}
