package spl;
import matrix.Matrix;

public class Spl
{

	public static void main(String[] args)
	{
		Matrix data = new Matrix(3,3);
		// TODO Auto-generated method stub
		System.out.println("Hello World"); //prints hello world
		data.BacaMatrix(3,3);
		//data.TulisMATRIKS();
		System.out.println("Determinan= " + data.Determinan());
		data.TulisMATRIKS();
	}

}
