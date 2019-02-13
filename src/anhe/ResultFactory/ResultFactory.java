package anhe.ResultFactory;

import java.util.List;

import anhe.container.AlgorithmContainer;
import anhe.entity.Coordinate;
import anhe.entity.Result;
/**
 * 结果工厂
 * @author 张文浩
 *
 */
public interface ResultFactory {
	/**
	 * 随机获得一个容器中不存在的结果
	 * @return
	 */
	Result getResult();

	/**
	 * 随机获得一个容器中不存在的,且在followResult附近的结果
	 * @param followResult
	 * @return
	 */
	Result getResult(Result followResult);

	/**
	 * 获得评估最佳结果的时间
	 * @return
	 */
	Long getProbablyBestResultTime();

	/**
	 * 评估现最佳结果是否应该当做最终解
	 * @param startTime
	 * @return
	 */
	boolean isProbablyBestResult(long startTime);
	
	/**
	 * 设置变量
	 * @param coordinateList 坐标集
	 * @param algorithmContainer 容器
	 */
	void setParameter(List<Coordinate> coordinateList, AlgorithmContainer algorithmContainer);

}
