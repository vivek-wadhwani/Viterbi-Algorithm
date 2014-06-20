import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

/*
 * This class is used to calculate the most probable sequence of states to get the observations.
 */
public class Calculate 
{
	double v[][];
	DecimalFormat df = new DecimalFormat("#.##");

	double max;
	double ar[];
	int state[][];

	ArrayList<Integer> Sequence = new ArrayList<Integer>();

	//This method is used to initialize the state-observation table for the first observation sequence.
	public void initialize(int number_sequence,int number_states,double[][] output_dist,double [][] a, ArrayList<Double> initprob, String[] output_alpha, String[] seq)
	{
		v = new double[number_states][number_sequence];
		state = new int[number_states][seq.length];
		String first = seq[0];
		int getindex=getindex(first,output_alpha);

		for(int i=0;i<number_states;i++)
		{
			v[i][0]=initprob.get(i)*output_dist[i][getindex];

			if(v[i][0]!=0)
			{
				state[i][0]=i+1;
			}

			else
			{
				state[i][0]=0;
			}
		}
	}

	//This method is used to calculate the remaining columns of the state-observation table.
	public void calculateRest(int number_sequence,int number_states,double[][] output_dist,double [][] a, ArrayList<Double> initprob, String[] output_alpha, String[] seq)
	{
		int getindex;
		ar = new double[number_states];
		for(int j=1;j<seq.length;j++)
		{

			getindex=getindex(seq[j],output_alpha);
			for(int i=0;i<number_states;i++)
			{
				for(int k=0;k<number_states;k++)
				{
					v[i][j]=v[k][j-1]*a[k][i];
					ar[k] = v[i][j];
				}
				v[i][j]=ar[max(ar)];
				state[i][j]=max(ar)+1;
				v[i][j]=v[i][j]*output_dist[i][getindex];
			}
		}
		findmax(v, state,number_states,seq);
	}

	//This method is used to find the maximum of all the values calculated in the state-sequence table.
	public int max(double arr[])
	{
		max = arr[0];
		int c=0;
		for(int i=0;i<arr.length;i++)
		{
			if(max<arr[i])
			{
				max = arr[i];
				c=i;
			}
		}
		return c;
	}

	//This method is used to get the index of the particular observation sequence. 
	public int getindex(String output_seq,String output[])
	{
		int a=0;
		for(int i=0;i<output.length;i++)
		{
			if(output[i].equals(output_seq))
			{
				a=i;
				break;
			}
		}
		return a;
	}

	//This method is used to find the maximum of the final sequence to decide which state the sequence ends. It is also used to print the most likely sequence of states.
	public void findmax(double[][] v, int[][] state,int number_states,String[] seq)
	{
		Sequence.clear();
		int maxelementindex;
		int ind;
		for(int i=0;i<number_states;i++)
		{
			ar[i]=v[i][seq.length-1];
		}
		maxelementindex = max(ar);

		for(int j=seq.length-1;j>=0;j--)
		{
			Sequence.add(maxelementindex+1);
			ind = state[maxelementindex][j]-1;
			maxelementindex=state[ind][j]-1;
		}

		Collections.reverse(Sequence);

		//This is the part where the most likely sequence is printed.
		System.out.println(Sequence);
	}
}