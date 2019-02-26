package anhe.entity;

/**
 * 坐标实体
 * 
 * @author 张文浩
 *
 */
public class Coordinate {

	private Integer id;// 唯一标识
	private Integer x;// 横坐标
	private Integer y;// 纵坐标
	private String name;// 地点名称
	private Integer flag;// -1,起点；0，中间点；1，终点
	private int CID;// 计算用id

	/**
	 * 
	 * @param id   唯一标识
	 * @param x    横坐标
	 * @param y    纵坐标
	 * @param name 地点名称
	 * @param flag -1,起点；0，中间点；1，终点
	 */
	public Coordinate(Integer id, Integer x, Integer y, String name, Integer flag) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.name = name;
		this.flag = flag;
	}

	public int getCID() {
		return CID;
	}

	public void setCID(int cID) {
		CID = cID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

}
