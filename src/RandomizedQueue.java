import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item>
{
	private Item[] buf;
	private int size;

	@SuppressWarnings("unchecked")
	public RandomizedQueue() // construct an empty randomized queue
	{
		buf = (Item[]) new Object[8];
	}

	public boolean isEmpty() // is the queue empty?
	{
		return size == 0;
	}

	public int size() // return the number of items on the queue
	{
		return size;
	}

	private void checkCapacity()
	{
		if (size >= buf.length)
		{
			@SuppressWarnings("unchecked")
			Item[] newBuf = (Item[]) new Object[buf.length << 1];
			System.arraycopy(buf, 0, newBuf, 0, buf.length);
			buf = newBuf;
		}
	}

	@SuppressWarnings("unchecked")
	private void checkRemove()
	{
		if (isEmpty())
			throw new NoSuchElementException();

		if (size < buf.length >> 2)
		{
			Item[] newBuf = (Item[]) new Object[buf.length >> 1];
			System.arraycopy(buf, 0, newBuf, 0, size);
			buf = newBuf;
		}
	}

	public void enqueue(Item item) // add the item
	{
		if (item == null)
			throw new NullPointerException();

		checkCapacity();

		buf[size++] = item;
	}

	public Item dequeue() // remove and return a random item
	{
		checkRemove();

		int i = StdRandom.uniform(size--);
		Item t = buf[i];
		buf[i] = buf[size];
		buf[size] = null;
		return t;
	}

	public Item sample() // return (but do not remove) a random item
	{
		if (isEmpty())
		{
			throw new NoSuchElementException();
		}
		return buf[StdRandom.uniform(size)];
	}

	public Iterator<Item> iterator() // return an independent iterator over
										// items in random order
	{
		return new RandomQueueIterator<Item>(size, buf);
	}

	public static void main(String[] args) // unit testing
	{
	}

	private class RandomQueueIterator<T> implements Iterator<T>
	{
		private int[] order;
		private T[] buf;
		private int size;
		private int i;

		public RandomQueueIterator(int size, T[] buf)
		{
			this.buf = buf;
			this.size = size;
			order = new int[size];
			for (int i = 0; i < order.length; i++)
			{
				order[i] = i;
			}
			StdRandom.shuffle(order);
		}

		@Override
		public boolean hasNext()
		{
			return i < size;
		}

		@Override
		public T next()
		{
			if (!hasNext())
			{
				throw new NoSuchElementException();
			}
			return buf[order[i++]];
		}
	}
}
