package anhe.container;

import java.util.Set;

import anhe.comparator.AlgorithmComparator;
import anhe.entity.Result;
/**
 * 容器
 * @author 张文浩
 *
 */
public interface AlgorithmContainer {

	/**
	 * 将结果添加入容器
	 * @param result
	 */
	void addResult(Result result);

	/**
	 * 获得第followNum个结果
	 * @param followNum
	 * @return
	 */
	Result getResult(int followNum);

	/**
	 * 获得容器里元素的个数
	 * @return
	 */
	int size();

	/**
	 * 获得内置容器
	 * @return
	 */
	Set<Result> getContainer();
	
	/**
	 * 设置变量
	 * @param algorithmComparator 比较器
	 */
	void setParameter(AlgorithmComparator algorithmComparator);

}
