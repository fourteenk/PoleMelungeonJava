/* Jesse PL Pavlak (imageLinear.java)
	hw01 - 1/14/13
	Usage: imageLinear infile [filename] outile [filename] outefilePgm [filename] xResolution [int] yResolution[int]
	infile is a ".lin" file which consists of 3 decimal (float) numbers.
	assigns each coordinate as the dot product of the coordinate (in homogeneous form) and the coefficients from *.lin file
*/
import java.io.*;
import java.util.Scanner;

public class imageLinear{
	public static float dProd(float[] A, float[] X){
		float d = 0;
		for(int i = 0; i < 3; i++) d = d + (A[i] * X[i]);

		return d;
	}

	public static float[] getA(String line){

		Scanner scan = new Scanner(line);
		float[] A = new float[3];

		int i = 0;
		while(scan.hasNext()){
			A[i] = Float.parseFloat(scan.next());
			i++;
		}

		return A;
	}
	public static int[] getDims(String[] args){

		int i;
		if(args.length != 10) usage(0);

		for(i = 0; i < args[7].length(); i++) if(!Character.isDigit(args[7].charAt(i))) usage(2);
		int xres = Integer.parseInt(args[7]);
		if(xres < 1) usage(2);

		for(i = 0; i < args[9].length(); i++) if(!Character.isDigit(args[9].charAt(i))) usage(2);
		int yres = Integer.parseInt(args[9]);
		if(yres < 1) usage(2);

		int[] rv = {xres, yres};
		return rv;

	}

	public static void usage(int eCode){
		switch(eCode){
			case 0:		System.out.printf("Usage:\n imageLinear infile [filename] outile [filename] outefilePgm [filename] xResolution [int] yResolution[int]\n");
			case 1:		System.out.printf("File error: unable to open one or more files\n");
			case 2:		System.out.printf("xResolution/yResolution must be positive real valued integers\n");
		}
		System.exit(0);
	}

	public static void main(String[] args)
	{
		int i,j;
		int[] dims = new int[2];
		String line;
		float d;
		dims = getDims(args);
		try{
			FileInputStream infile = new FileInputStream(args[1]);
			BufferedReader fin = new BufferedReader(new InputStreamReader(infile));

			line = fin.readLine();
			float[] A = new float[3];
			while(line != null){
				if(line.charAt(0) != '#'){
					A = getA(line);
				}
				line = fin.readLine();
			}

		fin.close();

		FileWriter out_dat = new FileWriter(args[3]);
		BufferedWriter out = new BufferedWriter(out_dat);

		FileWriter out_pgm = new FileWriter(args[5]);
		BufferedWriter pgm = new BufferedWriter(out_pgm);

		pgm.write("P2\n" + dims[0] + " " + dims[1] + "\n255\n");
		float[] X = new float[3];
		for(j = (dims[1] - 1); j >= 0; j = j - 1){
			for(i = 0; i < dims[0]; i++){
				X[0] = (float)i;	X[1] = (float)j;	X[2] = 1;
				d = dProd(A, X);
				out.write(d + " ");
				if(d < 0) {d = 0;}
				else if(d > 255) {d = 255;}

				pgm.write((int)d + " ");
			}
			out.write("\n");
			pgm.write("\n");
		}
		out.close();
		pgm.close();

		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
