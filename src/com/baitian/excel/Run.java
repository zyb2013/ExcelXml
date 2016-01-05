package com.baitian.excel;

import java.io.File;

import javax.swing.JOptionPane;

public class Run {

	public static void main(String[] args) {
		if (args.length < 2) {
			JOptionPane.showMessageDialog(null, "����1��ԴĿ¼���ļ�·�� ����2��Ŀ��Ŀ¼");
			return;
		}
		File srcDir = new File(args[0]);
		File destDir = new File(args[1]);
		
		parse(srcDir, destDir);
	}

	private static void parse(File srcDir, File destDir) {
		if (!srcDir.exists()) {
			JOptionPane.showMessageDialog(null, "ԴĿ¼���ļ�������...");
			return;
		}
		
		if (!destDir.isDirectory()) {
			JOptionPane.showMessageDialog(null, "Ŀ��Ŀ¼�����ļ���...");
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
