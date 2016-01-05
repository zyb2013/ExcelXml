package com.baitian.excel;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;

/**
 * �����
 * 
 * @author Alias
 * @date 2015��12��4�� ����5:00:21
 *
 */
public class MainFrame {
	
	public static void main(String[] args) {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		jfc.showDialog(new JLabel(), "ѡ��");
		File file = jfc.getSelectedFile();
		if (file == null) {
			return;
		}
		if (file.isFile()) {
			ExcelToXmlConverter.parse(file.getAbsolutePath(), "");
			return;
		}
		if (file.isDirectory()) {
			for (File each : file.listFiles(new ExcelFileFilter())) {
				ExcelToXmlConverter.parse(each.getAbsolutePath(), "");
			}
		}
	}
	
}
