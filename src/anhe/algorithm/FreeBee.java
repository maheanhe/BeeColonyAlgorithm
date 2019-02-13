package anhe.algorithm;

import anhe.ResultFactory.ResultFactory;
import anhe.container.AlgorithmContainer;
import anhe.entity.Result;
/**
 * 自由活动的蜜蜂
 * @author 张文浩
 *
 */
public class FreeBee implements Runnable {

	private ResultFactory resultFactory;
	private AlgorithmContainer algorithmContainer;

	public FreeBee(ResultFactory resultFactory, AlgorithmContainer algorithmContainer) {
		this.resultFactory = resultFactory;
		this.algorithmContainer = algorithmContainer;
	}

	@Override
	public void run() {
		while (true) {

			if (Thread.currentThread().isInterrupted()) {
				break;
			}

			Result result = resultFactory.getResult();

			if (result == null) {
				continue;
			} else {
				algorithmContainer.addResult(result);
			}

		}
	}

}
