package sjz.feicui.contacts.biz;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import sjz.feicui.contacts.application.Myapplication;
import sjz.feicui.contacts.base.utils.FileTypeUtil;
import sjz.feicui.contacts.base.utils.LogUtil;
import sjz.feicui.contacts.entity.FileInfo;
import android.app.Activity;
import android.content.Context;
import android.text.format.Formatter;

public class FileManager {
	private static FileManager FileManager;

	private FileManager() {
	}

	public static FileManager getInstance() {
		if (FileManager == null) {
			synchronized (FileManager.class) {
				if (FileManager == null) {
					FileManager = new FileManager();
				}
			}
		}
		return FileManager;
	}

	private ArrayList<FileInfo> allFile = new ArrayList<FileInfo>();
	private ArrayList<FileInfo> textFile = new ArrayList<FileInfo>();
	private ArrayList<FileInfo> videoFile = new ArrayList<FileInfo>();
	private ArrayList<FileInfo> audioFile = new ArrayList<FileInfo>();
	private ArrayList<FileInfo> imageFile = new ArrayList<FileInfo>();
	private ArrayList<FileInfo> zipFile = new ArrayList<FileInfo>();
	private ArrayList<FileInfo> apkFile = new ArrayList<FileInfo>();
	private String allSize;
	private String textSize;
	private String videoSize;
	private String audioSize;
	private String imageSize;
	private String zipSize;
	private String apkSize;
	long all = 0;
	long text = 0;
	long video = 0;
	long audio = 0;
	long image = 0;
	long zip = 0;
	long apk = 0;

	public void destroyDate() {
		all = 0;
		text = 0;
		video = 0;
		audio = 0;
		image = 0;
		zip = 0;
		apk = 0;
		allSize = null;
		textSize = null;
		videoSize = null;
		audioSize = null;
		imageSize = null;
		zipSize = null;
		apkSize = null;
		allFile.clear();
		textFile.clear();
		videoFile.clear();
		audioFile.clear();
		imageFile.clear();
		zipFile.clear();
		apkFile.clear();
	}

	

	public String getAllSize() {
		return allSize;
	}

	public String getTextSize() {
		return textSize;
	}

	public String getVideoSize() {
		return videoSize;
	}

	public String getAudioSize() {
		return audioSize;
	}

	public String getImageSize() {
		return imageSize;
	}

	public String getZipSize() {
		return zipSize;
	}

	public String getApkSize() {
		return apkSize;
	}

	private MyOnClickListener myListener;
	public interface MyOnClickListener {
		void searchEnd();
	}

	public MyOnClickListener getMyListener() {
		return myListener;
	}
	
	public void setOnClickListener(MyOnClickListener on) {
		this.myListener = on;
	}
	

	public void searchSDFile(File file, Context context) {

		File[] sdFiles = file.listFiles();
		// LogUtil.d("sdFiles", sdFiles + "");
		if (sdFiles == null) {
			return;
		}
		// for (File file2 : sdFiles) {
		for (int x = 0; x < sdFiles.length; x++) {
			File file2 = sdFiles[x];
			// LogUtil.d("file2", file2 + "");
			if (file2.isDirectory()) {
				// LogUtil.d("yes", file2 + "");
				searchSDFile(file2, context);
			} else {
				// LogUtil.d("else", file2 + "");
				// 获取文件大小
				long length = file2.length();
				if (length <= 0) {
					continue;
				}
				all += length;
				String size = Formatter.formatFileSize(context, length);
				// 获取文件名称
				String name = file2.getName();
				// 获取文件后缀名
				int index = name.lastIndexOf(".");
				String end = name.substring(index + 1, name.length());
				// 根据后缀名判断文件类型
				String[] iconAndType = FileTypeUtil.getFileType(end);

				// 获取文件最后修改日期
				String lastTime = castTime(file2.lastModified());
				FileInfo fileInfo = new FileInfo();
				fileInfo.lastTime = lastTime;
				fileInfo.name = name;
				fileInfo.size = size;
				fileInfo.iconName = iconAndType[0];
				fileInfo.type = iconAndType[1];
				fileInfo.path = file2.getPath();
				allFile.add(fileInfo);
				if (iconAndType[1].equals(FileTypeUtil.TYPE_TXT)) {
					text += length;
					textFile.add(fileInfo);
				} else if (iconAndType[1].equals(FileTypeUtil.TYPE_VIDEO)) {
					video += length;
					videoFile.add(fileInfo);
				} else if (iconAndType[1].equals(FileTypeUtil.TYPE_AUDIO)) {
					audio += length;
					audioFile.add(fileInfo);
				} else if (iconAndType[1].equals(FileTypeUtil.TYPE_IMAGE)) {
					image += length;
					imageFile.add(fileInfo);
				} else if (iconAndType[1].equals(FileTypeUtil.TYPE_ZIP)) {
					zip += length;
					zipFile.add(fileInfo);
				} else if (iconAndType[1].equals(FileTypeUtil.TYPE_APK)) {
					apk += length;
					apkFile.add(fileInfo);
				}
			}
		}
		this.allSize = Formatter.formatFileSize(context, all);
		this.textSize = Formatter.formatFileSize(context, text);
		this.videoSize = Formatter.formatFileSize(context, video);
		this.audioSize = Formatter.formatFileSize(context, audio);
		this.imageSize = Formatter.formatFileSize(context, image);
		this.zipSize = Formatter.formatFileSize(context, zip);
		this.apkSize = Formatter.formatFileSize(context, apk);

		// 将数据保存到application
		Activity act = (Activity) context;
		Myapplication ma = (Myapplication) act.getApplication();
		ma.setAllFiles(allFile);
		ma.setTextFiles(textFile);
		ma.setVideoFiles(videoFile);
		ma.setAudioFiles(audioFile);
		ma.setImageFiles(imageFile);
		ma.setZipFiles(zipFile);
		ma.setApkFiles(apkFile);

		myListener.searchEnd();
	}

	public ArrayList<FileInfo> getAllFile() {
		return allFile;
	}

	public void setAllFile(ArrayList<FileInfo> allFile) {
		this.allFile = allFile;
	}

	public ArrayList<FileInfo> getTextFile() {
		return textFile;
	}

	public void setTextFile(ArrayList<FileInfo> textFile) {
		this.textFile = textFile;
	}

	public ArrayList<FileInfo> getVideoFile() {
		return videoFile;
	}

	public void setVideoFile(ArrayList<FileInfo> videoFile) {
		this.videoFile = videoFile;
	}

	public ArrayList<FileInfo> getAudioFile() {
		return audioFile;
	}

	public void setAudioFile(ArrayList<FileInfo> audioFile) {
		this.audioFile = audioFile;
	}

	public ArrayList<FileInfo> getImageFile() {
		return imageFile;
	}

	public void setImageFile(ArrayList<FileInfo> imageFile) {
		this.imageFile = imageFile;
	}

	public ArrayList<FileInfo> getZipFile() {
		return zipFile;
	}

	public void setZipFile(ArrayList<FileInfo> zipFile) {
		this.zipFile = zipFile;
	}

	public ArrayList<FileInfo> getApkFile() {
		return apkFile;
	}

	public void setApkFile(ArrayList<FileInfo> apkFile) {
		this.apkFile = apkFile;
	}

	public long getAll() {
		return all;
	}

	public void setAll(long all) {
		this.all = all;
	}

	public long getText() {
		return text;
	}

	public void setText(long text) {
		this.text = text;
	}

	public long getVideo() {
		return video;
	}

	public void setVideo(long video) {
		this.video = video;
	}

	public long getAudio() {
		return audio;
	}

	public void setAudio(long audio) {
		this.audio = audio;
	}

	public long getImage() {
		return image;
	}

	public void setImage(long image) {
		this.image = image;
	}

	public long getZip() {
		return zip;
	}

	public void setZip(long zip) {
		this.zip = zip;
	}

	public long getApk() {
		return apk;
	}

	public void setApk(long apk) {
		this.apk = apk;
	}

	public void setMyListener(MyOnClickListener myListener) {
		this.myListener = myListener;
	}

	public void setAllSize(String allSize) {
		this.allSize = allSize;
	}

	public void setTextSize(String textSize) {
		this.textSize = textSize;
	}

	public void setVideoSize(String videoSize) {
		this.videoSize = videoSize;
	}

	public void setAudioSize(String audioSize) {
		this.audioSize = audioSize;
	}

	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}

	public void setZipSize(String zipSize) {
		this.zipSize = zipSize;
	}

	public void setApkSize(String apkSize) {
		this.apkSize = apkSize;
	}

	public String castTime(long time) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		Date d = c.getTime();
		SimpleDateFormat adf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		String s = adf.format(d);
		return s;
	}

}
