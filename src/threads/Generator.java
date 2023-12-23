package threads;
import functions.basic.Log;

public class Generator  extends Thread
{
	private Task task;
	private Semaphore semaphore;

	public Generator(Task task, Semaphore semaphore)
	{
		this.task = task;
		this.semaphore = semaphore;
	}

	public void run()
	{
		try
		{
			for(int i = 0; i < task.getCount(); i++)
			{
				if(isInterrupted()) break;
				semaphore.startGenerator();
				task.setF(new Log(Math.random() * 10));
				task.setLeft(Math.random() * 100);
				task.setRight(Math.random() * 100 + 100);
				task.setStep(Math.random());
				System.out.println("Source " + task.getLeft() + " " + task.getRight() + " " + task.getStep());
				semaphore.finishGenerator();
			}
		}
		catch (InterruptedException e)
		{
			throw new RuntimeException(e);
		}

	}

}