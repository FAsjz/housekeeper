package sjz.feicui.contacts.entity;

import android.graphics.drawable.Drawable;








public class PhoneInfo {
	private String title;
	public PhoneInfo(String title, String text, Drawable image) {
		super();
		this.title = title;
		this.text = text;
		this.image = image;
	}
	private String text;
	private Drawable image;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Drawable getImage() {
		return image;
	}
	public void setImage(Drawable image) {
		this.image = image;
	}

	
	
	

	
}
