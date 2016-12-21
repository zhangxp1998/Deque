import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Subset
{
	// Reservoir sampling

	public static void main(String[] args)
	{
		final int k = Integer.parseInt(args[0]);
		if (k == 0)
		{
			return;
		}
		RandomizedQueue<String> que = new RandomizedQueue<String>();

		int i = 0;
		for (i = 0; i < k; i++)
		{
			que.enqueue(StdIn.readString());
		}

		while (!StdIn.isEmpty())
		{
			int op = StdRandom.uniform(i + 1);
			if (op < k)
			{
				que.dequeue();
				que.enqueue(StdIn.readString());
			} else
			{
				StdIn.readString();
			}
			i++;
		}

		for (i = 0; i < k; i++)
		{
			System.out.println(que.dequeue());
		}
	}
}