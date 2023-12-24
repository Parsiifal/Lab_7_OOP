import functions.*;
import functions.basic.*;
import java.io.*;
import functions.TabulatedFunction;
import functions.TabulatedFunctions;

public class Main
{
	public static void main(String[] args) throws InappropriateFunctionPointException
	{
		TabulatedFunction f;

		f = TabulatedFunctions.createTabulatedFunction(
				ArrayTabulatedFunction.class, 0, 10, 3);
		System.out.println(f.getClass());
		System.out.println(f);

		f = TabulatedFunctions.createTabulatedFunction(
				ArrayTabulatedFunction.class, 0, 10, new double[] {0, 10});
		System.out.println(f.getClass());
		System.out.println(f);

		f = TabulatedFunctions.createTabulatedFunction(
				LinkedListTabulatedFunction.class,
				new FunctionPoint[] {
						new FunctionPoint(0, 0),
						new FunctionPoint(10, 10)
				}
		);
		System.out.println(f.getClass());
		System.out.println(f);

		f = TabulatedFunctions.tabulate(
				LinkedListTabulatedFunction.class, new Sin(), 0, Math.PI, 11);
		System.out.println(f.getClass());
		System.out.println(f);

	}

	public static void printFunctionValues(TabulatedFunction function) // функция для вывода значений функции
	{
		System.out.println("Значения функции:");

		for (int i = 0; i < function.getPointsCount(); i++)
		{
			double x = function.getPointX(i);
			double y = function.getPointY(i);
			System.out.println("f(" + x + ") = " + y);
		}
		System.out.println();
	}
}