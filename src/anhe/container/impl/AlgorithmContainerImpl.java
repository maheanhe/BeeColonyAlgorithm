package anhe.container.impl;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import anhe.comparator.AlgorithmComparator;
import anhe.container.AlgorithmContainer;
import anhe.entity.Result;

public class AlgorithmContainerImpl implements AlgorithmContainer {

	private ConcurrentSkipListSet<Result> concurrentSkipListSet;
	private volatile int flag = 0;
	private Object lock = new Object();

	public AlgorithmContainerImpl(AlgorithmComparator algorithmComparator) {
		this.concurrentSkipListSet = new ConcurrentSkipListSet<Result>(algorithmComparator);
	}

	public AlgorithmContainerImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setParameter(AlgorithmComparator algorithmComparator) {
		this.concurrentSkipListSet = new ConcurrentSkipListSet<Result>(algorithmComparator);
	}
	
	@Override
	public void addResult(Result result) {
		concurrentSkipListSet.add(result);

		if(flag > 0) { 
			synchronized (lock) {
				if(flag > 0) {
					flag = 0;
					lock.notifyAll();
				}
			}
		}
	}

	@Override
	public Result getResult(int followNum) {
		Result result = null;
		int size = 0;
		while (result == null) {
			size = concurrentSkipListSet.size();
			if (followNum >= size) {
				
				synchronized (lock) {
					size = concurrentSkipListSet.size();
					if (followNum >= size) {
						try {
							flag++;
							lock.wait();
						} catch (InterruptedException e) {
							flag--;
							e.printStackTrace();
						}
					}
				}
				
			} else {
				Iterator<Result> iterator = concurrentSkipListSet.iterator();
				int i = 0;
				while(i < followNum) {
					iterator.next();
					i++;
				}
				result = iterator.next();
			}
		}
		return result;
	}

	@Override
	public int size() {
		return concurrentSkipListSet.size();
	}

	@Override
	public Set<Result> getContainer() {
		return concurrentSkipListSet;
	}

}
