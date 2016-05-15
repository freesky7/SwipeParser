package com.swipeparser;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 
 * @author Dolphin
 *
 */
public class FileExporter {
	/**
	 * 
	 * @param account
	 * @param fop
	 * @return
	 * @throws IOException
	 */
	public static int writeObjToFile(Account account, FileOutputStream fop) throws IOException {
		// get the content in bytes
		String newLine;
		if(account.isError()) {
			newLine = account.getErrMessage() + '\n';
		} else {
			newLine = String.format(ParserConstants.OUTPUT_LINE_FORMAT, account.getAccountNum(), account.getExpMonth(),
					account.getExpYear());
		}
		
		byte[] contentInBytes = newLine.getBytes();

		fop.write(contentInBytes);
		return 0;
	}
}
