import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.awt.*;

public class BitPlane{

	public static void main(String[] v){

	new BitPlane();
	}

	public BitPlane(){

	BufferedImage img = null;

	try{

	img = ImageIO.read(new File("image.bmp"));

	}

	catch (IOException e){

	e.printStackTrace();

	}

	int n = img.getWidth();
	int m = img.getHeight();
	double[][] matrix = new double[n][m];
	int [][]pixels = new int[n*m][8];
	String s=null;
	int cnt=0;

	for (int row = 0; row < n; ++row){

		for (int col = 0; col < m; ++col){

			Raster rst = img.getRaster();
			int grayLevel = rst.getSample(row,col,0);
			matrix[row][col] = grayLevel;
			s=Integer.toBinaryString((int)matrix[row][col]);
			int len = s.length()-1;
			boolean b = false;
			int len2 = 7;
			if(len<7)
				b = true;
			
			for(int i=len;i>=0;i--){

				char c = s.charAt(i);

				int x = Character.getNumericValue(c);

				if(b==false)

				pixels[cnt][i] = x;

				else if(b==true){

				pixels[cnt][len2] = x;
				len2--;

				}

			}

			cnt++;

		}

	}


	int bit0[],bit1[],bit2[],bit3[],bit4[],bit5[],bit6[],bit7[];

	bit0 = new int[(n*m)];
	bit1 = new int[(n*m)];
	bit2 = new int[(n*m)];
	bit3 = new int[(n*m)];
	bit4 = new int[(n*m)];
	bit5 = new int[(n*m)];
	bit6 = new int[(n*m)];
	bit7 = new int[(n*m)];

	// image_7
	for(int i=0;i<n*m;i++){

	bit7[i] = pixels[i][0];

	}

	//image_6
	for(int i=0;i<n*m;i++){

	bit6[i] = pixels[i][1];

	}

	//image_5
	for(int i=0;i<n*m;i++){

	bit5[i] = pixels[i][2];

	}

	//image_4
	for(int i=0;i<n*m;i++){

	bit4[i] = pixels[i][3];

	}

	//image_3
	for(int i=0;i<n*m;i++){

	bit3[i] = pixels[i][4];

	}

	//image_2
	for(int i=0;i<n*m;i++){

	bit2[i] = pixels[i][5];

	}

	//image_1
	for(int i=0;i<n*m;i++){

	bit1[i] = pixels[i][6];

	}

	//image_0
	for(int i=0;i<n*m;i++){

	bit0[i] = pixels[i][7];

	}

	getImageFromArray(bit7,n,m,"image_7");

	getImageFromArray(bit6,n,m,"image_6");

	getImageFromArray(bit5,n,m,"image_5");

	getImageFromArray(bit4,n,m,"image_4");

	getImageFromArray(bit3,n,m,"image_3");

	getImageFromArray(bit2,n,m,"image_2");

	getImageFromArray(bit1,n,m,"image_1");

	getImageFromArray(bit0,n,m,"image_0");

	}

	public void getImageFromArray(int[] pixels, int width, int height,String name){

		BufferedImage im = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

		int k=0;

		int data[]=new int[width*height];

		int imData[][]= new int[width][height];

		for(int i=0;i<width;i++){

			for(int j=0;j<height;j++){

			imData[j][i] = pixels[k];

			k++;

			}

		}

		k=0;

		for(int i=0;i<width;i++){

			for(int j=0;j<height;j++){

				try{

					if(imData[i][j]==1){

						int r = 255;

						int g = 255;

						int b = 255;

						data[k] = (255 << 24) | (r << 16) | (g << 8) | b;  //WHITE

					}

					else if(imData[i][j]==0)

					{

						int r = 0;

						int g = 0;

						int b = 0;

						data[k] = (255 << 24) | (r << 16) | (g << 8) | b;  //BLACK
					}

					k++;

				}

				catch(Exception e)

				{}

			}

		}

		MemoryImageSource mis = new MemoryImageSource(width, height, data, 0, width);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image imgOut =  tk.createImage(mis);
		Graphics2D g = im.createGraphics();
		g.drawImage(imgOut, 0,0,null);

		try{

		ImageIO.write(im, "bmp", new File(name+".bmp"));

		}

		catch(IOException e ){

		System.out.println("saveJpeg "+e);

		}

	}

}