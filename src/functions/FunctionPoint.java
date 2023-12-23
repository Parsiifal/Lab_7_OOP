package functions;
import java.io.Serializable;

public class FunctionPoint implements Serializable
{
    private double x;
    private double y;

    public FunctionPoint()
    {
        x = 0;
        y = 0;
    }

    public FunctionPoint(double X, double Y)
    {
        x = X;
        y = Y;
    }

    public FunctionPoint(FunctionPoint point)
    {
        x = point.x;
        y = point.y;
    }

    public double GetX()
    {
        return x;
    }

    public double GetY()
    {
        return y;
    }

    public void SetX(double X)
    {
        x = X;
    }

    public void SetY(double Y)
    {
        y = Y;
    }

    @Override
    public String toString()
    {
		return "(" + x + "; " + y + ")";
    }

    @Override
    public boolean equals(Object o)
    {
        if(o == null || !(o instanceof FunctionPoint)) return false;
        return Double.compare(this.x, x) == 0 && Double.compare(this.y, y) == 0;
    }

    @Override
    public int hashCode()
    {
        int code = (int)(Double.doubleToLongBits(x) ^ (Double.doubleToLongBits(x) >>> 32));
        code = 31 * code + (int)(Double.doubleToLongBits(y) ^ (Double.doubleToLongBits(y) >>> 32));
        return code;
    }

    @Override
    public Object clone()
    {
        return new FunctionPoint(this);
    }
}