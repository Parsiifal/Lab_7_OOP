package threads;
import functions.Functions;

public class Integrator extends Thread
{
	private Task task;
	private Semaphore semaphore;

	public Integrator(Task task, Semaphore semaphore)
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
				semaphore.startIntegrator();
				double integral = Functions.integral(task.getF(), task.getLeft(), task.getRight(), task.getStep());
				System.out.println("Result " + task.getLeft() + " " + task.getRight() + " " + task.getStep() + " " + integral);
				semaphore.finishIntegrator();
			}
		}
		catch (InterruptedException e)
		{
			throw new RuntimeException(e);
		}

	}

}