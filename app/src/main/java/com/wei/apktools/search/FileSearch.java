package com.wei.apktools.search;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FileSearch {
	
	protected FileFilter filter;

    public FileSearch() {}
	
	public FileSearch(FileFilter filter) {
		
		this.filter = filter;
	}
	
	public FileFilter getFilter() {
		return filter;
	}

	public void setFilter(FileFilter filter) {
		this.filter = filter;
	}

    /**
     * 搜索指定目录下的所有文件或目录,
     * 把过滤后的所有文件或目录添加到ArrayList容器中
     * @param searchDir 搜索的目录
     * @return 探索的结果
     */
	public List<File> search(File searchDir) {
		
		if (searchDir == null || !searchDir.isDirectory()) {
			return null;
		}
		
		List<File> searchFile = new ArrayList<File>();
		
		// 获取源目录下的所有文件或目录
		Queue<File> searchQueue = listDir(searchDir);
		
		while (searchQueue != null && !searchQueue.isEmpty()) {
			
			File file = searchQueue.poll();
			
			if (file != null) {

                if (file.isDirectory()) {

                    Queue<File> queue1 = listDir(file);

                    if (queue1 != null) searchQueue.addAll(queue1);
                }

                if (accept(file)) searchFile.add(file);
			}
		}
		
		return searchFile;
	}

    /**
     * 列表指定目录下的所有文件或目录并存在LinkedList容器中
     * @param dir 列表的目录
     * @return 目录下的所有文件或目录
     */
	protected Queue<File> listDir(File dir) {
		
		if (dir == null || !dir.isDirectory()) {
			return null;
		}
		
		File[] files = dir.listFiles();
		
		if (files != null && files.length > 0) {
			
			Queue<File> queue = new LinkedList<File>();
			
			for (int i = 0; i < files.length; i++) {
				
				queue.add(files[i]);
			}
			
			return queue;
		}
		
		return null;
	}

    /**
     * 用来过滤文件是否可用,
     * 当不设置过滤规则时放行所有文件
     * @param file 判断需要过滤文件
     * @return true:文件可用,false:文件需要过滤
     */
	protected boolean accept(File file) {
		
		if (filter != null) return filter.accept(file);
		
		return true;
	}
}
