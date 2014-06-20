import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
 * This is the class that is used to read the files provided as the arguments
 */
public class ReadFile 
{
	String s1[][]=new String[500][500];
	int number_states;
	ArrayList<Double>initprob = new ArrayList<Double>();
	double a[][];
	int number_output;
	String output_alpha[]; 
	double output_dist[][];
	int length[] = new int[5000];

	//This is the method used to read the model file.
	public int readModelFile(String args) throws NumberFormatException, IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(args));

		//Get the number of states from the file
		number_states = Integer.parseInt(br.readLine());
		//System.out.println(number_states);

		//Get the initial probability of each state
		String s=br.readLine();
		String s2[]=s.split("\\s");

		for(int i=0;i<number_states;i++)
		{
			initprob.add(Double.parseDouble(s2[i]));
		}
		//System.out.println(initprob);

		s=br.readLine();
		s2=s.split("\\s");
		a= new double[number_states][number_states]; 

		int p=0;

		//Get the probability transition matrix
		for(int i=0;i<number_states;i++)
		{
			for(int j=0;j<number_states;j++)
			{
				a[i][j] = Double.parseDouble(s2[p++]);
		//		System.out.println(a[i][j]);

			}
		}

		//Get the number of outputs

		number_output=Integer.parseInt(br.readLine());
		//System.out.println(number_output);

		//Get the output alphabets
		output_alpha= new String[number_output];
		s=br.readLine();
		s2=s.split("\\s");

		for(int i=0;i<number_output;i++)
		{
			output_alpha[i]=s2[i];
			//System.out.println(output_alpha[i]);
		}

		//Get the output distribution probability from the specification file
		output_dist=new double[number_states][number_output];
		s=br.readLine();
		s2=s.split("\\s");
		p=0;
		for(int i=0;i<number_states;i++)
		{
			for(int j=0;j<number_output;j++)
			{
				output_dist[i][j]=Double.parseDouble(s2[p++]);
				//System.out.print(output_dist[i][j] + "\t");
			}
			//System.out.println("");
		}

		br.close();


		return number_states;
	}

	//This is the method to read the file that contains the sequence of observations.
	public int[] readSequenceFile(String args) throws IOException
	{
		BufferedReader br1 = new BufferedReader(new FileReader(args));

		String seq;
		int i=0;


		while((seq = br1.readLine())!=null)
		{
			//System.out.println(seq);


			s1[i]=seq.split("\\s");
			//System.out.println(s1[i]);
			length[i]=s1[i].length;
			//System.out.println(length[i]);
			i++;
		}

		br1.close();
		return length;
	}

	//This method calculates the number of observations that are present in the sequence file.
	public int no_observations(String args) throws IOException
	{
		BufferedReader br1 = new BufferedReader(new FileReader(args));
		int line=0;

		while(br1.readLine()!=null)
		{
			//System.out.println(seq);
			line++;
		}
		return line;
	}

	//This method returns the output distribution function.
	public double[][] returnOutputDist()
	{
		return output_dist;
	}

	//This method returns the number of output symbols.
	public int returnNumberObs()
	{
		return number_output;
	}

	//This method returns the probability state transition matrix.
	public double[][] returnA()
	{
		return a;
	}

	//This method returns the output alphabets.
	public String[] returnOutputAlpha()
	{
		return output_alpha;
	}

	//This method returns the initial probability matrix.
	public ArrayList<Double> returnInitProb()
	{
		return initprob;
	}

	//This method returns the sequence of the observations from the file.
	public String[][] returnSequence()
	{
		return s1;
	}

}