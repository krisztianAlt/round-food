package com.example.roundfood.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DateAndTime {

	Logger logger = LoggerFactory.getLogger(DateAndTime.class);
	
	private final static int OPENING_HOUR = 11;
	private final static int OPENING_MINUTE = 0;
	private final static int CLOSING_HOUR = 22;
	private final static int CLOSING_MINUTE = 0;
	private final static int MINIMUM_WAITING_BEFORE_FIRST_CHOOSABLE_SHIPPING_DATE_AND_TIME = 40;
	private final static int SHIPPING_MINUTE_INTERVAL = 20;
	private final static int NUMBER_OF_SHIPPING_DAYS = 3;
	private final static TimeZone TIME_ZONE = TimeZone.getTimeZone("CET");
	
	public HashMap<String, List<Date>> getChoosableShippingDatesAndTimes() {
		Calendar calendar = Calendar.getInstance();
		
		logger.info("DEFAULT TIME ZONE: " + calendar.getTimeZone().getDisplayName());
		
		calendar.setTimeZone(TIME_ZONE);
		
		logger.info("TIME ZONE AFTER SETTING: " + calendar.getTimeZone().getDisplayName());
		
		SimpleDateFormat dateAndTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDateAndTime = calendar.getTime();
		calendar.add(Calendar.HOUR_OF_DAY, 1);
		Date oneHourLater = calendar.getTime();
		
		Date closingDateAndTimeOnCurrentDay = getClosingDateAndTimeOnCurrentDay(currentDateAndTime);
	
		Date firstChoosableShippingDateAndTime = calendar.getTime();
		calendar.setTime(currentDateAndTime);
		
		// Test:
		try {
			logger.info("CURRENT: " + dateAndTimeFormatter.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(currentDateAndTime)) +
					", ONEHOURLATER: " + dateAndTimeFormatter.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(oneHourLater)) +
					", CLOSING: " + dateAndTimeFormatter.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(closingDateAndTimeOnCurrentDay)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (oneHourLater.after(closingDateAndTimeOnCurrentDay)) {
			firstChoosableShippingDateAndTime = getFirstChoosableShippingDateAndTimeOnNextDay(currentDateAndTime);
		} else {
			calendar.add(Calendar.MINUTE, MINIMUM_WAITING_BEFORE_FIRST_CHOOSABLE_SHIPPING_DATE_AND_TIME);
			
			if (firstChoosableShippingDateAndTime.before(getOpeningDateAndTimeOnCurrentDay(firstChoosableShippingDateAndTime))) {
				firstChoosableShippingDateAndTime = getOpeningDateAndTimeOnCurrentDay(firstChoosableShippingDateAndTime);
			} else {
				boolean firstChoosableShippingDateAndTimeIsNeeded = true;
				
				while (firstChoosableShippingDateAndTimeIsNeeded == true) {
					int actualMinute = calendar.get(Calendar.MINUTE);
					
					if (actualMinute % SHIPPING_MINUTE_INTERVAL == 0) {
						firstChoosableShippingDateAndTimeIsNeeded = false;
					} else {
						calendar.add(Calendar.MINUTE, 1);
						firstChoosableShippingDateAndTime = calendar.getTime();	
					}
				}
			}
		}
		
		HashMap<String, List<Date>> choosableShippingDatesAndTimes = initializeChoosableShippingDatesAndTimes(firstChoosableShippingDateAndTime);
		
		return choosableShippingDatesAndTimes;
	}
	
	private Date getClosingDateAndTimeOnCurrentDay(Date currentDateAndTime) {
		SimpleDateFormat dateAndTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		
		Date closingDateAndTimeOnCurrentDay = null;
		try {
			closingDateAndTimeOnCurrentDay = dateAndTimeFormatter.parse(dateFormatter.format(currentDateAndTime) + " " + String.valueOf(CLOSING_HOUR) + ":" +  String.valueOf(CLOSING_MINUTE));
		} catch (ParseException e) {
			logger.error("Date and time parsing failed: " + e.getMessage());
		}
		
		return closingDateAndTimeOnCurrentDay;
	}
	
	private Date getOpeningDateAndTimeOnCurrentDay(Date currentDateAndTime) {
		SimpleDateFormat dateAndTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		
		Date openingDateAndTimeOnCurrentDay = null;
		try {
			openingDateAndTimeOnCurrentDay = dateAndTimeFormatter.parse(dateFormatter.format(currentDateAndTime) + " " + String.valueOf(OPENING_HOUR) + ":" +  String.valueOf(OPENING_MINUTE));
		} catch (ParseException e) {
			logger.error("Date and time parsing failed: " + e.getMessage());
		}
		
		return openingDateAndTimeOnCurrentDay;
	}
	
	private Date getFirstChoosableShippingDateAndTimeOnNextDay(Date currentDateAndTime) {
		SimpleDateFormat dateAndTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDateAndTime);
		Date firstChoosableShippingDateAndTimeOnNextDay = null;
		try {
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			Date oneDayLater = calendar.getTime();
			firstChoosableShippingDateAndTimeOnNextDay = dateAndTimeFormatter.parse(dateFormatter.format(oneDayLater) + " " + String.valueOf(OPENING_HOUR) + ":" +  String.valueOf(OPENING_MINUTE));
		} catch (ParseException e) {
			logger.error("Date and time parsing failed: " + e.getMessage());
		}
		
		return firstChoosableShippingDateAndTimeOnNextDay;
	}
	
	private HashMap<String, List<Date>> initializeChoosableShippingDatesAndTimes(Date choosableShippingDateAndTime) {
		Calendar calendar = Calendar.getInstance();
		HashMap<String, List<Date>> dayStringsAndShippingDates = new HashMap<>();
		List<Date> choosableDayStrings = new ArrayList<>();
		List<Date> choosableShippingDatesAndTimes = new ArrayList<>();
		
		for (int day = 1; day <= NUMBER_OF_SHIPPING_DAYS; day++) {
			choosableDayStrings.add(choosableShippingDateAndTime);
			Date closingDateAndTimeOnCurrentDay = getClosingDateAndTimeOnCurrentDay(choosableShippingDateAndTime);
			
			while (choosableShippingDateAndTime.before(closingDateAndTimeOnCurrentDay)) {
				choosableShippingDatesAndTimes.add(choosableShippingDateAndTime);
				calendar.setTime(choosableShippingDateAndTime);
				calendar.add(Calendar.MINUTE, SHIPPING_MINUTE_INTERVAL);
				choosableShippingDateAndTime = calendar.getTime();
			}
			
			choosableShippingDateAndTime = getFirstChoosableShippingDateAndTimeOnNextDay(choosableShippingDateAndTime);	
		}
		
		dayStringsAndShippingDates.put("choosableDays", choosableDayStrings);
		dayStringsAndShippingDates.put("choosableShippingDates", choosableShippingDatesAndTimes);
		
		return dayStringsAndShippingDates;
	}
	
	public Date convertStringToDateAndTime(String dateAndTimeString) {
		SimpleDateFormat dateAndTimeFormatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
		Date dateAndTime = null;
		try {
			dateAndTime = dateAndTimeFormatter.parse(dateAndTimeString);
		} catch (ParseException e) {
			logger.error("Converting string to date failed: " + e.getMessage());
		}
		return dateAndTime;
	}
	
}
