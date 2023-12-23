package threads;

public class Semaphore
{
	private boolean canIntegr = true;

	public synchronized void startGenerator() throws InterruptedException
	{
		while(!canIntegr)
		{
			wait();
		}
	}

	public synchronized void finishGenerator()
	{
		canIntegr = false;
		notifyAll();
	}

	public synchronized void startIntegrator() throws InterruptedException
	{
		while(canIntegr)
		{
			wait();
		}
	}

	public synchronized void finishIntegrator()
	{
		canIntegr = true;
		notifyAll();
	}
}