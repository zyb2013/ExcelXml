package com.baitian.excel;

import java.io.File;
import java.io.FileFilter;

/**
 * Excel文件过滤器
 * @author Alias
 * @date 2015年12月29日 下午3:16:25
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