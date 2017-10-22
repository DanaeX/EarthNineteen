package com.danaex.earthNineteen.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

public class FileFinder {

	private Path mainPath;
	private ArrayList<Path> imgList;
	private ArrayList<Path> vidList;
	public boolean noFile;

	public FileFinder(String path) {
		noFile = false;
		init(path);
	}

	private void init(String path) {
		mainPath = Paths.get(path.toString() + "\\AppData\\Roaming\\EarthNineteen");
		checkFolders(mainPath);
		mainPath = Paths.get(path.toString() + "\\AppData\\Roaming\\EarthNineteen\\img");
		checkFolders(mainPath);
		checkImages(mainPath);
	}

	private void checkImages(Path imgFolder) {
		imgList = new ArrayList<Path>();
		vidList = new ArrayList<Path>();
		try {
			for (Path p : Files.newDirectoryStream(imgFolder)) {
				if (p.getFileName().toString().endsWith(".jpg") || p.getFileName().toString().endsWith(".png")
						|| p.getFileName().toString().endsWith(".jpeg") || p.getFileName().toString().endsWith(".JPG")
						|| p.getFileName().toString().endsWith(".PNG")
						|| p.getFileName().toString().endsWith(".JPEG")) {
					imgList.add(p);
				}
				if (p.getFileName().toString().endsWith(".mov") || p.getFileName().toString().endsWith(".mp4")
						|| p.getFileName().toString().endsWith(".avi") || p.getFileName().toString().endsWith(".wmv")
						|| p.getFileName().toString().endsWith(".MOV") || p.getFileName().toString().endsWith(".MP4")
						|| p.getFileName().toString().endsWith(".AVI") || p.getFileName().toString().endsWith(".WMV")) {
					vidList.add(p);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		if (imgList.size() == 0) {
			System.out.print("Nothing found in img folder.");
			noFile = true;
		} else {
			System.out.print((new StringBuilder(String.valueOf(imgList.size()))).append(" image(s) and ").append(vidList.size())
					.append(" video(s) were found in img folder.\n").toString());
		}
	}

	private void checkFolders(Path path) {
		if (!Files.exists(path, new LinkOption[0])) {
			System.out.println("Creating img folder and subfolders.");
			try {
				Files.createDirectory(path, new FileAttribute[0]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void moveFiles() {
		for (Path p : imgList) {
			try {
				Files.move(p, mainPath, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void update() {
		checkFolders(mainPath);
		checkImages(mainPath);
	}

	public ArrayList<Path> getImagesList() {
		update();
		return imgList;
	}

	public ArrayList<Path> getVideosList() {
		update();
		return vidList;
	}

	public boolean promptFiles() {
		int option = JOptionPane.showConfirmDialog(null,
				"You haven't imported pics or videos yet. Do you want to import some ?", "EarthNineteen", 1, 2);
		if (option != JOptionPane.YES_OPTION) {
			return true;
		} else {
			return !addFiles();
		}
	}

	public boolean addFiles() {

		FileFilter mf1 = new FFilter(new String[] { "png", "PNG" }, "PNG (*.png, *.PNG)");
		FileFilter mf2 = new FFilter(new String[] { "jpeg", "JPEG" }, "JPEG (*.jpeg, *.JPEG)");
		FileFilter mf3 = new FFilter(new String[] { "JPG", "jpg" }, "JPG (*.jpg, *.JPG)");
		FileFilter mf4 = new FFilter(new String[] { "mov", "MOV" }, "MOV (*.mov, *.MOV)");
		FileFilter mf5 = new FFilter(new String[] { "mp4", "MP4" }, "MP4 (*.mp4, *.MP4)");
		FileFilter mf6 = new FFilter(new String[] { "avi", "AVI" }, "AVI (*.avi, *.AVI)");

		JFileChooser jfc = new JFileChooser();

		jfc.addChoosableFileFilter(mf1);
		jfc.addChoosableFileFilter(mf2);
		jfc.addChoosableFileFilter(mf3);
		jfc.addChoosableFileFilter(mf4);
		jfc.addChoosableFileFilter(mf5);
		jfc.addChoosableFileFilter(mf6);

		jfc.setMultiSelectionEnabled(true);

		int i = jfc.showOpenDialog(null);

		File files[] = jfc.getSelectedFiles();

		if (i == JFileChooser.APPROVE_OPTION) {
			for (File f : files) {
				try {
					Files.copy(new FileInputStream(f), mainPath.resolve(f.getName()));
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
			System.out.println("GATE 1");
			checkImages(mainPath);
			int delete = JOptionPane.showConfirmDialog(null,
					"Do you want to delete original pics ? (This action can't be undone) ", "EarthNineteen", 1, 2);
			if (delete == JOptionPane.YES_OPTION) {
				for (File f : files) {
					try {
						Files.delete(f.toPath());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
			return true;
		} else {
			return false;
		}
	}

	public int getImageNum() {
		return imgList.size();
	}

	public int getVideoNum() {
		return vidList.size();
	}
	
	public void deleteFileAtIndex(int index) {
		Path p = imgList.get(index);
		imgList.remove(index);
		try {
			Files.delete(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class FFilter extends FileFilter {

		String description;
		String suffixes[];

		public FFilter(String strings[], String desc) {

			description = desc;
			suffixes = strings;

		}

		public boolean accept(File f) {

			if (f.isDirectory()) {
				return true;
			}

			String suffix = f.getName().substring(f.getName().lastIndexOf(".") + 1);

			for (String str : suffixes) {

				if (suffix.equals(str)) {
					return true;
				}

			}

			return false;
		}

		public String getDescription() {
			return description;
		}
	}
}