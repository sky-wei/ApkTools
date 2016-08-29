package com.wei.apktools.task;

import com.wei.apktools.interfaces.OutputInfo;
import com.wei.apktools.utils.Log;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 当前类主要用于执行相关的命令<br/>
 *
 * <p>{@link #exec(String[])}用来执行一组命令</p>
 *
 * @author jingcai.wei
 */
public class Execute {

    public enum Environment {
        Windows, Linux
    }

	private String[] envp;
	private File workDir;

	private Environment enumeration;
	private OutputInfo outputInfo;

	public Execute() {
		this(null, new File(System.getProperty("user.dir")));
	}

    /**
     * 设置执行命令的指定的环境变量
     * @param envp 运行时环境变量
     */
	public Execute(String[] envp) {
		this(envp, new File(System.getProperty("user.dir")));
	}

    /**
     * 设置执行命令的指定工作目录
     * @param workDir 运行时的工作目录
     */
	public Execute(File workDir) {
		this(null, workDir);
	}
	
	public Execute(String[] envp, File workDir) {
		
		this.envp = envp;
		this.workDir = workDir;

        initExecute();
	}

	public OutputInfo getOutputInfo() {
		return outputInfo;
	}

	public void setOutputInfo(OutputInfo outputInfo) {
		this.outputInfo = outputInfo;
	}

	public String[] getEnvp() {
		return envp;
	}

	public File getWorkDir() {
		return workDir;
	}

    public void setEnvp(String[] envp) {
        this.envp = envp;
    }

    public void setWorkDir(File workDir) {
        this.workDir = workDir;
    }

    public Environment getEnumeration() {
        return enumeration;
    }

    private void initExecute() {

        // 设置默认系统环境
        enumeration = Environment.Windows;

        String osName = System.getProperty("os.name");

        if (osName.startsWith("Windows ")) {

            enumeration = Environment.Windows;
        } else if (osName.startsWith("Linux")) {

            enumeration = Environment.Linux;
        }
    }

    /**
     * 把一组String[]命令转换成List,并且跳过第一个命令
     * @param commands 需要转换的命令
     * @return 转换后的命令
     */
    private List<String> resolveCommands(String[] commands) {

        List<String> outputExecute = new ArrayList<String>();

        for (int i = 1; i < commands.length; i++) {

            outputExecute.add(commands[i]);
        }

        return outputExecute;
    }

    /**
     * cmd|sh中执行指定的一组命令(并把执行的结果输出到流中)<br/>
     * 例如:Windows{"cmd", "java -version", "exit"}<br/>
     * 例如:Linux{"sh", "java -version", "exit"}<br/>
     * 使用方法:{"sh|cmd", commands, "exit"}<br/>
     * @param commands 执行的命令
     * @throws BrutException
     */
    public void insertionExec(String... commands) throws BrutException {

        if (commands == null) {
            throw new NullPointerException();
        }

        String[] newCommands = new String[commands.length + 3];

        // 获取相应系统下的命令
        newCommands[0] = (enumeration == Environment.Windows) ? "cmd" : "sh";

        // 最后添加的退出命令
		newCommands[newCommands.length - 2] = "sleep(2000)";
        newCommands[newCommands.length - 1] = "exit";

        // 复制需要执行的命令
        System.arraycopy(commands, 0, newCommands, 1, commands.length);

        // 执行整个命令
        exec(newCommands);
    }

    /**
     * 执行指定的一组命令(并把执行的结果输出到流中)<br/>
     * 执行命令时,可以休眠再执行下一条命令(单位:毫秒)<br/>
     * 例如:{"java -version", "sleep(1000)", "java"}<br/>
     * 注意:休眠命令不能放在第一条命令上<br/>
     * @param commands 执行的命令
     * @throws BrutException
     */
    public void exec(String... commands) throws BrutException {
		
		if (commands == null) {
			throw new NullPointerException();
		}
    	
		Process process = null;

		try {
			String command = commands[0];

			process = Runtime.getRuntime().exec(command, envp, workDir);

            InputStream in = process.getInputStream();
            InputStream ine = process.getErrorStream();
            OutputStream out = process.getOutputStream();

			new OutputExecute(out, resolveCommands(commands)).start();
			new StreamForwarder(in, outputInfo).start();
			new StreamForwarder(ine, outputInfo).start();

			if (process.waitFor() != 0) {
				throw new BrutException(
                        "could not exec command: " + Arrays.toString(commands));
			}
		} catch (IOException ex) {
			throw new BrutException(
                    "could not exec command: " + Arrays.toString(commands), ex);
		} catch (InterruptedException ex) {
			throw new BrutException(
                    "could not exec command: " + Arrays.toString(commands), ex);
		} finally {

            // destroy方法会关闭自己流对象
            if (process != null) process.destroy();
		}
    }

    /**
     * 执行命令时命令无法执行异常类
     */
	public class BrutException extends Exception {

		private static final long serialVersionUID = 4256669169296619920L;

		public BrutException(String msg) {
			
			super(msg);
		}
		
		public BrutException(String msg, Throwable t) {
			
			super(msg, t);
		}
	}
	
	protected class StreamForwarder extends Thread {

        private final InputStream mIn;
        private final OutputInfo mOut;
		
        public StreamForwarder(InputStream in, OutputInfo out) {
            mIn = in;
            mOut = out;
        }

        @Override
        public void run() {
            try {
				String line;
				BufferedReader reader = new BufferedReader(new InputStreamReader(mIn));

    			while ((line = reader.readLine()) != null) {
    				mOut.onOutput(line);
    			}
            } catch (IOException ex) {
				mOut.onOutput("输出信息异常", ex);
            }
        }
    }
	
	protected class OutputExecute extends Thread {

		private OutputStream out;
		private List<String> commands;
		
		public OutputExecute(OutputStream out, List<String> commands) {
			
			this.out = out;
			this.commands = commands;
		}
		
		@Override
		public void run() {
			super.run();
			
			try {
				for (int i = 0; i < commands.size(); i++) {
					
					String command = commands.get(i) + "\n";
					
					if (command.startsWith("sleep(")) {
						
						// 需要休眠一下
						String time = command.substring(command.indexOf('(') + 1, command.lastIndexOf(')'));
						long sleepTime = Integer.parseInt(time);
						Thread.sleep(sleepTime);
						continue;
					}
					
					out.write(command.getBytes());
					out.flush();
				}
			} catch (Exception e) {
				Log.e("执行命令异常", e);
			}
		}
	}
}
