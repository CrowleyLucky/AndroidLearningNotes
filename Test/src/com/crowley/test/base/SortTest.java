package com.crowley.test.base;

import java.util.Arrays;
import java.util.Random;

import junit.framework.TestCase;

public class SortTest extends TestCase {
	public final int DATA_SIZE = 100000;
	
	public void testKindsOfSort() {
		//���ɲ�������
		Double[] originalDatas = new Double[DATA_SIZE];
		Random random = new Random();
		for(int i = 0; i < DATA_SIZE; i++) {
			originalDatas[i] = random.nextInt(DATA_SIZE) + random.nextDouble();
		}
		Double[] ascendOrder = Arrays.copyOf(originalDatas, DATA_SIZE);
		Double[] bubbleSortDatas = Arrays.copyOf(originalDatas, DATA_SIZE);
		Double[] selectionSortDatas = Arrays.copyOf(originalDatas, DATA_SIZE);
		Double[] quickSortDatas = Arrays.copyOf(originalDatas, DATA_SIZE);
		
		
		

		//���������������Ҫ��ʱ�䣺
		long startTime = System.nanoTime();
		
		Arrays.sort(ascendOrder);//ʹ��ϵͳ��������Ϊ�����Ե������㷨�Ƿ���ȷ�����ݡ�
		System.out.println("JRE Arrays.sort() sort duration:" + (System.nanoTime() - startTime) + "ns");
		
		this.quickSort(quickSortDatas, 0, quickSortDatas.length - 1);//�Բ�������ʹ��ѡ������
		System.out.println("Quick sort duration:" + (System.nanoTime() - startTime) + "ns");
		//����ѡ����������Ҫ��ʱ�䣺
		startTime = System.nanoTime();
		this.selectionSort(selectionSortDatas);//�Բ�������ʹ��ѡ������
		System.out.println("Selection sort duration:" + (System.nanoTime() - startTime) + "ns");
		//����ð����������Ҫ��ʱ�䣺
		startTime = System.nanoTime();
		this.bubbleSort(bubbleSortDatas);//�Բ�������ʹ��ð������
		System.out.println("Bubble sort duration:" + (System.nanoTime() - startTime) + "ns");
		
		//assertEquals(ascendOrder, this.bubbleSort(datas2));//����ȽϵĲ��������ֵ�������������ã�Ӧ�õ���Arrays.equals(xxx, xxx);
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
	
	/*�ⲻ��ð�������е���ѡ�������ˣ�������
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
