package com.tcc.common.utils;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
	/**
	 * 将byte数组写入文件
	 * 
	 * @param file
	 * @param bytes
	 */
	public static void writeByteArrayToFile(File file, byte[] bytes) {
		FileOutputStream fos = null;
		BufferedWriter bw = null;
		InputStream is = null;
		try {
			fos = new FileOutputStream(file);
			bw = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));
			is = new ByteArrayInputStream(bytes);
			byte[] buff = new byte[1024];
			int len = 0;
			while ((len = is.read(buff)) != -1) {
				fos.write(buff, 0, len);
			}
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
				if (fos != null) {
					fos.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException ie) {
			}
		}
	}

	/**
	 * 将字符串写入文件
	 * @param content
	 * @param path
	 * @param charsetName
	 */
	public static void writeFile(String content, String path, String charsetName) {
		FileOutputStream fos = null;
		BufferedWriter bw = null;
		try {
			File file = new File(path);
			fos = new FileOutputStream(file);
			bw = new BufferedWriter(new OutputStreamWriter(fos, charsetName));
			bw.write(content);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fos != null)
					fos.close();
			} catch (IOException ie) {
			}
		}
	}
	/**
	 * 获得目录下所有文件
	 * @param dirPath
	 * @param isDeep
	 * @return
	 */
	public static List<File> getAllFiles(String dirPath, boolean isDeep) {
		File dir = new File(dirPath);
		List<File> filelist = new ArrayList<File>();
		if (dir.isDirectory()) {
			File[] dirFiles = dir.listFiles();
			for (File file : dirFiles) {
				if (file.isFile()) {
					filelist.add(file);
				} else if (file.isDirectory() && isDeep) {
					try {
						filelist.addAll(getAllFiles(file.getCanonicalPath(), isDeep));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else if (dir.isFile()) {
			filelist.add(dir);
		}
		return filelist;
	}
	/**
	 * 获取目录下所有文件名称
	 * @param dirPath
	 * @param isDeep
	 * @return
	 */
	public static List<String> getAllFileNames(String dirPath, boolean isDeep) {
		File dir = new File(dirPath);
		List<String> filelist = new ArrayList<String>();
		if (dir.isDirectory()) {
			File[] dirFiles = dir.listFiles();
			for (File file : dirFiles) {
				if (file.isFile()) {
					filelist.add(file.getName());
				} else if (file.isDirectory() && isDeep) {
					try {
						filelist.addAll(getAllFileNames(file.getCanonicalPath(), isDeep));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else if (dir.isFile()) {
			filelist.add(dir.getName());
		}
		return filelist;
	}
	/**
	 * 复制文件
	 * @param sourceFileName
	 * @param targetFileName
	 */
	public static void copyFile(String sourceFileName, String targetFileName) {
		copyFile(new File(sourceFileName), targetFileName);
	}
	/**
	 * 复制文件
	 * @param sourceFile
	 * @param targetFileName
	 */
	public static void copyFile(File sourceFile, String targetFileName) {
		if (sourceFile.exists() && sourceFile.isFile()) {
			File targetFile = new File(targetFileName);
			FileOutputStream fos = null;
			FileInputStream fis = null;
			try {
				fos = new FileOutputStream(targetFile);
				fis = new FileInputStream(sourceFile);
				byte[] b = new byte[1024];
				int n = 0;
				while ((n = fis.read(b)) != -1) {
					fos.write(b, 0, n);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fos != null) {
						fos.close();
					}
					if (fis != null) {
						fis.close();
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 将文件转成byte数组
	 * @param file
	 * @return
	 */
	public static byte[] File2byte(File file) {
		byte[] buffer = null;
		try {

			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}
	/**
	 * 文件字符集转换
	 * @param filename
	 * @param outFilename
	 * @param ocharset
	 * @param xcharset
	 * @throws IOException
	 */
	public static void FileCharsetConvert(String filename, String outFilename, String ocharset, String xcharset) throws IOException {
		FileInputStream fis = new FileInputStream(filename);
		File f = new File(outFilename);
		if (!f.exists())
			f.createNewFile();
		FileOutputStream fos = new FileOutputStream(f);
		// io流转接
		InputStreamReader isr = new InputStreamReader(fis, ocharset);
		OutputStreamWriter osw = new OutputStreamWriter(fos, xcharset);
		// 读取：
		char[] cs = new char[1024];
		int len = 0;
		while ((len = isr.read(cs)) != -1) {
			osw.write(cs, 0, len);
		}
		// 关闭流
		osw.flush();
		osw.close();
		isr.close();
	}
}
