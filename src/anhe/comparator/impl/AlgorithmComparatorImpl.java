package anhe.comparator.impl;

import anhe.comparator.AlgorithmComparator;
import anhe.entity.Result;

public class AlgorithmComparatorImpl implements AlgorithmComparator {

	@Override
	public int compare(Result o1, Result o2) {
		double comValue = o1.getValue() - o2.getValue();
		if (comValue > 0) {
			return 1;
		} else if (comValue < 0) {
			return -1;
		} else {
			return o1.getId().compareTo(o2.getId());
		}
	}

}
