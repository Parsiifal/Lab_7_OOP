import functions.*;
import functions.basic.*;
import java.io.*;
import functions.TabulatedFunction;
import functions.TabulatedFunctions;

public class Main
{
	public static void main(String[] args) throws InappropriateFunctionPointException
	{
		Function f = new Cos();
		TabulatedFunction tf;
		tf = TabulatedFunctions.tabulate(f, 0, Math.PI, 11);
		System.out.println(tf.getClass());
		System.out.println(tf);
		TabulatedFunctions.setTabulatedFunctionFactory(new
				LinkedListTabulatedFunction.LinkedListTabulatedFunctionFactory());
		tf = TabulatedFunctions.tabulate(f, 0, Math.PI, 11);
		System.out.println(tf.getClass());
		System.out.println(tf);
		TabulatedFunctions.setTabulatedFunctionFactory(new
				ArrayTabulatedFunction.ArrayTabulatedFunctionFactory());
		tf = TabulatedFunctions.tabulate(f, 0, Math.PI, 11);
		System.out.println(tf.getClass());
		System.out.println(tf);

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