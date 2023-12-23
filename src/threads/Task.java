package threads;
import functions.Function;

public class Task
{
	private Function f;
	private double left, right, step;
	int count;

	public Function getF()
	{
		return f;
	}

	public void setF(Function f)
	{
		this.f = f;
	}

	public double getLeft()
	{
		return left;
	}

	public void setLeft(double left)
	{
		this.left = left;
	}

	public double getRight()
	{
		return right;
	}

	public void setRight(double right)
	{
		this.right = right;
	}

	public double getStep()
	{
		return step;
	}

	public void setStep(double step)
	{
		this.step = step;
	}

	public int getCount()
	{
		return count;
	}

	public Task (int count)
	{
		this.count = count;
	}
}