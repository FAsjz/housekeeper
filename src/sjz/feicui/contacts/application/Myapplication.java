package sjz.feicui.contacts.application;

import java.util.ArrayList;

import sjz.feicui.contacts.entity.FileInfo;
import android.app.Application;
/**
 * 所有界面共享内容（单例）
 * @author Administrator
 *
 */
public class Myapplication extends Application {
	private ArrayList<FileInfo> allFiles;
	private ArrayList<FileInfo> textFiles;
	private ArrayList<FileInfo> videoFiles;
	private ArrayList<FileInfo> audioFiles;
	private ArrayList<FileInfo> imageFiles;
	private ArrayList<FileInfo> zipFiles;
	private ArrayList<FileInfo> apkFiles;
	public ArrayList<FileInfo> getAllFiles() {
		return allFiles;
	}
	public void setAllFiles(ArrayList<FileInfo> allFiles) {
		this.allFiles = allFiles;
	}
	public ArrayList<FileInfo> getTextFiles() {
		return textFiles;
	}
	public void setTextFiles(ArrayList<FileInfo> textFiles) {
		this.textFiles = textFiles;
	}
	public ArrayList<FileInfo> getVideoFiles() {
		return videoFiles;
	}
	public void setVideoFiles(ArrayList<FileInfo> videoFiles) {
		this.videoFiles = videoFiles;
	}
	public ArrayList<FileInfo> getAudioFiles() {
		return audioFiles;
	}
	public void setAudioFiles(ArrayList<FileInfo> audioFiles) {
		this.audioFiles = audioFiles;
	}
	public ArrayList<FileInfo> getImageFiles() {
		return imageFiles;
	}
	public void setImageFiles(ArrayList<FileInfo> imageFiles) {
		this.imageFiles = imageFiles;
	}
	public ArrayList<FileInfo> getZipFiles() {
		return zipFiles;
	}
	public void setZipFiles(ArrayList<FileInfo> zipFiles) {
		this.zipFiles = zipFiles;
	}
	public ArrayList<FileInfo> getApkFiles() {
		return apkFiles;
	}
	public void setApkFiles(ArrayList<FileInfo> apkFiles) {
		this.apkFiles = apkFiles;
	}
	
	
	
}
