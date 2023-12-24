package functions;
import java.io.*;

public class TabulatedFunctions
{
	private static TabulatedFunctionFactory functionFactory = new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory();

	private TabulatedFunctions() {} // Предотвращаем создание объектов этого класса извне

	public static void setTabulatedFunctionFactory(TabulatedFunctionFactory factory)
	{
		functionFactory = factory;
	}

	public static TabulatedFunction createTabulatedFunction(FunctionPoint[] points)
	{
		return functionFactory.createTabulatedFunction(points);
	}
	public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount)
	{
		return functionFactory.createTabulatedFunction(leftX, rightX, pointsCount);
	}
	public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values)
	{
		return functionFactory.createTabulatedFunction(leftX, rightX, values);
	}

	public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> clazz, FunctionPoint[] points)
	{
		try
		{
			return clazz.getConstructor(FunctionPoint[].class).newInstance((Object) points);
		}
		catch (Exception e)
		{
			throw new IllegalArgumentException(e);
		}
	}

	public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> clazz, double leftX, double rightX, int pointsCount)
	{
		try
		{
			return clazz.getConstructor(double.class, double.class, int.class).newInstance(leftX, rightX, pointsCount);
		}
		catch (Exception e)
		{
			throw new IllegalArgumentException(e);
		}
	}

	public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> clazz, double leftX, double rightX, double[] values)
	{
		try
		{
			return clazz.getConstructor(double.class, double.class, double[].class).newInstance(leftX, rightX, values);
		}
		catch (Exception e)
		{
			throw new IllegalArgumentException(e);
		}
	}

	public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount)
	{
		if (leftX >= rightX || pointsCount < 2)
		{
			throw new IllegalArgumentException("Некорректные параметры");
		}
		if (leftX < function.getLeftDomainBorder() || rightX > function.getRightDomainBorder())
		{
			throw new IllegalArgumentException("Диапазон находится за пределами области");
		}

		double step = (rightX - leftX) / (pointsCount - 1);
		FunctionPoint[] values = new FunctionPoint[pointsCount];

		for (int i = 0; i < pointsCount; i++)
		{
			double x = leftX + i * step;
			values[i] = new FunctionPoint(x, function.getFunctionValue(x));
		}

		return createTabulatedFunction(values);
	}

	public static TabulatedFunction tabulate(Class<? extends TabulatedFunction> clazz, Function function, double leftX, double rightX, int pointsCount)
	{
		if (leftX >= rightX || pointsCount < 2)
		{
			throw new IllegalArgumentException("Некорректные параметры");
		}
		if (leftX < function.getLeftDomainBorder() || rightX > function.getRightDomainBorder())
		{
			throw new IllegalArgumentException("Диапазон находится за пределами области");
		}

		double step = (rightX - leftX) / (pointsCount - 1);
		FunctionPoint[] values = new FunctionPoint[pointsCount];

		for (int i = 0; i < pointsCount; i++)
		{
			double x = leftX + i * step;
			values[i] = new FunctionPoint(x, function.getFunctionValue(x));
		}

		return createTabulatedFunction(clazz, values);
	}

	public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) throws IOException
	{
		DataOutputStream datOutStr = new DataOutputStream(out);
		try
		{
			datOutStr.writeInt(function.getPointsCount());
			for (int i = 0; i < function.getPointsCount(); i++)
			{
				datOutStr.writeDouble(function.getPointX(i));
				datOutStr.writeDouble(function.getPointY(i));
			}
		}
		finally
		{
			datOutStr.close();
		}
	}

	public static TabulatedFunction inputTabulatedFunction(InputStream in) throws IOException
	{
		DataInputStream datInStr = new DataInputStream(in);
		try
		{
			int pointsCount = datInStr.readInt();
			FunctionPoint[] values = new FunctionPoint[pointsCount];

			for (int i = 0; i < pointsCount; i++)
			{
				values[i] = new FunctionPoint(datInStr.readDouble(), datInStr.readDouble());
			}
			return createTabulatedFunction(values);
		}
		finally
		{
			datInStr.close();
		}
	}

	public static TabulatedFunction inputTabulatedFunction(Class<? extends TabulatedFunction> clazz, InputStream in) throws IOException
	{
		DataInputStream datInStr = new DataInputStream(in);
		try
		{
			int pointsCount = datInStr.readInt();
			FunctionPoint[] values = new FunctionPoint[pointsCount];

			for (int i = 0; i < pointsCount; i++)
			{
				values[i] = new FunctionPoint(datInStr.readDouble(), datInStr.readDouble());
			}
			return createTabulatedFunction(clazz, values);
		}
		finally
		{
			datInStr.close();
		}
	}

	public static void writeTabulatedFunction(TabulatedFunction function, Writer out) throws IOException
	{
		BufferedWriter buffWriter = new BufferedWriter(out);
		try
		{
			buffWriter.write(Integer.toString(function.getPointsCount()));
			buffWriter.write("\n");
			for (int i = 0; i < function.getPointsCount(); i++)
			{
				buffWriter.write(Double.toString(function.getPointX(i)));
				buffWriter.write(" ");
				buffWriter.write(Double.toString(function.getPointY(i)));
				buffWriter.write("\n");
			}
		}
		finally
		{
			buffWriter.close();
		}
	}

	public static TabulatedFunction readTabulatedFunction(Reader in) throws IOException
	{
		StreamTokenizer token = new StreamTokenizer(in);
		token.parseNumbers();
		token.nextToken();
		int pointsCount = (int) token.nval;

		FunctionPoint[] values = new FunctionPoint[pointsCount];
		double x, y;

		for (int i = 0; i < pointsCount; i++)
		{
			token.nextToken();
			x = token.nval;
			token.nextToken();
			y = token.nval;
			values[i] = new FunctionPoint(x, y);
		}
		return createTabulatedFunction(values);
	}

	public static TabulatedFunction readTabulatedFunction(Class<? extends TabulatedFunction> clazz, Reader in) throws IOException
	{
		StreamTokenizer token = new StreamTokenizer(in);
		token.parseNumbers();
		token.nextToken();
		int pointsCount = (int) token.nval;

		FunctionPoint[] values = new FunctionPoint[pointsCount];
		double x, y;

		for (int i = 0; i < pointsCount; i++)
		{
			token.nextToken();
			x = token.nval;
			token.nextToken();
			y = token.nval;
			values[i] = new FunctionPoint(x, y);
		}
		return createTabulatedFunction(clazz, values);
	}

}