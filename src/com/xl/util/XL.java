package com.xl.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;

public class XL {

	
	//保存为16位bmp
	public static final void save_bmp16(String filename,Bitmap bmp) throws IOException
	{
		if (bmp != null)
		{

			int w = bmp.getWidth();
			int h =bmp.getHeight();
			int bmpw;
			int bmph;
			//检测是否为2的倍数
			if((w%2)==0)
				bmpw=w;
			else 
				bmpw=w+1;
			bmph=h;




			int[]pixels = new int[w * h];
			//获得颜色数组
			bmp.getPixels(pixels, 0, w, 0, 0, w, h);
			//将数组转换成byte
			byte[]rgb_565 = addBMP_RGB_565(pixels, w, h);
			//将byte转换成2的倍数
			byte[] bit_565 = new byte[bmpw * bmph * 2];
			for(int i=h-1;i>=0;i-=1)
			{
				System.arraycopy(rgb_565, w*i*2, bit_565, bmpw*i*2, w*2);
			}

			//bmp文件头
			//	 byte[]header = addBMPImageHeader(rgb.length);
			//bmp文件信息头
			//	 byte[]infos = addBMPImageInfosHeader(w, h);
			//bmp文件合成
			int bmstart=70;
			int size=bit_565.length;
			byte[]buffer = new byte[bmstart + size];
			System.out.println("buffer len = "+buffer.length);

			buffer[0] = 0x42;//B
			buffer[1] = 0x4D;//M
			buffer[2] = (byte) (size >> 0);//调色板长度
			buffer[3] = (byte) (size >> 8);
			buffer[4] = (byte) (size >> 16);
			buffer[5] = (byte) (size >> 24);
			buffer[6] = 0x00;
			buffer[7] = 0x00;
			buffer[8] = 0x00;
			buffer[9] = 0x00;
			buffer[10] =(byte) bmstart;
			buffer[11] = 0x00;
			buffer[12] = 0x00;
			buffer[13] = 0x00;

			buffer[14] =0x38; //0x28;
			buffer[15] = 0x00;
			buffer[16] = 0x00;
			buffer[17] = 0x00;
			buffer[18] = (byte) (bmpw >> 0);//宽度
			buffer[19] = (byte) (bmpw >> 8);
			buffer[20] = (byte) (bmpw >> 16);
			buffer[21] = (byte) (bmpw >> 24);
			buffer[22] = (byte) (bmph >> 0);//高度
			buffer[23] = (byte) (bmph >> 8);
			buffer[24] = (byte) (bmph >> 16);
			buffer[25] = (byte) (bmph >> 24);
			buffer[26] = 0x01;
			buffer[27] = 0x00;
			buffer[28] = 16;//bmp位数
			buffer[29] = 0x00;
			buffer[30] = 0x03; //0x00;
			buffer[31] = 0x00;
			buffer[32] = 0x00;
			buffer[33] = 0x00;
			buffer[34] = 0x00;
			buffer[35] = (byte)0xc8;//0x00;
			buffer[36] = 0x00;
			buffer[37] = 0x00;
			buffer[38] = (byte)0xA0; //(byte) 0xE0;
			buffer[39] = (byte)0x0F; // 0x01;
			buffer[40] = 0x00;
			buffer[41] = 0x00;
			buffer[42] = (byte)0xA0; //0x02;
			buffer[43] = (byte)0x0f; //0x03;
			buffer[44] = 0x00;
			buffer[45] = 0x00;
			buffer[46] = 0x00;
			buffer[47] = 0x00;
			buffer[48] = 0x00;
			buffer[49] = 0x00;
			buffer[50] = 0x00;
			buffer[51] = 0x00;
			buffer[52] = 0x00;
			buffer[53] = 0x00;

			buffer[54]=0;
			buffer[55]=(byte)0xF8;
			buffer[56]=0;
			buffer[57]=0;
			buffer[58]=(byte)0xE0;
			buffer[59]=(byte)0x07;
			buffer[60]=0;
			buffer[61]=0;
			buffer[62]=0x1F;
			buffer[63]=0;
			buffer[64]=0;
			buffer[67]=0;
			buffer[68]=0;
			buffer[69]=0;
			buffer[70]=0;



			// System.arraycopy(header, 0, buffer, 0,	header.length);
			// System.arraycopy(infos, 0, buffer, 14,		infos.length);
			System.arraycopy(bit_565, 0, buffer, bmstart,		bit_565.length); 
			
			try {
				FileOutputStream fos;
				fos = new FileOutputStream(new File(filename));
				fos.write(buffer);
				fos.close(); // 关闭输出流
		        System.out.println("文件关闭");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}


  //保存为bmp
	public void save_bmp24(String filename,Bitmap bmp) throws IOException
	{
		if (bmp != null)
		{

			int w = bmp.getWidth(),
				h =bmp.getHeight();

			int[]pixels = new int[w * h];
			bmp.getPixels(pixels, 0, w, 0, 0, w, h);
			byte[]rgb = addBMP_RGB_888(pixels, w, h);
			byte[]header = addBMPImageHeader(rgb.length);
			byte[]infos = addBMPImageInfosHeader(w, h);
			byte[]buffer = new byte[54 + rgb.length];
			System.arraycopy(header, 0, buffer, 0,
											 header.length);
			System.arraycopy(infos, 0, buffer, 14,
											 infos.length);
			System.arraycopy(rgb, 0, buffer, 54,
											 rgb.length); 
			FileOutputStream fos = null;
			try
			{
				fos = new FileOutputStream(filename);
				fos.write(buffer);
				fos.flush();
			} 
			catch(FileNotFoundException e)
			{
				e.printStackTrace();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			finally {
				fos.close();
			}
		}
	}




	// BMP文件头
	private byte[] addBMPImageHeader(int size)
	{
		byte[]buffer = new byte[14];
		buffer[0] = 0x42;//B
		buffer[1] = 0x4D;//M
		buffer[2] = (byte) (size >> 0);//调色板长度
		buffer[3] = (byte) (size >> 8);
		buffer[4] = (byte) (size >> 16);
		buffer[5] = (byte) (size >> 24);
		buffer[6] = 0x00;
		buffer[7] = 0x00;
		buffer[8] = 0x00;
		buffer[9] = 0x00;
		buffer[10] = 0x36;
		buffer[11] = 0x00;
		buffer[12] = 0x00;
		buffer[13] = 0x00;
		return buffer;
	}
	// BMP文件信息头(32位)
	private byte[] addBMPImageInfosHeader(int w, int h)
	{
		byte[]buffer = new byte[40];
		
		buffer[0] = 0x42;
		buffer[1] = 0x4D;
		buffer[2] = 0x00;
		buffer[3] = 0x00;
		buffer[4] = (byte) (w >> 0);//宽度
		buffer[5] = (byte) (w >> 8);
		buffer[6] = (byte) (w >> 16);
		buffer[7] = (byte) (w >> 24);
		buffer[8] = (byte) (h >> 0);//高度
		buffer[9] = (byte) (h >> 8);
		buffer[10] = (byte) (h >> 16);
		buffer[11] = (byte) (h >> 24);
		buffer[12] = 0x01;
		buffer[13] = 0x00;
		buffer[14] = 32;//bmp位数
		buffer[15] = 0x00;
		buffer[16] = 0x00;
		buffer[17] = 0x00;
		buffer[18] = 0x00;
		buffer[19] = 0x00;
		buffer[20] = 0x00;
		buffer[21] = 0x00;
		buffer[22] = 0x00;
		buffer[23] = 0x00;
		buffer[24] = (byte) 0xE0;
		buffer[25] = 0x01;
		buffer[26] = 0x00;
		buffer[27] = 0x00;
		buffer[28] = 0x02;
		buffer[29] = 0x03;
		buffer[30] = 0x00;
		buffer[31] = 0x00;
		buffer[32] = 0x00;
		buffer[33] = 0x00;
		buffer[34] = 0x00;
		buffer[35] = 0x00;
		buffer[36] = 0x00;
		buffer[37] = 0x00;
		buffer[38] = 0x00;
		buffer[39] = 0x00;
		return buffer;
	}


//将32位位图数组转换成byte数组
	static byte[] addBMP_RGB_888(int[]b, int w, int h)
	{
		int len = b.length;
		System.out.println(b.length);
		byte[]buffer = new byte[w * h * 3];
		int offset = 0;
		for (int i = len ; i >= w; i -= w)
		{
			// DIB文件格式最后一行为第一行，每行按从左到右顺序
			int end = i-1, start = i - w ;
			for (int j = start; j <= end; j++)
			{
				buffer[offset] = (byte) (b[j] >> 0);
				buffer[offset + 1] = (byte) (b[j] >> 8);
				buffer[offset + 2] = (byte) (b[j] >> 16);
				offset += 3;
			}
		}
		return buffer;
	}

	//将32位数组转换成565格式
	static byte[] addBMP_RGB_565(int[]b, int w, int h)
	{
		
		System.out.println(b.length);
		byte[]buffer = new byte[w * h * 2];
		int offset = 0;
		for (int i = h-1 ; i >= 0; i --)
		{
			// DIB文件格式最后一行为第一行，每行按从左到右顺序
			int end = i*w+w, start = i*w ;
			for (int j = start; j < end; j++)
			{
				short bit16;
				bit16 = (short) ( (b[j]>>8 & 0xf800 ) + (b[j]>>5 & 0x7e0) + (b[j]>>3 & 0x1f) );
				buffer[offset]=(byte)bit16;
				buffer[offset + 1] = (byte) (bit16>>8);

				offset += 2;
			}
		}
		return buffer;
	}
}
