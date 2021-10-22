package com.erha.CRM.util;

import java.util.UUID;

public class FileNameUtil {
	public static String getUUIDFileName() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}

	public static String getRealFileName(String context) {
		// Content-Disposition: form-data; name="myfile"; filename="a_left.jpg"
		int index = context.lastIndexOf("=");
		String filename = context.substring(index + 2, context.length() - 1);
		return filename;
	}

	public static String getFileType(String fileName){
		int index = fileName.lastIndexOf(".");
		return fileName.substring(index);
	}
}
