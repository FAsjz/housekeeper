package sjz.feicui.contacts.entity;
// ����չʾ��TelActivity��ListViewde������ʵ����
public class ClassListInfo {
	public String name;
	public int _id;
	public String number;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public ClassListInfo(String name) {
		super();
		this.name = name;
	}

	public ClassListInfo(String name, int _id, String number) {
		super();
		this.name = name;
		this._id = _id;
		this.number = number;
	}

}
