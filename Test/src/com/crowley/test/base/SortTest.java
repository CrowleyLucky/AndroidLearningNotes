package com.crowley.test.base;

import java.util.Arrays;
import java.util.Random;

import junit.framework.TestCase;

public class SortTest extends TestCase {
	public final int DATA_SIZE = 100000;
	
	public void testKindsOfSort() {
		//生成测试数据
		Double[] originalDatas = new Double[DATA_SIZE];
		Random random = new Random();
		for(int i = 0; i < DATA_SIZE; i++) {
			originalDatas[i] = random.nextInt(DATA_SIZE) + random.nextDouble();
		}
		Double[] ascendOrder = Arrays.copyOf(originalDatas, DATA_SIZE);
		Double[] bubbleSortDatas = Arrays.copyOf(originalDatas, DATA_SIZE);
		Double[] selectionSortDatas = Arrays.copyOf(originalDatas, DATA_SIZE);
		Double[] quickSortDatas = Arrays.copyOf(originalDatas, DATA_SIZE);
		
		
		

		//计算快速排序所需要的时间：
		long startTime = System.nanoTime();
		
		Arrays.sort(ascendOrder);//使用系统的排序作为本测试的排序算法是否正确的依据。
		System.out.println("JRE Arrays.sort() sort duration:" + (System.nanoTime() - startTime) + "ns");
		
		this.quickSort(quickSortDatas, 0, quickSortDatas.length - 1);//对测试数据使用选择排序
		System.out.println("Quick sort duration:" + (System.nanoTime() - startTime) + "ns");
		//计算选择排序所需要的时间：
		startTime = System.nanoTime();
		this.selectionSort(selectionSortDatas);//对测试数据使用选择排序
		System.out.println("Selection sort duration:" + (System.nanoTime() - startTime) + "ns");
		//计算冒泡排序所需要的时间：
		startTime = System.nanoTime();
		this.bubbleSort(bubbleSortDatas);//对测试数据使用冒泡排序
		System.out.println("Bubble sort duration:" + (System.nanoTime() - startTime) + "ns");
		
		//assertEquals(ascendOrder, this.bubbleSort(datas2));//这里比较的不是数组的值，不能这样调用，应该调用Arrays.equals(xxx, xxx);
		System.out.println("Quick sort is right? " + Arrays.equals(ascendOrder, quickSortDatas));
		System.out.println("Selection sort is right? " + Arrays.equals(ascendOrder, selectionSortDatas));
		System.out.println("Bubble sort is right? " + Arrays.equals(ascendOrder, bubbleSortDatas));
		
	}
	
	private void printDoubleArray(double[] datas) {
		for(int i = 0; i < datas.length; i++) {
			System.out.print(datas[i] + ",");
		}
	}
	
	public void testBinaryInsert() {
		double[] datas = new double[10];
		datas = this.binaryInsert(datas, 1);
		datas = this.binaryInsert(datas, 2);
		datas = this.binaryInsert(datas, 64);
		datas = this.binaryInsert(datas, 233);
		datas = this.binaryInsert(datas, 23);
		datas = this.binaryInsert(datas, 15);
		datas = this.binaryInsert(datas, 184);
		datas = this.binaryInsert(datas, 65);
		datas = this.binaryInsert(datas, 3);
		datas = this.binaryInsert(datas, 123);
		datas = this.binaryInsert(datas, 2003);
	}
	
	int len = 0;
	public double[] binaryInsert(double[] datas, double value) {
		if(datas == null || datas.length < len) {
			return null;
		}
		if(datas.length == len) {
			double[] newDatas = new double[len + 5];
			System.arraycopy(datas, 0, newDatas, 0, len);
			datas = newDatas;
		}
		int pos = this.findInsertPosition(datas, value, 0, len - 1);
		System.out.println();
		//System.out.println("pos:" + pos + ", value:" + value + ", len:" + len);
		//this.printDoubleArray(datas);
		for(int i = len - 1; i >= pos; i--) {
			datas[i + 1] = datas[i];
		}
		//System.out.print("||||");
		datas[pos] = value;
		len ++;
		this.printDoubleArray(datas);
		return datas;
	}
	
	private int findInsertPosition(double[] datas, double value, int start, int end) {
		int low = start;
		int high = end;
		int mid = 0;
		if(datas == null || len == 0) {
			return 0;
		}
		
		while(low <= high) {
			mid = (low + high) / 2;
			if(datas[mid] > value) {
				high = mid - 1;
			} else if(datas[mid] < value) {
				low = mid + 1;
			} else {
				return mid;
			}
		}
		if(datas[mid] < value) {
			return mid + 1;
		}
		
		return mid;
	}
	
	
	/**
	 * Bubble sort, time complexity : n*n
	 * @param datas
	 * @return
	 */
	public Double[] bubbleSort(Double[] datas) {
		for(int i = 0; i < datas.length - 1; i++) {
			for(int j = 0; j < datas.length - i - 1; j++) {
				if(datas[j] > datas[j + 1]) {
					double temp = datas[j];
					datas[j] = datas[j + 1];
					datas[j + 1] = temp;
				}
			}
		}
		
		return datas;
	}
	/**
	 * Selection sort, time complexity : n*n
	 * @param datas
	 * @return
	 */
	public Double[] selectionSort(Double[] datas) {
		for(int i = 0; i < datas.length - 1; i++) {
			int min = i;
			for(int j = i + 1; j < datas.length ; j++) {
				if(datas[min] > datas[j]) {
					min = j;
				}
			}
			if(i != min) {
				double temp = datas[i];
				datas[i] = datas[min];
				datas[min] = temp;
			}
		}
		
		return datas;
	}
	/**
	 * Quick sort, time complexity n*logn
	 * @param datas
	 * @param start
	 * @param end
	 * @return
	 */
	public Double[] quickSort(Double[] datas, int start, int end) {
		int left = start;
		int right = end;
		
		double key = datas[left];
		
		while(left != right) {
			
			for(; left < right; right--) {
				if(datas[right] < key) {
					datas[left] = datas[right];
					break;
				}
			}
			
			for(; left < right; left++) {
				if(datas[left] > key) {
					datas[right] = datas[left];
					break;
				}
			}
		}
		datas[left] = key;
		
		if(start < left) {
			quickSort(datas, start, left - 1);
		}
		if(right < end) {
			quickSort(datas, right + 1, end);
		}
		return datas;
	}
	
	/*这不是冒泡排序，有点像选择排序了！！！！
	 * public Double[] bubbleSort(Double[] datas) {
		for(int i = 0; i < datas.length - 1; i++) {
			for(int j = i + 1; j < datas.length; j++) {
				if(datas[i] > datas[j]) {
					double temp = datas[i];
					datas[i] = datas[j];
					datas[j] = temp;
				}System.out.println(Arrays.deepToString(datas));
			}
			break;
		}
		
		return datas;
	}*/
	
}
