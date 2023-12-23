package functions;

import functions.meta.*;

public class Functions
{
	private Functions() {} // Предотвращаем создание объектов этого класса извне

	public static Function shift(Function f, double shiftX, double shiftY)
	{
		return new Shift(f, shiftX, shiftY);
	}

	public static Function scale(Function f, double scaleX, double scaleY)
	{
		return new Scale(f, scaleX, scaleY);
	}

	public static Function power(Function f, double power)
	{
		return new Power(f, (int) power);
	}

	public static Function sum(Function f1, Function f2)
	{
		return new Sum(f1, f2);
	}

	public static Function mult(Function f1, Function f2)
	{
		return new Mult(f1, f2);
	}

	public static Function composition(Function f1, Function f2)
	{
		return new Composition(f1, f2);
	}

	public static double integral(Function f, double left, double right, double step)
	{
		if (left < f.getLeftDomainBorder() || right > f.getRightDomainBorder())
		{
			throw new IllegalArgumentException("Интервал интегрирования выходит за границы области определения функции!");
		}

		double result = 0;
		double currentX = left;

		while (currentX < right)
		{
			// умножаем высоту на сумму значений функций в текущей и в следующей точке
			double area = (step / 2) * (f.getFunctionValue(currentX) + f.getFunctionValue(currentX + step));
			result += area;
			currentX += step;
		}

		return result;
	}

}
