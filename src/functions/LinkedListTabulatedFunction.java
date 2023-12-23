package functions;
import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListTabulatedFunction implements TabulatedFunction, Serializable
{
	private static class FunctionNode
	{
		private FunctionPoint data;
		private FunctionNode prev, next;

		public FunctionNode()
		{
			data = new FunctionPoint();
			prev = null;
			next = null;
		}
	}

	private int lengthList;
	private FunctionNode first, current, tail, head;
	{
		head = new FunctionNode();
		head.next = head;
		head.prev = head;
		current = head;
		lengthList = 0;
	}

	public static class LinkedListTabulatedFunctionFactory implements TabulatedFunctionFactory
	{
		@Override
		public TabulatedFunction createTabulatedFunction(FunctionPoint[] points)
		{
			return new LinkedListTabulatedFunction(points);
		}

		@Override
		public TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount)
		{
			return new LinkedListTabulatedFunction(leftX, rightX, pointsCount);
		}

		@Override
		public TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values)
		{
			return new LinkedListTabulatedFunction(leftX, rightX, values);
		}

	}

	public LinkedListTabulatedFunction(FunctionPoint[] points)
	{
		if (points == null || points.length < 2)
		{
			throw new IllegalArgumentException("Некорректные параметры конструктора!");
		}

		for (int i = 1; i < points.length; i++)
		{
			if (points[i].GetX() <= points[i - 1].GetX())
			{
				throw new IllegalArgumentException("Точки не упорядочены по значению абсциссы!");
			}
		}

		for (FunctionPoint point : points)
		{
			current = addNodeToTail();
			current.data = new FunctionPoint(point);
		}

		lengthList = points.length;
	}


	public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount)
	{
		if (leftX >= rightX || pointsCount < 2)
		{
			throw new IllegalArgumentException("Некорректные параметры конструктора!");
		}

		double interval = (rightX - leftX) / (pointsCount - 1);
		for (int i = 0; i < pointsCount; ++i)
		{
			current = addNodeToTail();
			current.data = new FunctionPoint(leftX + i * interval,0);
		}
		lengthList = pointsCount;
	}

	public LinkedListTabulatedFunction(double leftX, double rightX, double[] values)
	{
		if (leftX >= rightX || values.length < 2)
		{
			throw new IllegalArgumentException("Некорректные параметры конструктора!");
		}

		double interval = (rightX - leftX) / (values.length - 1);
		for (int i = 0; i < values.length; ++i)
		{
			current = addNodeToTail();
			current.data = new FunctionPoint(leftX + i * interval,values[i]);
		}
		lengthList = values.length;

	}

	@Override
	public String toString()
	{
		System.out.print("{ ");
		current = head.next;
		int index = 0;
		while(current != head)
		{
			System.out.print(current.data.toString());
			if(index < lengthList - 1)
			{
				System.out.print(", ");
			}
			index++;
			current = current.next;
		}
		System.out.print(" }");
		return "";
	}

	@Override
	public boolean equals(Object o)
	{
		if(o == null || !(o instanceof TabulatedFunction)) return false;

		if (o instanceof LinkedListTabulatedFunction)
		{
			LinkedListTabulatedFunction obj = (LinkedListTabulatedFunction) o;
			if(obj.lengthList != this.lengthList) return false;

			this.current = this.head.next;
			obj.current = obj.head.next;

			while (this.current != this.head)
			{
				if(this.current.data.GetX() != obj.current.data.GetX() || this.current.data.GetY() != obj.current.data.GetY()) return false;
				this.current = this.current.next;
				obj.current = obj.current.next;
			}
		}
		else
		{
			TabulatedFunction obj = (TabulatedFunction) o;
			if(this.lengthList != obj.getPointsCount()) return false;

			for(int i = 0; i < this.lengthList; i++)
			{
				if(this.getPointX(i) != obj.getPointX(i) || this.getPointY(i) != obj.getPointY(i)) return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode()
	{
		current = head.next;
		int code = 0;
		int index = 0;
		while (current != head)
		{
			code += index + current.data.hashCode();
			index++;
			current = current.next;
		}
		return code + lengthList;
	}

	@Override
	public Object clone()
	{
		FunctionPoint[] points2 = new FunctionPoint[lengthList];
		current = head.next;

		for(int i = 0; i < lengthList; i++)
		{
			points2[i] = (FunctionPoint) current.data.clone();
			current = current.next;
		}

		return new LinkedListTabulatedFunction(points2);
	}

	@Override
	public Iterator<FunctionPoint> iterator()
	{
		return new Iterator<>()
		{
			private FunctionNode node = head;

			public boolean hasNext()
			{
				return node.next != head;
			}

			public void remove()
			{
				throw new UnsupportedOperationException();
			}
			public FunctionPoint next()
			{
				if(!hasNext()) throw new NoSuchElementException();
				FunctionNode nextNode = node;
				node = node.next;
				return (FunctionPoint) nextNode.data.clone();
			}
		};
	}

	private void checkIndex(int index)
	{
		if (index < 0 || index >= lengthList)
		{
			throw new FunctionPointIndexOutOfBoundsException();
		}
	}
	@Override
	public double getLeftDomainBorder()
	{
		return first.data.GetX();
	}

	@Override
	public double getRightDomainBorder()
	{
		return tail.data.GetX();
	}

	@Override
	public double getFunctionValue(double x)
	{
		if (x >= getLeftDomainBorder() && x <= getRightDomainBorder())
		{
			int prevX = 0, nextX = 0;
			for (int i = 0; i < lengthList; ++i)
			{
				current = getNodeByIndex(i);
				if (x == current.data.GetX())
				{
					return current.data.GetY();
				}
				if (current.data.GetX() < x)
				{
					prevX = i;
					nextX = i + 1;
				}
			}
			return (x - getNodeByIndex(prevX).data.GetX()) * (getNodeByIndex(nextX).data.GetY() - getNodeByIndex(prevX).data.GetY())/
					(getNodeByIndex(nextX).data.GetX() - getNodeByIndex(prevX).data.GetX()) + getNodeByIndex(prevX).data.GetY();
		}
		else
		{
			return Double.NaN;
		}
	}

	@Override
	public int getPointsCount()
	{
		return lengthList;
	}

	@Override
	public FunctionPoint getPoint(int index)
	{
		checkIndex(index);
		return new FunctionPoint(getNodeByIndex(index).data);
	}

	@Override
	public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException
	{
		checkIndex(index);

		if (index < 0 || index > getPointsCount() - 1)
			throw new FunctionPointIndexOutOfBoundsException();

		else if (index > 0 && index < getPointsCount() - 1)
		{
			if (point.GetX() >= getNodeByIndex(index - 1).data.GetX() && point.GetX() <= getNodeByIndex(index + 1).data.GetX())
			{
				getNodeByIndex(index).data.SetX(point.GetX());
				getNodeByIndex(index).data.SetY(point.GetY());
			}
		}
		else if (index == 0)
		{
			if (getPointsCount() <= 1 || point.GetX() <= getNodeByIndex(index + 1).data.GetX())
			{
				getNodeByIndex(index).data.SetX(point.GetX());
				getNodeByIndex(index).data.SetY(point.GetY());
			}
		}
		else if (index == getPointsCount() - 1)
		{
			if (getPointsCount() <= 1 || point.GetX() >= getNodeByIndex(index - 1).data.GetX())
			{
				getNodeByIndex(index).data.SetX(point.GetX());
				getNodeByIndex(index).data.SetY(point.GetY());
			}
		}
		else
		{
			throw new InappropriateFunctionPointException();
		}

	}

	@Override
	public double getPointX(int index)
	{
		checkIndex(index);
		return getNodeByIndex(index).data.GetX();
	}

	@Override
	public void setPointX(int index, double x) throws InappropriateFunctionPointException
	{
		if (index < 0 || index > getPointsCount() - 1)
			throw new FunctionPointIndexOutOfBoundsException();

		else if (index > 0 && index < getPointsCount() - 1
				&& (x >= getNodeByIndex(index - 1).data.GetX() && x <= getNodeByIndex(index + 1).data.GetX()))
		{
			getNodeByIndex(index).data.SetX(x);
		}
		else if (index == 0 && (x <= getNodeByIndex(index+1).data.GetX()))
		{
			getNodeByIndex(index).data.SetX(x);
		}
		else if (index == getPointsCount() - 1 && (x >= getNodeByIndex(index - 1).data.GetX()))
		{
			getNodeByIndex(index).data.SetX(x);
		}
		else
		{
			throw new InappropriateFunctionPointException();
		}

	}

	@Override
	public double getPointY(int index)
	{
		checkIndex(index);
		return getNodeByIndex(index).data.GetY();
	}

	@Override
	public void setPointY(int index, double y)
	{
		checkIndex(index);
		getNodeByIndex(index).data.SetY(y);
	}

	@Override
	public void deletePoint(int index)
	{
		checkIndex(index);

		if (lengthList < 3)
		{
			throw new IllegalStateException("Невозможно удалить точку, так как количество точек менее трех!");
		}

		deleteNodeByIndex(index);

	}

	@Override
	public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException
	{
		int index = 0;
		current = first;

		if(tail.data.GetX() <= point.GetX())
		{
			index = lengthList;
		}
		else
		{
			for(; current.data.GetX() <= point.GetX(); ++index)
				current = current.next;
		}

		if (point.GetX() == current.prev.data.GetX())
			throw new InappropriateFunctionPointException();

		FunctionNode temp;
		temp = addNodeByIndex(index);
		temp.data = point;
	}

	private FunctionNode getNodeByIndex(int index) // возвращает ссылку на объект элемента списка по его номеру
	{
		checkIndex(index);
		current = first;
		if (index >= 0 && index < lengthList)
			while (index-- != 0) current = current.next;

		return current;
	}

	private FunctionNode addNodeToTail() // добавляет элемент в конец списка и возвращает ссылку на объект
	{
		if (lengthList == 0)
		{
			first = new FunctionNode();
			first.next = head;
			first.prev = head;

			head.next = first;
			head.prev = first;

			++lengthList;
			tail = first;

			return first;
		}
		else
		{
			current = new FunctionNode();

			tail.next = current;
			current.prev = tail;
			current.next = head;

			tail = current;
			++lengthList;

			return current;
		}
	}

	private FunctionNode addNodeByIndex(int index) // добавляет новый элемент в указанную позицию списка
	{
		checkIndex(index);
		FunctionNode temp;

		if(index == 0)
		{
			temp = new FunctionNode();
			current = first;
			head.next = temp;
			head.prev = temp;

			temp.prev = current.prev;
			temp.next = current;

			current.prev.next = temp;
			current.prev = temp;

			first = temp;

			++lengthList;
		}
		else if(index == lengthList)
			temp = addNodeToTail();
		else
		{
			temp = new FunctionNode();
			current = getNodeByIndex(index);
			temp.prev = current.prev;
			temp.next = current;

			current.prev.next = temp;
			current.prev = temp;

			++lengthList;
		}

		return temp;
	}

	private FunctionNode deleteNodeByIndex(int index) // удаляет элемент по индексу и возвращает ссылку на объект
	{
		checkIndex(index);
		current = getNodeByIndex(index);
		if (index==0)
		{
			head.next = current.next;
			current.next.prev=head;
			first = current.next;
		}
		else
		{
			current.prev.next = current.next;
			current.next.prev = current.prev;
		}
		--lengthList;
		return current;
	}

}