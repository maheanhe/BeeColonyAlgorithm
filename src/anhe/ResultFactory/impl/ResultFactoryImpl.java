package anhe.ResultFactory.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentSkipListSet;

import anhe.ResultFactory.ResultFactory;
import anhe.container.AlgorithmContainer;
import anhe.entity.Coordinate;
import anhe.entity.Result;

public class ResultFactoryImpl implements ResultFactory {

	private List<Coordinate> coordinateList;
	private AlgorithmContainer algorithmContainer;
	private LinkedList<Integer> idList = new LinkedList<>();
	private ConcurrentSkipListSet<String> resultSet_idList = new ConcurrentSkipListSet<String>();
	private Object lock = new Object();

	public ResultFactoryImpl(List<Coordinate> coordinateList, AlgorithmContainer algorithmContainer) {
		setParameter(coordinateList,algorithmContainer);
	}

	public ResultFactoryImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setParameter(List<Coordinate> coordinateList, AlgorithmContainer algorithmContainer) {
		this.coordinateList = coordinateList;
		this.algorithmContainer = algorithmContainer;

		int flag = 0;
		int id = 0;
		int firstId = 0;
		int lastId = 0;
		for (Coordinate coordinate : this.coordinateList) {
			flag = coordinate.getFlag();
			id = coordinate.getId();
			if (flag == 0) {
				idList.add(id);
			} else if (flag == -1) {
				firstId = id;
			} else if (flag == 1) {
				lastId = id;
			}
		}
		idList.addFirst(firstId);
		idList.addLast(lastId);
	}
	
	/**
	 *  无参生成List<Integer>
	 * @return
	 */
	protected List<Integer> generatResult() {
		LinkedList<Integer> cloneList = new LinkedList<>(idList);

		LinkedList<Integer> result_idList = new LinkedList<Integer>();

		Random random = new Random();

		int firstId = cloneList.removeFirst();
		int lastId = cloneList.removeLast();
		int size = cloneList.size();
		int randomNum = 0;
		while (size > 0) {
			randomNum = random.nextInt(size);
			result_idList.add(cloneList.remove(randomNum));
			size--;
		}
		result_idList.addFirst(firstId);
		result_idList.addLast(lastId);
		return result_idList;
	}

	/**
	 *  有参生成List<Integer>
	 * @param f_result_idList
	 * @return
	 */
	protected List<Integer> generatResult(List<Integer> f_result_idList) {
		LinkedList<Integer> cloneList = new LinkedList<>(f_result_idList);

		Random random = new Random();

		int maxNum = cloneList.size() - 1;
		int n1 = 0;
		int n2 = 0;
		while (n1 == 0) {
			n1 = random.nextInt(maxNum);
		}
		while (n2 == 0 || n2 == n1) {
			n2 = random.nextInt(maxNum);
		}
		Integer a1 = cloneList.get(n1);
		Integer a2 = cloneList.get(n2);
		cloneList.set(n1, a2);
		cloneList.set(n2, a1);

		return cloneList;
	}

	/**
	 *  计算Result的value
	 * @param result_coordinateList
	 * @return
	 */
	protected double computeValue(List<Coordinate> result_coordinateList) {
		Coordinate c1 = result_coordinateList.get(0);
		Coordinate c2 = result_coordinateList.get(1);
		double value = computeCoordinateToCoordinate(c1, c2);
		for (int i = 2; i < result_coordinateList.size(); i++) {
			c1 = c2;
			c2 = result_coordinateList.get(i);
			value += computeCoordinateToCoordinate(c1, c2);
		}
		return value;
	}

	/**
	 *  计算两坐标之间的距离
	 * @param c1
	 * @param c2
	 * @return
	 */
	protected double computeCoordinateToCoordinate(Coordinate c1, Coordinate c2) {
		return Math.pow(Math.pow(c1.getX() - c2.getX(), 2) + Math.pow(c1.getY() - c2.getY(), 2), 0.5);
	}

	/**
	 *  判断是否包含List<Integer>
	 * @param result_idList
	 */
	protected void isContains(List<Integer> result_idList) {
		String resultStr_idList = result_idList.toString();
		if (resultSet_idList.contains(resultStr_idList)) {
			result_idList = null;
		} else {
			synchronized (lock) {
				if (resultSet_idList.contains(resultStr_idList)) {
					result_idList = null;
				} else {
					resultSet_idList.add(resultStr_idList);
				}
			}
		}
	}

	/**
	 * 生成List<Coordinate>
	 * @param result_idList
	 * @return
	 */
	protected List<Coordinate> generatCoordinateList(List<Integer> result_idList) {
		ArrayList<Coordinate> result_coordinateList = new ArrayList<>();
		for (Integer id : result_idList) {
			for (Coordinate coordinate : coordinateList) {
				if (coordinate.getId() == id) {
					result_coordinateList.add(coordinate);
				}
			}
		}
		return result_coordinateList;
	}

	@Override
	public Result getResult() {
		List<Integer> result_idList = null;
		while (result_idList == null) {
			result_idList = generatResult();
			isContains(result_idList);

			if (Thread.currentThread().isInterrupted()) {
				break;
			}

		}

		if (result_idList == null) {
			return null;
		}

		List<Coordinate> result_coordinateList = generatCoordinateList(result_idList);

		return new Result(result_idList.toString(), result_coordinateList, computeValue(result_coordinateList),
				System.currentTimeMillis());
	}

	@Override
	public Result getResult(Result followResult) {
		List<Coordinate> f_coordinateList = followResult.getCoordinateList();
		LinkedList<Integer> f_result_idList = new LinkedList<>();
		for (Coordinate coordinate : f_coordinateList) {
			f_result_idList.add(coordinate.getId());
		}

		int times = 0;
		List<Integer> result_idList = null;
		while (result_idList == null) {
			result_idList = generatResult(f_result_idList);
			isContains(result_idList);

			times++;
			if (times >= 20) {
				break;
			}
		}

		if (result_idList == null) {
			return null;
		}

		List<Coordinate> result_coordinateList = generatCoordinateList(result_idList);

		return new Result(result_idList.toString(), result_coordinateList, computeValue(result_coordinateList),
				System.currentTimeMillis());
	}

	@Override
	public Long getProbablyBestResultTime() {
		int size1 = algorithmContainer.size();
		long time1 = System.currentTimeMillis();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int size2 = algorithmContainer.size();
		long time2 = System.currentTimeMillis();
		double v = (size2 - size1) / (time2 - time1);
		long vTime = (long) (v * 100);
		if (vTime == 0) {
			vTime = 1000;
		}
		return vTime;
	}

	@Override
	public boolean isProbablyBestResult(long startTime) {
		Result result1 = algorithmContainer.getResult(0);
		Result result2 = algorithmContainer.getResult(1);
		long t1 = result1.getTime();
		long t2 = result2.getTime();
		double v1 = result1.getValue();
		double v2 = result2.getValue();

		double Jud1 = ((v2 - v1) / (t1 - t2)) / (v1 / (t1 - startTime));
		if (Jud1 < 0.005) {
			return true;
		}

		long nowTime = System.currentTimeMillis();
		double Jud2 = (nowTime - t1) / (nowTime - startTime);
		if (Jud2 > 0.3) {
			return true;
		}

		return false;
	}

}
