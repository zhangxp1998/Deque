import static org.junit.Assert.assertEquals;

import java.util.ArrayDeque;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class DequeueTest
{
	Deque<Integer> que;
	ArrayDeque<Integer> oracle;
	public static final int TEST_SIZE = 3000;
	private Random rand;
	private long seed;

	@Before
	public void setUp() throws Exception
	{
		que = new Deque<Integer>();
		oracle = new ArrayDeque<Integer>();
		seed = System.nanoTime();
		// seed = 16479807979427L;
		rand = new Random(seed);
	}

	@Test
	public void testAddLast()
	{
		for (int i = 0; i < TEST_SIZE; i++)
		{
			que.addLast(i);
		}

		for (int i = 0; i < TEST_SIZE; i++)
		{
			assertEquals(i, (int) que.removeFirst());
		}
	}

	@Test
	public void testAddFirst()
	{
		for (int i = TEST_SIZE; --i >= 0;)
		{
			que.addFirst(i);
		}

		for (int i = 0; i < TEST_SIZE; i++)
		{
			assertEquals(i, (int) que.removeFirst());
		}
	}

	@Test
	public void testIterator()
	{
		for (int i = TEST_SIZE; --i >= 0;)
		{
			que.addFirst(i);
		}
		int i = 0;
		for (int a : que)
		{
			assertEquals(i++, a);
		}
	}

	@Test
	public void randomTest()
	{
		System.out.println("DequeueTest.randomTest() " + seed);
		for (int i = 0; i < TEST_SIZE; i++)
		{
			int op = rand.nextInt(5);
			int operand = rand.nextInt(TEST_SIZE);
			switch (op)
			{
			case 0:
				assertEquals(oracle.size(), que.size());
				break;
			case 1:
				if (!oracle.isEmpty())
				{
					assertEquals(oracle.removeFirst(), que.removeFirst());
				}
				break;
			case 2:
				if (!oracle.isEmpty())
				{
					assertEquals(oracle.removeLast(), que.removeLast());
				}
				break;
			case 3:
				oracle.addFirst(operand);
				que.addFirst(operand);
				break;
			case 4:
				oracle.addLast(operand);
				que.addLast(operand);
				break;
			}
		}
	}

}
