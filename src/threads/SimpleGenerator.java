package threads;

import functions.basic.Log;

public class SimpleGenerator implements Runnable
{
	private Task task;

	public SimpleGenerator(Task task)
	{
		this.task = task;
	}

	@Override
	public void run()
	{
		try
		{
			for (int i = 0; i < task.getCount(); i++)
			{
				synchronized (task)
				{
					task.setF(new Log(Math.random() * 10));
					task.setLeft(Math.random() * 100);
					task.setRight(Math.random() * 100 + 100);
					task.setStep(Math.random());
				}
				System.out.println("Source " + task.getLeft() + " " + task.getRight() + " " + task.getStep());
			}
			Thread.currentThread().sleep(2);
		}
		catch (InterruptedException e)
		{
			throw new RuntimeException(e);
		}

	}
}
