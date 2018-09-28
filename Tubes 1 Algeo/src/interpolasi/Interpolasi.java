package interpolasi;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Interpolasi extends spl.Spl
{
	public int n;
	public double target;
	public double [] pointx;
	public double [] pointy;
	public double [][] TInterpolasi;

	public Interpolasi(int nb, int nk)
	{
		super(nb, nk);
		// TODO Auto-generated constructor stub
	}
	
	public void olahTitik()
	/* Prosedur untuk mengolah titik menjadi matrix spl untuk interpolasi */
	{
		/*membuat matriks dan mengisi elemen-elemen matriks sesuai dengan titik yang ada*/
		TInterpolasi = new double [n+1][n+2];
		
		//mengisi hingga 1 kolom sebelum kolom terakhir
		for (int i=1;i<=n;i++)
		{
			for(int j=1; j<=n; j++)
			{
				TInterpolasi[i][j]= Math.pow(pointx[i], j-1);
			}
		}
		
		//mengisi kolom paling terakhir
		int j=n+1;
		for (int i=1; i<=n; i++)
		{
			TInterpolasi [i][j] = pointy[j];
		}
		this.cariSolusiUnik();
	}
	
	public void bacaInterpolasiKeyboard()
	/*membuat matriks dari titik yang diberikan*/
	{
		Scanner in = new Scanner (System.in);
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
		in.close();
		this.olahTitik();
	}
	
	public void bacaInterpolasiFile(String namaFile) throws FileNotFoundException
	/*membuat matriks dari titik yang diberikan melalui file namaFile*/
	{
		Scanner in = new Scanner (new File(namaFile));
		
		/* Baca nilai x yang akan dicari nilainya melalui interpolasi */
		Scanner firstLine = new Scanner (in.nextLine());
		target = firstLine.nextDouble();
		firstLine.close();
		System.out.println("Nilai X yang akan dicari nilai interpolasinya:" + target);
		
		/* Read jumlah baris point yang diberi */
		n = 0;
		while (in.hasNextLine()) //tidak usah reset karena memang titik ditulis dari baris ke 2
		{
			n++;
			in.nextLine();
		}
		
		pointx = new double [n+1];
		pointy = new double [n+1];
		
		System.out.println("Reading Titik melalui file");
		int i = 1;
		while (i<=n)	//menerima inputan titik
		{
			pointx[i] = in.nextDouble();
			pointy[i] = in.nextDouble();
			i++;
		}
		
		in.close();
		this.olahTitik();
	}
}




