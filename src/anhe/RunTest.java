package anhe;

import java.util.ArrayList;

import anhe.ResultFactory.impl.ResultFactoryImpl;
import anhe.comparator.impl.AlgorithmComparatorImpl;
import anhe.container.impl.AlgorithmContainerImpl;
import anhe.entity.Coordinate;

public class RunTest {

	public static void main(String[] args) {

		ArrayList<Coordinate> coordinateList = new ArrayList<>();
		coordinateList.add(new Coordinate(1, 12, 56, "起点", -1));
		coordinateList.add(new Coordinate(2, 134, 356, "地点1", 0));
		coordinateList.add(new Coordinate(3, 1312, 34556, "地点2", 0));
		coordinateList.add(new Coordinate(4, 112, 3556, "地点3", 0));
		coordinateList.add(new Coordinate(5, 1332, 5456, "地点4", 0));
		coordinateList.add(new Coordinate(6, 1562, 356, "地点5", 0));
		coordinateList.add(new Coordinate(7, 311, 134, "地点6", 0));
		coordinateList.add(new Coordinate(8, 32, 231, "地点7", 0));
		coordinateList.add(new Coordinate(9, 2343, 14, "地点8", 0));
		coordinateList.add(new Coordinate(10, 1343, 321, "地点9", 0));
		coordinateList.add(new Coordinate(11, 43, 431, "地点10", 0));
		coordinateList.add(new Coordinate(12, 3, 41, "地点11", 0));
		coordinateList.add(new Coordinate(13, 122, 256, "终点", 1));

		BeeColonyAlgorithm beeColonyAlgorithm = new BeeColonyAlgorithm(coordinateList, 1 * 60 * 1000,
				new AlgorithmComparatorImpl(), new AlgorithmContainerImpl(), new ResultFactoryImpl());

		System.out.println(beeColonyAlgorithm.run(1));
	}

}
