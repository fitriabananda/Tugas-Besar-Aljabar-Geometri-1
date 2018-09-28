package interpolasi;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Interpolasi extends spl.Spl
{
	public int n;
	public double target;
	public double [] pointx;
	public double [] pointy;

	public Interpolasi()
	{
		
	}
	
	public double solvex(double x)
	{
		double solusi = 0;
		for (int i=1;i<=n;i++)
		{
			solusi = solusi + this.solusiSpl[i]*Math.pow(x,i-1);
		}
		return solusi;
	}

	public void outputLayar(int counter) throws IOException
	//menyimpan persamaan dalam array of string
	{
		FileWriter fw = new FileWriter("output.txt", counter!=0);
	    BufferedWriter bw = new BufferedWriter(fw);
	    PrintWriter out = new PrintWriter(bw);
	    
		System.out.println(counter+1+":");
		out.println(counter+1+":");

		System.out.println("Persamaan interpolasi polinom adalah : ");
		out.println("Persamaan interpolasi polinom adalah : ");
		
		persamaan = new String [1];
		persamaan[0] = "F(x)= ";
//		for (int i=1; i<=n; i++)
//		{
//			
//		}
		if (this.solusiSpl[n]!=0)
		{
			//angka = Math.round(this.solusiSpl[n]*1000.0)/1000.0;
			persamaan[0]+= solusiSpl[n]+"X^"+(n-1); //variabel pertama 
		}
		for(int j=n-1;j>1;j--)
		{
			if (this.solusiSpl[j]!=0)
			{
				if (this.solusiSpl[j]>0)
				{	
					persamaan[0]+=" + ";
				}
				else if (this.solusiSpl[j]<0)
				{	
					persamaan[0]+=" - ";
				}
				//angka = Math.round(this.solusiSpl[j]*1000.0)/1000.0;
				persamaan[0]+=Math.abs(solusiSpl[j])+"X^"+(j-1);
			}
		}
		if (this.solusiSpl[1]!=0)
		{
			if (this.solusiSpl[1]>0)
			{	
				persamaan[0]+=" + ";
			}
			else if (this.solusiSpl[1]<0)
			{	
				persamaan[0]+=" - ";
			}
			//angka = Math.round(this.solusiSpl[1]*1000.0)/1000.0;
			persamaan[0]+=Math.abs(this.solusiSpl[1]);
		}
		System.out.println(persamaan[0]);
		out.println(persamaan[0]);
		System.out.println("Nilai dari fungsi di titik target adalah : " + solvex(target));
		out.println("Nilai dari fungsi di titik target adalah : " + solvex(target));
		System.out.println();
		out.println();
		System.out.println("-------------------------------------------------------------------------");
		out.println("-------------------------------------------------------------------------");
		out.close();
	}
	
	public void olahTitik()
	/* Prosedur untuk mengolah titik menjadi matrix spl untuk interpolasi */
	{
		solusiSpl = new double [n+1];
		/*membuat matriks dan mengisi elemen-elemen matriks sesuai dengan titik yang ada*/
		TD = new double [n+1][n+2];
		NBrsEff = n;
		NKolEff = n+1;
		//mengisi hingga 1 kolom sebelum kolom terakhir
		for (int i=1;i<=n;i++)
		{
			for(int j=1; j<=n; j++)
			{
				TD[i][j]= Math.pow(pointx[i], j-1);
			}
		}
		
		//mengisi kolom paling terakhir
		int j=n+1;
		for (int i=1; i<=n; i++)
		{
			TD [i][j] = pointy[i];
		}
		this.EliminasiGauss();
		this.cariSolusiUnik();
	}
	
	
	/* ----------------------Baca Interpolasi----------------------- */
	public void bacaInterpolasiKeyboard()
	/*membuat matriks dari titik yang diberikan*/
	{
		System.out.print("Masukkan jumlah titik:");
		n = in.nextInt();	//n adalah jumlah titik
		System.out.print("Masukkan nilai x yang akan dikira-kira:");
		target = in.nextDouble(); //target adalah nilai x dimana akan dicari nilai interpolasinya
		
		pointx = new double [n+1];
		pointy = new double [n+1];
		
		System.out.println("Masukkan nilai tuple titik:");
		int i = 1;
		while (i<=n)	//menerima inputan titik
		{
			pointx[i] = in.nextDouble();
			pointy[i] = in.nextDouble();
			i++;
		}
		this.olahTitik();
	}
	
	public void bacaInterpolasiFile(String namaFile) throws FileNotFoundException
	/*membuat matriks dari titik yang diberikan melalui file namaFile*/
	{
		Scanner masukan = new Scanner (new File(namaFile));
		/* Baca nilai x yang akan dicari nilainya melalui interpolasi */
		Scanner firstLine = new Scanner (masukan.nextLine());
		target = firstLine.nextDouble();
		firstLine.close();
		System.out.println("Nilai X yang akan dicari nilai interpolasinya:" + target);
		
		/* Read jumlah baris point yang diberi */
		n = 0;
		while (masukan.hasNextLine()) //tidak usah reset karena memang titik ditulis dari baris ke 2
		{
			n++;
			masukan.nextLine();
		}
		
		pointx = new double [n+1];
		pointy = new double [n+1];
		
		masukan = new Scanner (new File(namaFile));
		masukan.nextLine();
		int i = 1;
		while (i<=n)	//menerima inputan titik
		{
			pointx[i] = masukan.nextDouble();
			pointy[i] = masukan.nextDouble();
			i++;
		}
		masukan.close();
		this.olahTitik();
	}
	
	public void outputInterpolasi()
	{
		persamaan = new String [1];
		persamaan[0] = "f(x)=";
	}
}




