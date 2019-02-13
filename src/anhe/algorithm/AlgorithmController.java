package anhe.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import anhe.ResultFactory.ResultFactory;
import anhe.comparator.AlgorithmComparator;
import anhe.container.AlgorithmContainer;
import anhe.entity.Coordinate;
import anhe.entity.Result;

/**
 * 算法主控制
 * 
 * @author 张文浩
 *
 */
public class AlgorithmController {

	/**
	 * 
	 * @param coordinateList      坐标集
	 * @param time                运行时间
	 * @param algorithmComparator 比较器
	 * @param algorithmContainer  容器
	 * @param resultFactory       结果工厂
	 */
	public AlgorithmController(List<Coordinate> coordinateList, long time, AlgorithmComparator algorithmComparator,
			AlgorithmContainer algorithmContainer, ResultFactory resultFactory) {
		this.coordinateList = coordinateList;
		this.time = time;
		this.algorithmComparator = algorithmComparator;
		this.algorithmContainer = algorithmContainer;
		this.resultFactory = resultFactory;

		algorithmContainer.setParameter(algorithmComparator);
		resultFactory.setParameter(coordinateList, algorithmContainer);
	}

	// 输入参数
	private List<Coordinate> coordinateList;// 坐标集
	private long time;// 运行时间

	// 设置的比较器、容器、结果工厂
	private AlgorithmComparator algorithmComparator;// 比较器
	private AlgorithmContainer algorithmContainer;// 容器
	private ResultFactory resultFactory;// 结果工厂

	// 线程集
	private List<Thread> threadList = new ArrayList<>();

	/**
	 * 运行算法
	 * 
	 * @param level 结果输出等级：0-不输出额外信息；1-输出获得时间和算出的结果数量；2-将所有计算得到的结果输出
	 * @return 获得的最短路径
	 */
	public Result run(int level) {

		//// 算法开始
		long startTime = System.currentTimeMillis();
		// 根据坐标集里坐标的个数进行FreeBee、FollowBee的数量分配
		init(coordinateList);
		// 开启子线程
		for (int i = 0; i < FreeBeeNum; i++) {
			Thread thread = new Thread(new FreeBee(resultFactory, algorithmContainer));
			thread.start();
			threadList.add(thread);
		}
		for (int i = 0; i < FollowBeeNum; i++) {
			Thread thread = new Thread(new FollowBee(resultFactory, algorithmContainer, getFollowNum()));
			thread.start();
			threadList.add(thread);
		}
		/*
		 * //旧方案 // 开启计时 long now = System.currentTimeMillis(); Long delay = 5 * 60 *
		 * 1000 - (now - startTime); //定时关闭算法 new Timer().schedule(new TimerTask() {
		 * 
		 * @Override public void run() { for (Thread thread : threadList) {
		 * thread.interrupt(); } }
		 * 
		 * }, delay); //主线程在子线程停止之前等待 for (Thread thread : threadList) { try {
		 * thread.join(); } catch (InterruptedException e) { e.printStackTrace(); } }
		 */
		// 新方案
		// 判断主体
		long now = 0;
		Result tempResult = null;
		while (true) {
			// 时间到停止
			now = System.currentTimeMillis();
			if (now - startTime >= time) {
				break;
			}

			// 验证最佳解是否是最优解
			tempResult = algorithmContainer.getResult(0);
			try {
				// 获得可能是最优解的等待时间
				Thread.sleep(resultFactory.getProbablyBestResultTime());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (algorithmContainer.getResult(0).equals(tempResult)) {
				if (resultFactory.isProbablyBestResult(startTime)) {
					if (algorithmContainer.getResult(0).equals(tempResult)) {
						break;
					}
				}
			}
		}
		// 停止程序
		stopThreadList();
		//// 取出最佳答案

		// 输出结果
		if (level > 0) {
			Set<Result> container = algorithmContainer.getContainer();

			System.out.println("使用时间：" + (System.currentTimeMillis() - startTime) + "ms");
			System.out.println("计算的结果数量/总结果数量：" + container.size() + "/" + totalResultNum);
			if (level > 1) {
				for (Result result : container) {
					System.out.println(result);
				}
			}

		}

		return algorithmContainer.getResult(0);
	}

	private int coordinateNum;// 坐标数量
	private int totalResultNum = 1;// 总共的结果数量
	private int FreeBeeNum;// FreeBee数量
	private int FollowBeeNum;// FollowBee数量
	private int[] FollowNumArr;// 分配给FollowBee的跟随目标

	// 根据坐标集里坐标的个数进行FreeBee、FollowBee的数量分配，同时分配好FollowNum
	private void init(List<Coordinate> coordinateList) {
		// coordinateNum
		coordinateNum = coordinateList.size();
		// totalResultNum
		for (int i = 2; i <= coordinateNum - 2; i++) {
			totalResultNum *= i;
		}
		// FreeBeeNum
		FreeBeeNum = (int) (totalResultNum * 0.05);
		if (FreeBeeNum < 1) {
			FreeBeeNum = 1;
		} else if (FreeBeeNum > 20) {
			FreeBeeNum = 20;
		}
		// FollowBeeNum
		FollowBeeNum = FreeBeeNum * 3;
		// FollowNumArr
		FollowNumArr = new int[FollowBeeNum];
		int MaxFollowNum = (int) (FreeBeeNum * 0.5);
		if (MaxFollowNum < 1) {
			MaxFollowNum = 1;
		}
		double a = FollowBeeNum * MaxFollowNum - (MaxFollowNum + 1) * MaxFollowNum / 2.0;
		int tempNum = 0;
		for (int i = 1; i <= MaxFollowNum; i++) {
			int FollowNum_num = (int) ((FollowBeeNum - i) / a * FollowBeeNum);
			for (int j = 0; j < FollowNum_num; j++) {
				FollowNumArr[tempNum++] = i;
			}
		}
		for (int i = tempNum; i < FollowNumArr.length - 1; i++) {
			FollowNumArr[tempNum++] = 1;
		}
		/*
		 * //方案二 int FollowNum_1 = FollowBeeNum/2; if(FollowNum_1 < 1) { FollowNum_1 =
		 * 1; } for (int i = 0; i < FollowNum_1; i++) { FollowNumArr[i] = 1; } int
		 * FollowNum = 2; for (int i = FollowNum_1; i < FollowNumArr.length; i++) {
		 * FollowNumArr[i] = FollowNum++; if(FollowNum > MaxFollowNum) { FollowNum = 2;
		 * } }
		 */
	}

	private int getFollowNum_num = 0;

	// 获得FollowNum
	private Integer getFollowNum() {
		return FollowNumArr[getFollowNum_num++];
	}

	// 停止程序
	private void stopThreadList() {
		for (Thread thread : threadList) {
			thread.interrupt();
		}
		// 主线程在子线程停止之前等待
		for (Thread thread : threadList) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
