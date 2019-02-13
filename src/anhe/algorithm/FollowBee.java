package anhe.algorithm;

import anhe.ResultFactory.ResultFactory;
import anhe.container.AlgorithmContainer;
import anhe.entity.Result;
/**
 * 跟随行为的蜜蜂
 * @author 张文浩
 *
 */
public class FollowBee implements Runnable {

	private ResultFactory resultFactory;
	private AlgorithmContainer algorithmContainer;
	private Integer followNum;

	public FollowBee(ResultFactory resultFactory, AlgorithmContainer algorithmContainer, Integer followNum) {
		this.resultFactory = resultFactory;
		this.algorithmContainer = algorithmContainer;
		this.followNum = followNum;
	}

	@Override
	public void run() {
		while (true) {

			if (Thread.currentThread().isInterrupted()) {
				break;
			}

			Result followResult = algorithmContainer.getResult(followNum);
			Result result = resultFactory.getResult(followResult);

			if (result == null) {
				continue;
			} else {
				algorithmContainer.addResult(result);
			}

		}
	}

}
