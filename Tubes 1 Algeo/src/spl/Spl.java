package spl;

import matrix.Matrix;

public class Spl extends Matrix
{
	/* jumlah variabel spl yang harus dipecahkan */
	//int n = this.GetLastIdxKol()-1;
	
	/* Solusi untuk x1 - xn sesuai dengan index dari array */
	public double [] solusiSpl;
	
	/* Status solved untuk x1 - xn sesuai dengan index dari array */
	/* 1 untuk x yang diketahui nilai eksaknya, 2 untuk yang bisa disubstitusikan 
	 * Untuk yang masih 0 harusnya sudah parametric dasar */
	public int [] solved;
	
	public Spl(int nb, int nk)
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
		solved = new int [this.GetLastIdxKol()];
		solusiSpl = new double [this.GetLastIdxKol()];
	}
	
	public void solusiBanyak()
	{
		System.out.println("Solusi untuk SPL: ");
		for (int i=this.GetLastIdxBrs(); i>=this.GetFirstIdxBrs(); i--) //looping dri baris terbawah sampe atas
		{
			int j=GetFirstIdxKol();
			while (this.Elmt(i, j)==0 && j<this.GetLastIdxKol()-1) //mencari idx keberapa yang tidak nol
			{
				j++;
			}
			if (this.Elmt(i, j)!=0) // kalau nemu 1 baru di proses, kalau 0 pasti baris yang semuanya 0
			{
				System.out.print("X" + j +" = ");
				boolean foundParametric = false; //found variabel lain di baris itu, kalau engga, berarti lsg solved eksak
				for (int k = j+1; k < GetLastIdxKol(); k++) //looping cari bagian koef yang tidak 0
				{
					if (this.Elmt(i, k)!=0) // kalau bukan 0 kita proses, kalau 0 lewatin saja
					{
						if (solved[k]==0) //selidiki (harusnya itu fixed parametric) jadi langsung output saja
						{
							if (this.Elmt(i,k) < 0 && foundParametric)
							{
								System.out.print("+");
							}
							else if (this.Elmt(i, k) > 0)
							{
								System.out.print("-");
							}
							System.out.print(Math.abs(this.Elmt(i, k))+ "P"+ k + " ");
							foundParametric=true;
						}
						/* kalau dia udh solved untuk nilai eksak */
						else if (solved[k]==1) //kurangin hasil ujungnya, lalu dia sendiri di nol kan
						{
							double result = this.Elmt(i, this.GetLastIdxKol())-(this.Elmt(i,k) * solusiSpl[k]) ;
							this.SetElmt(i, this.GetLastIdxKol(), result);
							this.SetElmt(i, k, 0);
						}
						else if (solved[k]==2) //bisa disubstitusikan
						{
							//proses substitusi
							for (int l = k+1; l<=this.GetLastIdxKol(); l++)
							{
								double result = this.Elmt(i, l) - (this.Elmt(i, k) * this.Elmt((int)Math.round(solusiSpl[k]), l) );
								this.SetElmt(i, l, result);
							}
							//set dia sendiri jadi 0
							this.SetElmt(i, k, 0);
						}
						else
						{
							System.out.print("Exceptional case!\n");
						}
					}
				}
				if (!foundParametric)
				{
					//System.out.println("debug 1 "+j+" "+this.GetLastIdxKol()+solusiSpl[1]);
					
					solusiSpl[j] = this.Elmt(i, this.GetLastIdxKol()); //solusiSpl nya berupa nilai akhirnya
					solved[j] = 1; //assign status solved dia sebagai yang eksak
				}
				else //kalau ketemu yang parametric
				{
					solved[j] = 2;
					solusiSpl[j] = i;
				}
				
				this.SetElmt(i, this.GetLastIdxKol(), Math.round(this.Elmt(i, this.GetLastIdxKol())*1000.0)/1000.0 );
				if (this.Elmt(i, this.GetLastIdxKol()) > 0 && foundParametric)
				{
					System.out.print("+" + this.Elmt(i, this.GetLastIdxKol()) );
				}
				else if (this.Elmt(i, this.GetLastIdxKol()) > 0 && !foundParametric)
				{
					System.out.print(this.Elmt(i, this.GetLastIdxKol()) );
				}
				else if (this.Elmt(i, this.GetLastIdxKol()) < 0)
				{
					System.out.print(this.Elmt(i, this.GetLastIdxKol()));
				}
				System.out.println();
			}
		}
		for (int j=this.GetFirstIdxKol(); j<=GetLastIdxKol()-1; j++)
		{
			if (solved[j]==0)
			{
				System.out.println("X"+j+"= P"+j);
			}
		}
		System.out.print("Dengan ");
		for (int j=this.GetFirstIdxKol(); j<=GetLastIdxKol()-1; j++)
		{
			if (solved[j]==0)
			{
				System.out.print("P"+j+" ");
			}
		}
		System.out.println("adalah element dari bilangan rill!");
	}
	
	/* Menyimpan nilai solusi dari x1- xn dari suatu spl */
	public static void main(String[] args)
	{
		Spl data = new Spl(4,7);
		// TODO Auto-generated method stub
		//System.out.println("Hello World"); //prints hello world
		data.BacaMatrix(4,7);
		data.solusiBanyak();
		//data.TulisMATRIKS();
		//System.out.println("Determinan= " + data.Determinan());
		System.out.println();
		//data.TulisMATRIKS();
	}

}
