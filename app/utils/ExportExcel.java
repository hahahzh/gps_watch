package utils;

import java.io.File;
import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * export excel
 * 
 * @author hzh
 *
 */
public class ExportExcel {

	public static void createExcel(OutputStream os, String sheetName,String[] title,List<String[]> datas) {
		createExcel(null,os,sheetName,title,datas);
	}
	
	public static void createExcel(String path, String sheetName,String[] title,List<String[]> datas) {
		createExcel(path,null,sheetName,title,datas);
	}
	
	public static void createExcel(String path, OutputStream os, String sheetName,String[] title,List<String[]> datas) {
		try {
			WritableWorkbook book = null;
			if(path == null || "".equals(path)){
				book = Workbook.createWorkbook(os);
			}else{
				book = Workbook.createWorkbook(new File(path));
			}
			WritableSheet sheet = book.createSheet(sheetName, 0);

			for(int i=0;i<title.length;i++){
				sheet.addCell(new jxl.write.Label(i, 0, title[i]));
			}
			String[] data = null;
			int width = 5;
			for(int i=0;i<datas.size();i++){
				data = datas.get(i);
				for(int j=0;j<data.length;j++){
					if(data[j] != null && data[j].length() > 5)width = (int)(data[j].length()*1.2);
					sheet.setColumnView(j, width);
					sheet.addCell(new jxl.write.Label(j, i+1, data[j]==null?"":"null".equals(data[j])?"":data[j]));
				}
			}
			// 写入数据并关闭文件
			book.write();
			book.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}