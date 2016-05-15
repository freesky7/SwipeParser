package com.swipeparser;
/**
 * Class Account
 * @author Dolphin
 *
 */
public class Account {
	
	private String accountName;
	private String surName;
	private String firstName;
	private String accountNum;
	private String expMonth;
	private String expYear;
	private boolean hasTrack1;
	private boolean hasTrack2;
	private String track1;
	private String track2;
	private boolean isError;
	private String errMessage;
	
	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}
	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	/**
	 * @return the surName
	 */
	public String getSurName() {
		return surName;
	}
	/**
	 * @param surName the surName to set
	 */
	public void setSurName(String surName) {
		this.surName = surName;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the account
	 */
	public String getAccountNum() {
		return accountNum;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	/**
	 * @return the expMonth
	 */
	public String getExpMonth() {
		return expMonth;
	}
	/**
	 * @param expMonth the expMonth to set
	 */
	public void setExpMonth(String expMonth) {
		this.expMonth = expMonth;
	}
	/**
	 * @return the expYear
	 */
	public String getExpYear() {
		return expYear;
	}
	/**
	 * @param expYear the expYear to set
	 */
	public void setExpYear(String expYear) {
		this.expYear = expYear;
	}
	/**
	 * @return the isError
	 */
	public boolean isError() {
		return isError;
	}
	/**
	 * @param isError the isError to set
	 */
	public void setError(boolean isError) {
		this.isError = isError;
	}
	/**
	 * @return the errMessage
	 */
	public String getErrMessage() {
		return errMessage;
	}
	/**
	 * @param errMessage the errMessage to set
	 */
	public void setErrMessage(String errMessage) {
		this.isError = true;
		this.errMessage = errMessage;
	}
	/**
	 * @return the hasTrack1
	 */
	public boolean isHasTrack1() {
		return hasTrack1;
	}
	/**
	 * @param hasTrack1 the hasTrack1 to set
	 */
	public void setHasTrack1(boolean hasTrack1) {
		this.hasTrack1 = hasTrack1;
	}
	/**
	 * @return the hasTrack2
	 */
	public boolean isHasTrack2() {
		return hasTrack2;
	}
	/**
	 * @param hasTrack2 the hasTrack2 to set
	 */
	public void setHasTrack2(boolean hasTrack2) {
		this.hasTrack2 = hasTrack2;
	}
	/**
	 * @return the track1
	 */
	public String getTrack1() {
		return track1;
	}
	/**
	 * @param track1 the track1 to set
	 */
	public void setTrack1(String track1) {
		this.track1 = track1;
	}
	/**
	 * @return the track2
	 */
	public String getTrack2() {
		return track2;
	}
	/**
	 * @param track2 the track2 to set
	 */
	public void setTrack2(String track2) {
		this.track2 = track2;
	}
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
        String sep = "\r"; // line separator
        s.append("Name: ").append(accountName).append(sep);
        s.append("Surname: ").append(surName).append(sep);
        s.append("first name: ").append(firstName).append(sep);
        s.append("account: ").append(accountNum).append(sep);
        s.append("exp_month: ").append(expMonth).append(sep);
        s.append("exp_year: ").append(expYear).append(sep);
        s.append("has track1: ").append(hasTrack1).append(sep);
        s.append("has track2: ").append(hasTrack2).append(sep);
        s.append("TRACK 1: ").append(track1).append(sep);
        s.append("TRACK 2: ").append(track2).append(sep);
        return s.toString();
	}
}
