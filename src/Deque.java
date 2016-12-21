import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item>
{
	private Item[] buf;
	private int size;// number of elements

	private int head;// index of head element
	private int tail;// index of tail element

	@SuppressWarnings("unchecked")
	public Deque() // construct an empty deque
	{
		buf = (Item[]) new Object[4];
	}

	public boolean isEmpty() // is the deque empty?
	{
		return size == 0;
	}

	public int size() // return the number of items on the deque
	{
		return size;
	}

	private void copyTo(Item[] newBuf)
	{
		int j = 0;
		for (int i = head; i < buf.length; i++, j++)
		{
			newBuf[j] = buf[i];
		}
		for (int i = 0; j < buf.length; i++, j++)
		{
			newBuf[j] = buf[i];
		}
	}

	@SuppressWarnings("unchecked")
	private void checkCapacity()
	{
		if (size >= buf.length)
		{
			// Array full....
			Item[] newBuf = (Item[]) new Object[buf.length << 1];
			copyTo(newBuf);
			head = 0;
			tail = buf.length - 1;
			buf = newBuf;
		}
	}

	private int dec(int i)
	{
		i--;
		if (i < 0)
			i += buf.length;
		return i;
	}

	private int inc(int i)
	{
		i++;
		if (i >= buf.length)
			i -= buf.length;
		return i;
	}

	public void addFirst(Item item) // add the item to the front
	{
		if (item == null)
			throw new NullPointerException();

		checkCapacity();
		if (size == 0)
			head = tail = 0;
		else
			head = dec(head);

		buf[head] = item;
		size++;
	}

	public void addLast(Item item) // add the item to the end
	{
		if (item == null)
			throw new NullPointerException();

		checkCapacity();
		if (size == 0)
			head = tail = 0;
		else
			tail = inc(tail);

		buf[tail] = item;
		size++;
	}

	@SuppressWarnings("unchecked")
	private void checkRemove()
	{
		if (isEmpty())
			throw new NoSuchElementException();

		if (size < buf.length >> 2)
		{
			Item[] newBuf = (Item[]) new Object[buf.length >> 1];
			int j = 0;
			for (Item a : this)
			{
				newBuf[j++] = a;
			}

			head = 0;
			tail = size - 1;
			buf = newBuf;
		}
	}

	public Item removeFirst() // remove and return the item from the front
	{
		checkRemove();

		size--;
		Item t = buf[head];
		buf[head] = null;
		head = inc(head);
		return t;
	}

	public Item removeLast() // remove and return the item from the end
	{
		checkRemove();

		size--;
		Item t = buf[tail];
		buf[tail] = null;
		tail = dec(tail);
		return t;
	}

	public Iterator<Item> iterator() // return an iterator over items in order
										// from front to end
	{
		return new Iterator<Item>()
		{
			private int n = size;
			private int i = head;

			@Override
			public boolean hasNext()
			{
				return n > 0;
			}

			@Override
			public Item next()
			{
				if (!hasNext())
				{
					throw new NoSuchElementException();
				}
				Item t = buf[i];
				i = inc(i);
				n--;
				return t;
			}

		};
	}
}