import java.util.ArrayList;
import java.io.IOException;


/*
 * This class is the class that implements the main method. This will initialize reading the file and will call all the methods to calculate
 * the most likely sequence.
 */
public class Viterbi {
	
	public static void main(String args[]) throws NumberFormatException,
			IOException {
		int number_states;
		int number_sequence[];
		double output_dist[][];
		double a[][];
		String output_alpha[];
		int number_output;
		ArrayList<Double> initprob = new ArrayList<Double>();
		String s1[][];
		int obs;

		ReadFile rf = new ReadFile();
		Calculate c = new Calculate();

		number_states = rf.readModelFile(args[0]);
		obs = rf.no_observations(args[1]);
		number_sequence = rf.readSequenceFile(args[1]);
		number_output = rf.returnNumberObs();
		a = rf.returnA();
		output_alpha = rf.returnOutputAlpha();
		output_dist = rf.returnOutputDist();
		initprob = rf.returnInitProb();
		s1 = rf.returnSequence();

		for (int i = 0; i < obs; i++) {
			System.out.println("The most probable sequence for observation "
					+ (i + 1) + ":");
			c.initialize(number_sequence[i], number_states, output_dist, a,
					initprob, output_alpha, s1[i]);
			c.calculateRest(number_sequence[i], number_states, output_dist, a,
					initprob, output_alpha, s1[i]);
			System.out.println("\n");
		}
	}
}