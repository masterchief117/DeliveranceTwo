package service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import model.YearAndDayOfYear;

public class YearAndDayOfYearService {

	private static YearAndDayOfYearService yearAndDayOfYearService;

	private YearAndDayOfYearService() {
	}

	public static YearAndDayOfYearService getInstance() {
		if (yearAndDayOfYearService == null) {
			yearAndDayOfYearService = new YearAndDayOfYearService();
		}
		return yearAndDayOfYearService;
	}

	public double createMedian(List<Integer> differenceBetweenOrderDates) {
		int median = 0;
		Object[] orderedArray = differenceBetweenOrderDates.toArray();
		int[] intArray = new int[orderedArray.length];
		for (int index = 0; index < orderedArray.length; index++) {
			intArray[index] = (Integer) orderedArray[index];
		}
		Arrays.sort(intArray);
		if (intArray.length % 2 == 0 && intArray.length != 0) {
			int num = intArray.length / 2;
			median = (intArray[num] + intArray[num - 1]) / 2;
		} else if (intArray.length != 1 && intArray.length != 0) {
			int num = intArray.length / 2;
			median = intArray[num];
		}
		return median;
	}

	public double createMean(List<Integer> differenceBetweenOrderDates) {
		int total = 0;
		for (int value : differenceBetweenOrderDates) {
			total += value;
		}
		return differenceBetweenOrderDates.size() == 0 ? 0 : total
				/ differenceBetweenOrderDates.size();
	}

	public void sortYearAndDay(List<YearAndDayOfYear> yearAndDayOfYear) {
		Collections.sort(yearAndDayOfYear, new Comparator<YearAndDayOfYear>() {

			@Override
			public int compare(YearAndDayOfYear first, YearAndDayOfYear second) {
				if (first.getYear() == second.getYear())
					return checkDay(first, second);
				return first.getYear() < second.getYear() ? -1 : 1;
			}

			private int checkDay(YearAndDayOfYear first, YearAndDayOfYear second) {
				if (first.getDay() == second.getDay())
					return 0;
				return first.getDay() < second.getDay() ? -1 : 1;
			}
		});
	}

}
