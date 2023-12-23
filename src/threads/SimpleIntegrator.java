package threads;

import functions.Function;
import functions.Functions;

public class SimpleIntegrator implements Runnable
{
	private Task task;

	public SimpleIntegrator(Task task)
	{
		this.task = task;
	}

	@Override
	public void run()
	{
		try
		{
			Thread.currentThread().sleep(2);
			for (int i = 0; i < task.getCount(); i++)
			{
				Function f;
				double left, right, step;
				synchronized (task)
				{
					f = task.getF();
					left = task.getLeft();
					right = task.getRight();
					step = task.getStep();
				}
				double integral = Functions.integral(f, left, right, step);
				System.out.println("Result " + left + " " + right + " " + step + " " + integral);
			}
		}
		catch (InterruptedException e)
		{
			throw new RuntimeException(e);
		}

	}
}
