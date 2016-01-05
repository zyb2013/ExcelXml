package com.baitian.excel;

import java.io.File;
import java.io.FileFilter;

/**
 * Excel�ļ�������
 * @author Alias
 * @date 2015��12��29�� ����3:16:25
 *
 */
public class ExcelFileFilter implements FileFilter {

		@Override
		public boolean accept(File file) {
			if (file.isDirectory()) {
				return false;
			}
			String path = file.getAbsolutePath();
			String postfix = path.substring(path.lastIndexOf(".") + 1);
			if ("xlsx".equals(postfix)) {
				return true;
			}
			return false;
		}
		
	}