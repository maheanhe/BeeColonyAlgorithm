package anhe;

import java.util.List;

import anhe.ResultFactory.ResultFactory;
import anhe.algorithm.AlgorithmController;
import anhe.comparator.AlgorithmComparator;
import anhe.container.AlgorithmContainer;
import anhe.entity.Coordinate;
import anhe.entity.Result;
/**
 * 离散人工蜂群算法：用于求解最短路径问题
 * @author 张文浩
 *
 */
public class BeeColonyAlgorithm {

	private AlgorithmController algorithmController;

	/**
	 * 
	 * @param coordinateList      坐标集
	 * @param time                运行时间
	 * @param algorithmComparator 比较器
	 * @param algorithmContainer  容器
	 * @param resultFactory       结果工厂
	 */
	BeeColonyAlgorithm(List<Coordinate> coordinateList, long time, AlgorithmComparator algorithmComparator,
			AlgorithmContainer algorithmContainer, ResultFactory resultFactory) {
		algorithmController = new AlgorithmController(coordinateList, time, algorithmComparator, algorithmContainer,
				resultFactory);
	}

	/**
	 * 运行算法
	 * @param level 结果输出等级：0-不输出额外信息；1-输出获得时间和算出的结果数量；2-将所有计算得到的结果输出
	 * @return 获得的最短路径
	 */
	public Result run(int level) {
		return algorithmController.run(level);
	}

}
