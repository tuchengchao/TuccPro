package com.tcc.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class File2ZipUtil {
	/**
	 * 将文件或文件夹打包成zip
	 * @param baseDir 文件夹路径
	 * @param finalZipName 压缩包名(完整路径)
	 * @return
	 * @throws IOException
	 */
	public static void file2zip(String baseDir, String finalZipName) throws IOException {
		File file = new File(baseDir);
		if(file.exists()){
			ZipOutputStream zipOutput = new ZipOutputStream(
					new BufferedOutputStream(new FileOutputStream(finalZipName)));
			String path = file.getName();
			if(file.isFile()){
				zip(zipOutput, file, path);
			}
			else{
				compressZip(zipOutput, file, path);
			}
			zipOutput.closeEntry();
			zipOutput.close();
			System.out.println("成功生成zip文件");
		}
		else{
			System.out.println("源文件不存在");
		}
	}

	/**
	 * 递归子文件夹
	 * @param zipOutput
	 * @param file
	 * @param base
	 * @throws IOException
	 */
	private static void compressZip(ZipOutputStream zipOutput, File file, String base) throws IOException {

		if (file.isDirectory()) {
			File[] listFiles = file.listFiles();// 列出所有的文件
			for (File f : listFiles) {
				if (f.isDirectory()) {
					compressZip(zipOutput, f, base + File.separator + f.getName());
				} else {
					zip(zipOutput, f, base + File.separator + f.getName());
				}
			}
		} else {
			zip(zipOutput, file, base + File.separator + file.getName());
		}
	}

	/**
	 * 压缩
	 * @param zipOutput
	 * @param file
	 * @param name
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private static void zip(ZipOutputStream zipOutput, File file, String name)
			throws IOException, FileNotFoundException {
		ZipEntry zEntry = new ZipEntry(name);
		zipOutput.putNextEntry(zEntry);
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		byte[] buffer = new byte[1024];
		int read = 0;
		while ((read = bis.read(buffer)) != -1) {
			zipOutput.write(buffer, 0, read);
		}
		bis.close();
	}
	
	public static void main(String[] args) {
		try {
			file2zip("F:\\zipDir", "F:\\pakage.zip");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
