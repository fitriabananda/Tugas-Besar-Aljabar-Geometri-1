package matrix;
import java.util.Scanner;

public class Matrix
{
	
	//Atribut object
	int NBrsEff;
	int NKolEff;
	double [][] TD = new double [BrsMax+1][KolMax+1];
	
	public static int BrsMin = 1;
	public static int BrsMax = 100;
	public static int KolMin = 1;
	public static int KolMax = 100;
	Scanner in = new Scanner(System.in);
	
	
	//Method
	//Konstruktor
	public Matrix()
	{
		NBrsEff=0;
		NKolEff=0;
	}
	
	public Matrix(int nb, int nk)
	/* Membuat matrix ukuran ixj */
	{
		NBrsEff=nb;
		NKolEff=nk;
		for (int i=1; i<=nb; i++)
		{
			for (int j=1; j<=nk; j++)
			{
				this.SetElmt(i,j,0);
			}
		}
	}
	
	//Getter
	public double Elmt(int i, int j)
	{
		return TD[i][j];
	}
	
	//Setter
	public void SetElmt(int i, int j, double val)
	{
		TD[i][j] = val;
	}
	
	//Selektor
	public int GetFirstIdxBrs()
	/* Mengirimkan index baris terkecil M */
	{
		return BrsMin;
	}
	
	public int GetFirstIdxKol()
	/* Mengirimkan index kolom terkecil M */
	{
		return KolMin;
	}
	
	public int GetLastIdxBrs ()
	/* Mengirimkan index baris terbesar M */
	{
		return this.GetFirstIdxBrs()+this.NBrsEff-1;
	}
	
	public int GetLastIdxKol ()
	/* Mengirimkan index kolom terbesar M */
	{
		return this.GetFirstIdxKol()+this.NKolEff-1;
	}
	
	public double GetDiagonal (int i)
	/* Mengirimkan elemen M(i,i) */
	{
		return this.Elmt(i,i);
	}
	
	/* ********** KELOMPOK OBE ***************** */
	public void MinusRow(int basis, int target, double koef)
	/* Mengurangi row ke target dengan koef * basis */
	{
		for (int j=this.GetFirstIdxKol(); j<= this.GetLastIdxKol(); j++)
		{
			this.SetElmt(target, j,this.Elmt(target,j)-(koef*this.TD[basis][j]));
		}
	}
	
	public void SwapBaris(int a, int b)
	/* Melakukan swap matrix m antara a dengan b */
	{
		for (int j=this.GetFirstIdxKol(); j<= this.GetLastIdxKol(); j++)
		{
			double temp = this.Elmt(a, j);
			this.SetElmt(a, j, this.Elmt(b, j));
			this.SetElmt(b, j, temp);
		}
	}
	
	public void MakeSatu(int i, double koef)
	/* Membagi baris ke i dengan konstanta koef dengan tujuan untuk membuat depannya menjadi 1 */
	{
		for (int j=this.GetFirstIdxKol(); j<= this.GetLastIdxKol(); j++)
		{
			this.SetElmt(i, j,this.Elmt(i,j)/koef);
		}
	}
	
	public void findSwap(int j)
	/* Mencari baris yang tidak 0, lalu di swap */
	{
		
	}
	
	/* ********** KELOMPOK BACA/TULIS ********** */ 
	public void BacaMatrix (int NB, int NK)
	/* I.S. IsIdxValid(NB,NK) */ 
	/* F.S. M terdefinisi nilai elemen efektifnya, berukuran NB x NK */
	/* Proses: Melakukan MakeMATRIKS(M,NB,NK) dan mengisi nilai efektifnya */
	/* Selanjutnya membaca nilai elemen per baris dan kolom */
	/* Contoh: Jika NB = 3 dan NK = 3, maka contoh cara membaca isi matriks :
	1 2 3
	4 5 6
	8 9 10 
	*/
	{
		int i,j;
		for (i=BrsMin; i<BrsMin+NB; i++)
		{
			for (j=KolMin; j<KolMin+NK; j++)
			{
				SetElmt(i,j,in.nextDouble());
				
			}
		}
	}
	
	public void BacaFileMatrix()
	{
		
	}
	public void TulisMATRIKS ()
	/* I.S. M terdefinisi */
	/* F.S. Nilai M(i,j) ditulis ke layar per baris per kolom, masing-masing elemen per baris 
	   dipisahkan sebuah spasi */
	/* Proses: Menulis nilai setiap elemen M ke layar dengan traversal per baris dan per kolom */
	/* Contoh: menulis matriks 3x3 (ingat di akhir tiap baris, tidak ada spasi)
	1 2 3
	4 5 6
	8 9 10
	*/
	{
		for (int i=this.GetFirstIdxBrs(); i<=this.GetLastIdxBrs(); i++)
		{
			System.out.print(this.Elmt(i,this.GetFirstIdxKol()));
			for (int j=this.GetFirstIdxKol()+1; j<=this.GetLastIdxKol(); j++)
			{
				System.out.print(" "+ this.Elmt(i,j));
			}
			System.out.print("\n");
		}
	}
	
	public double Determinan ()
	/* Prekondisi: IsBujurSangkar(M) */
	/* Menghitung nilai determinan M */
	{
		double det=0;
		double tampung;
		double kali=1;
		boolean lanjut = true;
		for (int j=1; j<(this.GetLastIdxKol()) && lanjut; j++)//pembuatan matrix segitiga
		{
			if (Math.floor(this.Elmt(j,j))==0)//jika element diagonalnya==0
			{
				int x=j+1;
				while (this.Elmt(x,j)==0 && x<this.GetLastIdxBrs())
				{
					x++;
				}
				if (Math.floor(this.Elmt(x,j))!=0)//swap
				{
					kali*=-1;
					for(int i=j; i<=this.GetLastIdxKol(); i++)
					{
						tampung = this.Elmt(x,i);
						this.SetElmt(x,i,this.Elmt(j,i));
						this.SetElmt(i, j, tampung);
					}
				}
				else
				{
					lanjut = false;
				}
			}
			if (lanjut) //pembuatan matrix segitiga
			{
				for (int i=j+1; i<=this.GetLastIdxBrs(); i++)
				{
					double koef = this.Elmt(i,j)/this.Elmt(j,j);
					this.MinusRow(j, i,koef);
				}
			}
		}
		if (lanjut)
		{
			det = this.Elmt(1,1);
			int i=2;
			int j=2;
			while (i<=this.GetLastIdxBrs() && j<=this.GetLastIdxKol())
			{
				det *= this.Elmt(i,j);
				i++;
				j++;
			}
		}
		else
		{
			det = 0;
		}
		return det*kali;
	}
}
