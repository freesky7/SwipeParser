package com.swipeparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

/**
 * 
 * @author Dolphin
 *
 */
public class Parser {
	/**
	 * 
	 * @param ifile
	 * @return
	 */
	public Account lineToObj(String line) {
		Account account = new Account();

		if (line == null || "".equals(line.trim())) {
			account.setErrMessage(ParserConstants.ERROR_EMPTY_LINE);
		}

		// --- Determine the presence of special characters
		int nKeyTrack1 = line.indexOf(ParserConstants.KEYOFF_1);
		int nKeyTrack2 = line.indexOf(ParserConstants.KEYOFF_2);

		if (nKeyTrack1 > 0 && nKeyTrack2 > 0) {
			account.setHasTrack1(true);
			account.setHasTrack2(true);

			parseTrack12(account, line);
			return account;
		}
		if (nKeyTrack1 > 0) {
			account.setHasTrack1(true);

			parseTrack1(account, line);
			return account;
		}
		if (nKeyTrack2 > 0) {
			account.setHasTrack2(true);

			parseTrack2(account, line);
			return account;
		}

		account.setErrMessage(ParserConstants.ERROR_INVALID_FORMAT);
		return account;
	}

	/**
	 * 
	 * @param account
	 * @param line
	 */
	private void parseTrack12(Account account, String line) {
		// -----------------------------------------------------------------------------
		// --- Track 1 & 2 cards
		// --- Ex:
		// B1234123412341234^CardUser/John^030510100000019301000000877000000?;1234123412341234=0305101193010877?
		// -----------------------------------------------------------------------------
		String strCutUpSwipe = "" + line + ' ';
		String[] arrayStrSwipe = strCutUpSwipe.split("\\^");

		if (arrayStrSwipe.length > 2) {
			account.setAccountNum(trimAll(stripAlpha(arrayStrSwipe[0].substring(1, arrayStrSwipe[0].length()))));
			account.setAccountName(trimAll(arrayStrSwipe[1]));
			account.setExpMonth(trimAll(arrayStrSwipe[2].substring(2, 4)));
			account.setExpYear(trimAll(/*"20" +*/ arrayStrSwipe[2].substring(0, 2)));
			
			if (line.startsWith("%")) {
				line = trimAll(line.substring(1, line.length()));
			}

			int track2sentinel = line.indexOf(";");
			if (track2sentinel != -1) {
				account.setTrack1(trimAll(line.substring(0, track2sentinel)));
				account.setTrack2(trimAll(line.substring(track2sentinel)));
			}
			
			//--- parse name field into first/last names
	        int nameDelim = account.getAccountName().indexOf("/");
	        if( nameDelim != -1 ){
	        	account.setSurName(trimAll(account.getAccountName().substring(0, nameDelim)));
	        	account.setFirstName(trimAll(account.getAccountName().substring(nameDelim+1)));
	        }
		} else {
			account.setErrMessage(ParserConstants.ERROR_INVALID_FORMAT);
		}
		
	}

	/**
	 * 
	 * @param account
	 * @param line
	 */
	private void parseTrack1(Account account, String line) {
		String strCutUpSwipe = "" + line + ' ';
		String[] arrayStrSwipe = strCutUpSwipe.split("\\^");

		if (arrayStrSwipe.length > 2) {
			account.setAccountNum(trimAll(stripAlpha(arrayStrSwipe[0].substring(1, arrayStrSwipe[0].length()))));
			account.setAccountName(trimAll(arrayStrSwipe[1]));
			account.setExpMonth(trimAll(arrayStrSwipe[2].substring(2, 4)));
			account.setExpYear(trimAll(/*"20" +*/ arrayStrSwipe[2].substring(0, 2)));
			
			//--- Different card swipe readers include or exclude the % in
	        //--- the front of the track data - when it's there, there are
	        //---   problems with parsing on the part of credit cards processor - so strip it off
	        if ( line.startsWith("%")) { 
	            line = trimAll(line.substring(1,line.length()));
	            account.setTrack1(String.format("%s", line));
	        }
	  
	        //--- Add track 2 data to the string for processing reasons
//	        if (sTrackData.substring(sTrackData.length-1,1) != '?')  //--- Add a ? if not present
//	        { sTrackData = sTrackData + '?'; }
			String track2 = ';' + account.getAccountNum() + '=' + account.getExpYear().substring(2, 4)
					+ account.getExpMonth() + "111111111111?";
			account.setTrack2(track2);
	        line = line + track2;
	  
			//--- parse name field into first/last names
	        int nameDelim = account.getAccountName().indexOf("/");
	        if( nameDelim != -1 ){
	        	account.setSurName(trimAll(account.getAccountName().substring(0, nameDelim)));
	        	account.setFirstName(trimAll(account.getAccountName().substring(nameDelim+1)));
	        }
		} else {
			account.setErrMessage(ParserConstants.ERROR_INVALID_FORMAT);
		}
	}

	/**
	 * 
	 * @param account
	 * @param line
	 */
	private void parseTrack2(Account account, String line) {
		int nSeperator  = line.indexOf("=");
	    String  sCardNumber = trimAll(line.substring(1,nSeperator));
	    String  sYear       = trimAll(/*"20" +*/ line.substring(nSeperator+1,2));
	    String  sMonth      = trimAll(line.substring(nSeperator+3,2));

	      // alert(sCardNumber + ' -- ' + sMonth + '/' + sYear);

	      account.setAccountNum(trimAll(stripAlpha(sCardNumber)));
	      account.setExpMonth(trimAll(sMonth));
	      account.setExpYear(trimAll(sYear));
	        
	      //--- Different card swipe readers include or exclude the % in the front of the track data - when it's there, 
	      //---  there are problems with parsing on the part of credit cards processor - so strip it off
	      if ( line.startsWith("%") ){
	        line = line.substring(1,line.length());
	      }
	}

	/**
	 * Strip alpha
	 * 
	 * @param sInput
	 * @return
	 */
	private String stripAlpha(String sInput) {
		if (sInput == null) {
			return "";
		}
		return sInput.replaceAll("[^\\d.]", "");
	}

	/**
	 * Process parse file
	 * 
	 * @return
	 */
	public String parse(File infile, JProgressBar progressBar) {

		String path = infile.getParent();
		File outFile = new File(String.format("%s/output_%d.txt", path, System.currentTimeMillis()));
		FileOutputStream fop = null;

		try {
			if (!outFile.exists()) {
				outFile.createNewFile();
			}
			fop = new FileOutputStream(outFile);

			readAndParse(infile, fop, progressBar);
			fop.flush();
			fop.close();
		} catch (Exception e) {
			return "";
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return outFile.getAbsolutePath();
	}

	/**
	 * Read and parse file
	 * 
	 * @param inFile
	 * @param fop
	 */
	private void readAndParse(File inFile, FileOutputStream fop, JProgressBar progressBar) {

		BufferedReader br = null;
		try {
//			String sCurrentLine;

			br = new BufferedReader(new FileReader(inFile));

//			while ((sCurrentLine = br.readLine()) != null) {
			String[] lines = readAll(inFile);
			for(int i = 0; i < lines.length; i++) {
				String line = lines[i].trim();
//				progressBar.setValue((int)i*10/lines.length);
				if (!"".equals(line.trim())) {
					Account account = lineToObj(line);
					System.out.println(account.toString());
					FileExporter.writeObjToFile(account, fop);
				}
			}
//			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * @param inFile
	 * @return
	 * @throws IOException
	 */
	private String[] readAll(File inFile) throws IOException {
		FileInputStream fis = new FileInputStream(inFile);
		byte[] data = new byte[(int) inFile.length()];
		fis.read(data);
		fis.close();

		String str = new String(data, "UTF-8");
		
		str = str.replaceAll("\\?[\n\r|\n][ \t\n\\x0B\f\r]*;", "?;");
		return str.split("[\n\r|\n]");
	}
	/**
	 * 
	 * @param input
	 * @return
	 */
	private String trimAll(String input) {
		if(input != null && !"".equals(input)) {
			input = input.trim();
		}
		
		return input;
	}
}
