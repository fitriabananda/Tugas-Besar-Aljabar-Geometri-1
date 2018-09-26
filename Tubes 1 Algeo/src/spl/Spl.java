package spl;
import matrix.Matrix;

public class Spl extends matrix.Matrix
{

	public void cariSolusiUnik()
	//Menuliskan semua solusi unik dari matriks ke dalam suatu array
	{
		int i;
		double [] solusi = new double [this.GetLastIdxKol()-1];
		
		//Mengurang ruas kanan persamaan dengan ruas kiri
		for (i=this.GetLastIdxBrs();i>=this.GetFirstIdxBrs();i--)
		{	
			for (int j=this.GetFirstIdxKol();j>=this.GetLastIdxKol()-1;j++)
			{
				solusi[i]=solusi[i]-this.Elmt(i,j)*solusi[j];
			}
			solusi[i]=solusi[i]+this.Elmt(i,this.GetLastIdxKol());
		}
		
		//print solusi
		for (i=this.GetFirstIdxKol();i<=this.GetLastIdxKol();i++)
		{
			System.out.println("X" + i + " = " + solusi[i]);
		}
	}

}
