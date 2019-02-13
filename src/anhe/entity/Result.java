package anhe.entity;

import java.util.List;
/**
 * 结果实体
 * @author 张文浩
 *
 */
public class Result {

	private String id;//路径--地点id表示，从左到右
	private List<Coordinate> coordinateList;//详细路径信息
	private double value;//评价值--总路程
	private long time;//生成结果的时间

	/**
	 * 
	 * @param id 路径--地点id表示，从左到右
	 * @param coordinateList 详细路径信息
	 * @param value 评价值--总路程
	 * @param time 生成结果的时间
	 */
	public Result(String id, List<Coordinate> coordinateList, double value, long time) {
		this.id = id;
		this.coordinateList = coordinateList;
		this.value = value;
		this.time = time;
	}

	@Override
	public int hashCode() {
		return (int) value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Result other = (Result) obj;
		return other.id.equals(this.id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Coordinate> getCoordinateList() {
		return coordinateList;
	}

	public void setCoordinateList(List<Coordinate> coordinateList) {
		this.coordinateList = coordinateList;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Result [id=" + id + ", coordinateList=" + coordinateList + ", value=" + value + ", time=" + time + "]";
	}

}
