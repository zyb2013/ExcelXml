package com.baitian.excel;

import java.io.File;

import javax.swing.JOptionPane;

public class Run {

	public static void main(String[] args) {
		if (args.length < 2) {
			JOptionPane.showMessageDialog(null, "参数1：源目录或文件路径 参数2：目标目录");
			return;
		}
		File srcDir = new File(args[0]);
		File destDir = new File(args[1]);
		
		parse(srcDir, destDir);
	}

	private static void parse(File srcDir, File destDir) {
		if (!srcDir.exists()) {
			JOptionPane.showMessageDialog(null, "源目录或文件不存在...");
			return;
		}
		
		if (!destDir.isDirectory()) {
			JOptionPane.showMessageDialog(null, "目标目录不是文件夹...");
			return;
		}
		
		if (srcDir.isFile()) {
			ExcelToXmlConverter.parse(srcDir.getAbsolutePath(), destDir.getAbsolutePath());
			return;
		}
		
		if (srcDir.isDirectory()) {
			for (File each : srcDir.listFiles(new ExcelFileFilter())) {
				ExcelToXmlConverter.parse(each.getAbsolutePath(), destDir.getAbsolutePath());
			}
		}
	}
	
}
