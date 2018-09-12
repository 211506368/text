package x211506368;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class MathExam368 {

	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("请输出所需要的题目数量：");
		try{
			
			int count = sc.nextInt();
			System.out.println("请输出年级：");
			int grade = sc.nextInt();
			File file = new File("C:\\java\\out.txt");
			if(!makeTxt(file)){
				throw new RuntimeException("文件创建失败");
			}
			String[][] RT = buildQuestion(count, grade);
			boolean isSuccess = printQuestion(file, RT);
			if(isSuccess){
				System.out.println("文件及其算术创建成功！");
			}else{
				System.out.println("文件及其算术创建失败！");
			}
		}catch(InputMismatchException e){
			System.out.println("文件及其算术创建失败!");
			System.out.println("输入的题目数量和年级必须为正整数!");
		}catch(Exception e){
			System.out.println("文件及其算术创建失败!");
		}
	}
	
	
	private static String[][] buildQuestion(int count){
		return buildQuestion(count, 2);
	}
	
	
	private static String[][] buildQuestion(int count, int grade){
		
		if(count < 0){
			count = 1;
		}
		if(grade < 1){
			grade = 1;
		}
		if(grade==1) {
			grade=1;
		}
		int limited = grade * 20;
		Random random = new Random();
		String[][] 	RT= new String[count][4];
		for (String[] arr : RT) {
			int first = random.nextInt(limited);
			//符号 0:+ 1:- 2:× 3:÷ 
			int second = random.nextInt(4);
			//第二位运算
			int third = random.nextInt(limited);
			if(grade==1) {
			if(second==0 ) {
			arr[1] = " + ";
			arr[3] = String.valueOf(first + third);	
			}
			else if(second==1 ) {
				arr[1] = " - ";
				arr[3] = String.valueOf(first - third);
				
			}
			}
			if(grade==2) {
				if(second==2) {arr[1] = " × ";
			arr[3] = String.valueOf(first * third);
			}		
				
			
			
			 if(second==3 ) {
				//乘法
					arr[1] = " ÷ ";
					
					while(third == 0){
						third = random.nextInt(limited);
					}
					//余数
					int remainder = (first%third);
					if(remainder == 0){
						arr[3] = String.valueOf(first/third);
					}else{
						arr[3] = String.valueOf(first/third) + "..." + String.valueOf(remainder);
					}
			
				
			}
			}
			  arr[0] = String.valueOf(first);
				arr[2] = String.valueOf(third);
		}
		return RT;
		
	}
	
	private static boolean printQuestion(File file, String[][] RT){
		// 仅带有问题的字符缓存器
		StringBuilder buff = new StringBuilder();
		//携带问题和答案的字符缓存器
		StringBuilder answerbuff = new StringBuilder();
		//换行符
		String newLine = "\r\n";
		//"------------------标准答案------------------"字符
		String divideStr = "------------------标准答案------------------\r\n";
		// "211606359 戴俊涵 yyyy年MM月dd日 HH：mm"
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年 MM月 dd日\tHH:mm");
		String anthorInfo = "\t\t211506368 \t\t霍金池\t\t" + sdf.format(new Date());
		
		for(int i =1; i<=RT.length; i++){
			String[] arr = RT[i-1];
			String str = "("+ i + ") " + arr[0] + arr[1] + arr[2] + " =";
			buff.append(str + newLine);
			answerbuff.append(str + " " + arr[3] + newLine);
		}
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			fos.write(buff.toString().getBytes());
			fos.write(divideStr.getBytes());
			fos.write(answerbuff.toString().getBytes());
			fos.write(anthorInfo.getBytes());
		} catch (FileNotFoundException e) {
			throw new RuntimeException("文件未找到异常", e);
		}  catch (IOException e) {
			throw new RuntimeException("输入或输出流发生异常", e);
		}
		finally{
			try {
				fos.close();
			} catch (IOException e) {
				throw new RuntimeException("输出流关闭发生异常", e);
			}
		}
		return true;
	}
	
	/**
	 * 检查文件是否存在
	 * @param f				文件目录
	 * @param fileName		文件名称
	 * @return				目录创建成功则返回true，否则返回false。
	 */
	private static boolean makeTxt(File file){
		//如果目录不存在，则创建父级目录
		if(!file.exists()){
			file.getParentFile().mkdirs();
		}
		
		//如果文件不存在，则创建文件。
		if(! file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException("文件创建失败", e);
			}
		}

		//清空文本数据，防止出现乱数据
		FileWriter fileWrite = null;
		try {
			fileWrite = new FileWriter(file);
			fileWrite.write("");
			fileWrite.flush();
		} catch (IOException e) {
			throw new RuntimeException("文件清空发生异常", e);
		}finally{
			try {
				fileWrite.close();
			} catch (IOException e) {
				throw new RuntimeException("文件写入流关闭发生异常", e);
			}
		}
		
		return file.exists();
	}
}
