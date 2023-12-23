package threads;

import functions.Functions;
import functions.basic.Log;

public class GeneralMain
{

	public static void nonThread()
	{
		Task task = new Task(100);

		for(int i = 0; i < task.getCount(); i++)
		{
			task.setF(new Log(Math.random() * 10));
			task.setLeft(Math.random() * 100);
			task.setRight(Math.random() * 100 + 100);
			task.setStep(Math.random());

			System.out.println("Source " + task.getLeft() + " " + task.getRight() + " " + task.getStep());
			double integral = Functions.integral(task.getF(), task.getLeft(), task.getRight(), task.getStep());
			System.out.println("Result " + task.getLeft() + " " + task.getRight() + " " + task.getStep() + " " + integral);
		}
	}

	public static void simpleThreads()
	{
		Task task = new Task(100);
		SimpleGenerator generator = new SimpleGenerator(task);
		SimpleIntegrator integrator = new SimpleIntegrator(task);

		Thread generatorTh = new Thread(generator);
		Thread integratorTh = new Thread(integrator);

		generatorTh.start();
		integratorTh.start();
	}

	public static void complicatedThreads()
	{
		Task task = new Task(100);
		Semaphore semaphore = new Semaphore();
		Thread generatorTh = new Generator(task, semaphore);
		Thread integratorTh = new Integrator(task, semaphore);
		generatorTh.start();
		integratorTh.start();

		try
		{
			Thread.sleep(50);
		} catch (InterruptedException e)
		{
			throw new RuntimeException(e);
		}
		generatorTh.interrupt();
		integratorTh.interrupt();
	}

	public static void main (String[] args)
	{
		//nonThread();
		//simpleThreads();
		complicatedThreads();
	}
}
