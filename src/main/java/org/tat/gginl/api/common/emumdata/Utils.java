package org.tat.gginl.api.common.emumdata;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.tat.gginl.api.domains.LifeProposal;

public class Utils {
	public static Date addYears(Date date, int years) {
		return org.apache.commons.lang3.time.DateUtils.addYears(date, years);
	}

	/**
	 * @param month
	 *            : if month = 0, it is January. If month = 11, it is December.
	 */
	public static Date getStartDateOfMonth(int month, int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * @param month
	 *            : if month = 0, it is January. If month = 11, it is December.
	 */
	public static Date getEndDateOfMonth(int month, int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.YEAR, year);
		DateTime dateTime = new DateTime(cal.getTime());
		DateTime lastTime = dateTime.dayOfMonth().withMaximumValue();
		return new Date(lastTime.getMillis());
	}

//	public static String getSystemPath() {
//		Object context = FacesContext.getCurrentInstance().getExternalContext().getContext();
//		String systemPath = ((ServletContext) context).getRealPath("/");
//		return systemPath;
//	}

	public static <T> boolean isNull(T t) {
		if (t == null) {
			return true;
		}
		return false;
	}

	public static <T> boolean isNotNull(T t) {
		if (t != null) {
			return true;
		}
		return false;
	}

	public static <T> boolean isLifeProposal(T lifeInfo) {
		if (lifeInfo instanceof LifeProposal) {
			return true;
		}
		return false;
	}

	public static String formattedCurrency(double value) {
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
		StringBuffer buffer = new StringBuffer(numberFormat.format(value));
		return buffer.substring(1).toString();
	}

	/**
	 * @param double
	 *            : if double = 1.0, return String 1.0000%.
	 */
	public static String formattedPercentage(double value) {
		DecimalFormat decimalFormat = new DecimalFormat("##,##0.0000");
		StringBuffer buffer = new StringBuffer(decimalFormat.format(value) + "%");
		return buffer.toString();
	}

	/**
	 * @param Date
	 *            : if Date, return Date eg. 12-30-2000.
	 */
	public static String formattedDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(date);
	}

	/**
	 * @param Date
	 * @param String
	 *            return Date string with dateFormat.
	 */
	public static String formattedDate(Date date, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(date);
	}

	public static boolean isEmpty(String value) {
		if (value == null || value.isEmpty()) {
			return true;
		}
		return false;
	}

}
