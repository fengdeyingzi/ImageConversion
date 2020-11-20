package com.xl.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Bitmap.CompressFormat;

public class ImageUtil {
	
	//将图片压缩为指定宽高
	public static void zoomBitmap(Bitmap bitmap, int width,int height, String filename){
		System.out.println(""+width+" "+height+" "+filename+" "+bitmap.image.getType());
		Bitmap temp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//		temp.image = new BufferedImage(width, height, bitmap.image.getType());
		Canvas canvas = new Canvas(temp);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		Rect src = new Rect(0,0, bitmap.getWidth(), bitmap.getHeight());
		Rect dst = new Rect(0,0, width, height);
		canvas.drawBitmap(bitmap, src,dst, paint);
		canvas.dispose();
		try {
			temp.compress(CompressFormat.PNG, 100, new FileOutputStream(new File(filename)));
//			ImageIO.write(temp.image,"png", new File("D:\\2.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//将图片压缩为指定宽高
		public static void zoomBitmapFromFile(String input_file, int width,int height, String output_file){
			Bitmap bitmap = BitmapFactory.decodeFile(input_file);
			Bitmap temp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//			temp.image = new BufferedImage(width, height, bitmap.image.getType());
			Canvas canvas = new Canvas(temp);
			Paint paint = new Paint();
			paint.setAntiAlias(true);
			Rect src = new Rect(0,0, bitmap.getWidth(), bitmap.getHeight());
			Rect dst = new Rect(0,0, width, height);
			canvas.drawBitmap(bitmap, src,dst, paint);
			canvas.dispose();
			try {
				System.out.println(output_file);
				temp.compress(CompressFormat.PNG, 100, new FileOutputStream(new File(output_file)));
//				ImageIO.write(temp.image,"png", new File("D:\\2.png"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	public static Bitmap zoomBitmap(Bitmap bitmap, int width,int height){
//		System.out.println(""+width+" "+height+" "+filename+" "+bitmap.image.getType());
		Bitmap temp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//		temp.image = new BufferedImage(width, height, bitmap.image.getType());
		Canvas canvas = new Canvas(temp);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		Rect src = new Rect(0,0, bitmap.getWidth(), bitmap.getHeight());
		Rect dst = new Rect(0,0, width, height);
		canvas.drawBitmap(bitmap, src,dst, paint);
		canvas.dispose();
		return temp;
	}
	
	//将图片按最大宽度压缩
	public static void zoomBitmapWidth(Bitmap bitmap, int width, String filename){
		int height = width * bitmap.getHeight()/bitmap.getWidth();
		if(width>=bitmap.getWidth()){
			width = bitmap.getWidth();
			height = bitmap.getHeight();
		}
		else{
			height = width * bitmap.getHeight()/bitmap.getWidth();
		}
		zoomBitmap(bitmap, width, height, filename);
	}
	
	//将图片按最大高度压缩
	public static void zoomBitmapHeight(Bitmap bitmap, int height, String filename){
		int width = height * bitmap.getWidth()/bitmap.getHeight();
		if(height>=bitmap.getHeight()){
			width = bitmap.getWidth();
			height = bitmap.getHeight();
		}
		else{
			width = height * bitmap.getWidth()/bitmap.getHeight();
		}
		zoomBitmap(bitmap, width, height, filename);
	}
	
	public static final int
	_JPG=0,
	_PNG=1,
	_BMP565=2;
	
	public static int saveBitmap(Bitmap bitmap,String filename,int type,int load)
	{
		try
		{
			FileOutputStream out = new FileOutputStream(filename);
      switch(type)
			{
				case _JPG:
			  if(bitmap.compress(Bitmap.CompressFormat.JPEG, load, out))
				return 0;
			  break;
				case _PNG:
					//bitmap.setHasAlpha(true);
					//Bitmap savebitmap= bitmap.copy(Bitmap.Config.ARGB_8888,true);
					//bitmap.eraseColor(0);
					
					//Bitmap savebitmap=Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),Bitmap.Config.ARGB_8888);
					
					//Canvas canvas = new Canvas(savebitmap);
					//canvas.drawBitmap(bitmap,0,0,null);
					
					/*
					int pixs[]=new int [bitmap.getWidth()*bitmap.getHeight()];
					bitmap.getPixels(pixs,0,bitmap.getWidth(), 0,0,bitmap.getWidth(),bitmap.getHeight());
					savebitmap.setPixels(pixs, 0, bitmap.getWidth(),0,0,bitmap.getWidth(),bitmap.getHeight());
					*/
					if(bitmap.compress(Bitmap.CompressFormat.PNG,load,out))
						return 0;
				break;
				case _BMP565:
		try {
			XL.save_bmp16(filename, bitmap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
					return 0;
			}
		}
		catch(FileNotFoundException e)
		{
			return -1;
		}
	return -1;
	}
	
	public static byte[] convertBitmap2Bmp(String filename,Bitmap bitmap) {
		if (bitmap == null) {
			return null;
		}
		
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		int wWidth = w * 3 + w % 4;
		int bmpDateSize = h * wWidth;
		int size = 14 + 40 + bmpDateSize;
		byte buffer[] = new byte[size];
		
		// 1.BMP文件头 14
		buffer[0] = 0x42; //bfType 2bytes
		buffer[1] = 0x4D;
		buffer[2] = (byte) ((size >> 0) & 0xFF); //bfSize 4bytes
		buffer[3] = (byte) ((size >> 8) & 0xFF);
		buffer[4] = (byte) ((size >> 16) & 0xFF);
		buffer[5] = (byte) ((size >> 24) & 0xFF);
		buffer[6] = 0x00; //bfReserved1 2bytes
		buffer[7] = 0x00;
		buffer[8] = 0x00; //bfReserved2 2bytes
		buffer[9] = 0x00;
		buffer[10] = 0x36; //bfOffBits 14+40 4bytes
		buffer[11] = 0x00;
		buffer[12] = 0x00;
		buffer[13] = 0x00;
		// 2.BMP信息头 40
		buffer[14] = 0x28; //biSize 40 4bytes
		buffer[15] = 0x00;
		buffer[16] = 0x00;
		buffer[17] = 0x00;
		buffer[18] = (byte) ((w >> 0) & 0xFF); //biWidth 4bytes
		buffer[19] = (byte) ((w >> 8) & 0xFF);
		buffer[20] = (byte) ((w >> 16) & 0xFF);
		buffer[21] = (byte) ((w >> 24) & 0xFF);
		buffer[22] = (byte) ((h >> 0) & 0xFF); //biHeight 4bytes
		buffer[23] = (byte) ((h >> 8) & 0xFF);
		buffer[24] = (byte) ((h >> 16) & 0xFF);
		buffer[25] = (byte) ((h >> 24) & 0xFF);
		buffer[26] = 0x01; //biPlanes 2bytes
		buffer[27] = 0x00;
		buffer[28] = 0x18; //biBitCount 24位位图 2bytes 
		buffer[29] = 0x00;
		buffer[30] = 0x00; //biCompression 4bytes
		buffer[31] = 0x00;
		buffer[32] = 0x00;
		buffer[33] = 0x00;
		buffer[34] = 0x00; //biSizeImage 4bytes
		buffer[35] = 0x00;
		buffer[36] = 0x00;
		buffer[37] = 0x00;
		buffer[38] = 0x00; //biXpelsPerMeter 4bytes
		buffer[39] = 0x00;
		buffer[40] = 0x00;
		buffer[41] = 0x00;
		buffer[42] = 0x00; //biYPelsPerMeter 4bytes
		buffer[43] = 0x00;
		buffer[44] = 0x00;
		buffer[45] = 0x00;
		buffer[46] = 0x00; //biClrUsed 4bytes
		buffer[47] = 0x00;
		buffer[48] = 0x00;
		buffer[49] = 0x00;
		buffer[50] = 0x00; //biClrImportant 4bytes
		buffer[51] = 0x00;
		buffer[52] = 0x00;
		buffer[53] = 0x00;
		
		byte bmpData[] = new byte[bmpDateSize];
		for (int nCol = 0, nRealCol = h - 1; nCol < h; ++nCol, --nRealCol) {
			for (int wRow = 0, wByteIdex = 0; wRow < w; wRow++, wByteIdex += 3) {
				int clr = bitmap.getPixel(wRow, nCol);
				//clr = clr == 0 ? 0xFFFFFF : clr; //黑色背景转为白色
				bmpData[nRealCol * wWidth + wByteIdex] = (byte) (clr&0xff);
				bmpData[nRealCol * wWidth + wByteIdex + 1] = (byte) ((clr>>8)&0xff);
				bmpData[nRealCol * wWidth + wByteIdex + 2] = (byte)((clr>>16)&0xff);
			}
		}
		
		System.arraycopy(bmpData, 0, buffer, 54, bmpDateSize);
		
		// 输出到sdcard查看
		try {
			FileOutputStream fos = new FileOutputStream(new File(filename));
			fos.write(buffer);
			fos.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return buffer;
	}
	

}
