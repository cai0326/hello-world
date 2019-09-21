import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class Sudoku {

	public static void main(String[] args)throws IOException {
		// TODO 自动生成的方法存根
		int m= Integer.parseInt(args[1]);
		int n= Integer.parseInt(args[3]);
		int arr[][]=new int[m][m];
		int l;
		int i=0,j=0,k=0,a,b;
		String s="";
		File file1=new File(args[5]);
		File file2=new File(args[7]);
		for(l=1;l<=n;l++) {
		try {
			String str=null;
			FileReader fr=new FileReader(file1);
			BufferedReader bufr=new BufferedReader(fr);
			for(i=0;i<(m+1)*l-1;i++)
			{
				str=bufr.readLine();
				String[] strs = str.split(" ");
				for (j=0;j<m;j++){
					if((i-(l-1)*(m+1))>-1) {
					    arr[i-(l-1)*(m+1)][j]=Integer.parseInt(strs[j]);
					}
				}
			} 
        bufr.close();
	    }catch(Exception e) {
		     e.printStackTrace();
	     }
		boolean[][] cols = new boolean[m][m];
		boolean[][] rows = new boolean[m][m];
		boolean[][] blocks = new boolean[m][m];
		for (i = 0; i < arr.length; i++) {
			for (j = 0; j < arr.length; j++) {
				if (arr[i][j] != 0) {
					if(m==6||m==9) {
						a=m/3;
						b=i/a*a;
						k=j/3+b;
					}
					else if(m==4||m==8) {
						a=m/2;
						b=i/a*a;
					    k=j/2+b;
					}
					int val = arr[i][j] - 1;
					rows[i][val] = true;
					cols[j][val] = true;
					blocks[k][val] = true;
				}
			}
		}// 数据装载完毕
		if(m%2!=0&&m!=9) {
			DFS1(arr,m, cols, rows);
		}
		else {
			DFS2(arr,m, cols, rows,blocks);
		}
		for(i=0;i<m;i++) {
	    	for(j=0;j<m;j++)
	    	{
	    		String c = Integer.toString(arr[i][j]);
	            s=s+c;
	            if(j<m-1)
	            s=s+" ";//拼接成字符串，最终放在变量s中
	    	}
	    	if(i<m-1)
	    	s=s+"\r\n";
	    }
		if(l<n)
		s=s+"\r\n"+"\r\n";
		}
		try {
		    FileWriter fw=new FileWriter(file2);
		    BufferedWriter bufw=new BufferedWriter(fw);
		    System.out.println(s);
		    bufw.write(s);
		    bufw.flush();
		    bufw.close();
	    }catch(Exception e) {
	     e.printStackTrace();
	    }
    }
	public static boolean DFS2(int[][] arr,int m, boolean[][] cols, boolean[][] rows,boolean[][] blocks) {
		int a,b,k=0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < m; j++) {
				if (arr[i][j] == 0) {
						if(m==6||m==9) {
							a=m/3;
							b=i/a*a;
							k=j/3+b;
						}
						else if(m==4||m==8) {
							a=m/2;
							b=i/a*a;
						    k=j/2+b;
						}
					for (int l = 0; l < m; l++) {
						if (!cols[j][l] && !rows[i][l]&& !blocks[k][l]) {// l对于的数字l+1没有在行列块中出现
							rows[i][l] = cols[j][l]= blocks[k][l]=true;
							arr[i][j] = 1 + l;// 下标加1
							if (DFS2(arr,m, cols, rows,blocks))
								return true;// 递进则返回true
							rows[i][l] = cols[j][l] = blocks[k][l]=false;// 递进失败则回溯
							arr[i][j] = 0;
						}
					}
					return false;// a[i][j]==0时，l发现都不能填进去
				}// the end of a[i][j]==0
			}
		}
		return true;// 没有a[i][j]==0,则返回true
	}
	public static boolean DFS1(int[][] arr,int m, boolean[][] cols, boolean[][] rows) {
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < m; j++) {
				if (arr[i][j] == 0) {
					for (int l = 0; l < m; l++) {
						if (!cols[j][l] && !rows[i][l]) {// l对于的数字l+1没有在行列块中出现
							rows[i][l] = cols[j][l]=true;
							arr[i][j] = 1 + l;// 下标加1
							if (DFS1(arr,m, cols, rows))
								return true;// 递进则返回true
							rows[i][l] = cols[j][l] =false;// 递进失败则回溯
							arr[i][j] = 0;
						}
					}
					return false;// a[i][j]==0时，l发现都不能填进去
				}// the end of a[i][j]==0
			}
		}
		return true;// 没有a[i][j]==0,则返回true
	}
}