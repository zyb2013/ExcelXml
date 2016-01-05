package com.baitian.excel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.swing.JOptionPane;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Convert Excel to XML
 * @author zhuyuanbiao
 *
 */
public class ExcelToXmlConverter {
	
	private static final String XML_POSTFIX = ".xml";
	
	private static final String TEMPLATE_POSTFIX = ".ftl";
	
	private static final String ENCODING = "utf-8";
	
	@SuppressWarnings("deprecation")
	public static void parse(String path, String destPath) {
		ExcelReader reader = new ExcelReader();
		reader.readExcel(path);
		
		Configuration cfg = new Configuration();
		String basePath = path.substring(0, path.lastIndexOf(File.separatorChar));
		cfg.setDefaultEncoding(ENCODING);
		Properties p = new Properties();
		
		try {
			p.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("freemarker.properties"));
			cfg.setSettings(p);
			cfg.setDirectoryForTemplateLoading(new File(basePath + File.separatorChar + "ftl"));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
		Writer out = null;
		
		for (Entry<String, List<Map<String, Object>>> entry : reader.getRowsOfSheets().entrySet()) {
			if (entry.getValue().isEmpty()) {
				continue;
			}
			try {
				Template template = cfg.getTemplate(entry.getKey() + TEMPLATE_POSTFIX);
				if (!"".equals(destPath)) {
					basePath = destPath;
				}
				File targetFile = new File(basePath + "/" + entry.getKey() + XML_POSTFIX);
				out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile), ENCODING));
				Map<String, List<Map<String, Object>>> map = new HashMap<>();
				
				map.put("entities", entry.getValue());
				template.process(map, out);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	
}
