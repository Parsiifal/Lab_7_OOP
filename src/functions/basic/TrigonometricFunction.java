package functions.basic;

import functions.Function;

public class TrigonometricFunction implements Function
{
	@Override
	public double getLeftDomainBorder()
	{
		return Double.NEGATIVE_INFINITY;
	}

	@Override
	public double getRightDomainBorder()
	{
		return Double.POSITIVE_INFINITY;
	}

	@Override
	public double getFunctionValue(double x)
	{
		throw new UnsupportedOperationException("Класс тригонометрических функций не считает значения функций");
	}
}