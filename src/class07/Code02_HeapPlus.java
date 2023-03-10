package src.class07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * 加强堆的实现, 小根堆heapInsert、heapify比较判断小于0; 大根堆比较判断大于0
 * 
 * @author peiyiding
 *
 */
public class Code02_HeapPlus<T> {
	
	/**
	 * 内部包装类, 包装Integer整形, 防止map, put的时候按值传递, key会丢失
	 * @param <T> 泛型
	 */
//	public static class Inner<T> {
//		private T val;
//		
//		public Inner(T value) {
//			val = value;
//		}
//	}
	
	private ArrayList<T> heap;
	private HashMap<T, Integer> indexMap;
	private int heapSize;
	private Comparator<? super T> comparator;
	
	public Code02_HeapPlus(Comparator<? super T> comparator) {
		heap = new ArrayList<>();
		indexMap = new HashMap<>();
		heapSize = 0;
		this.comparator = comparator;
	}
	
	public boolean isEmpty() {
		return heapSize == 0;
	}
	
	public int size() {
		return heapSize;
	}
	
	public boolean contains(T obj) {
		return indexMap.containsKey(obj);
	}
	
	public T peek() {
		return heap.get(0);
	}
	
	public void push(T obj) {
		heap.add(obj);
		indexMap.put(obj, heapSize);
		heapInsert(heapSize++);
	}
	
	public T pop() {
		T ans = heap.get(0);
		swap(0, heapSize - 1);
		indexMap.remove(ans);
		heap.remove(--heapSize);
		heapify(0);
		return ans;
	}
	
	public void remove(T obj) {
		T replace = heap.get(heapSize - 1);
		int index = indexMap.get(obj);
		indexMap.remove(obj);
		heap.remove(--heapSize);
		
		if (obj != replace) {
			heap.set(index, replace);
			indexMap.put(replace, index);
			resign(replace);
		}
	}
	
	public void resign(T obj) {
		heapInsert(indexMap.get(obj));
		heapify(indexMap.get(obj));
	}
	
	public List<T> getAllElements() {
		List<T> ans = new ArrayList<>();
		
		for (T element : heap) {
			ans.add(element);
		}
		
		return ans;
	}

	/**
	 * 插入的数值往上走
	 * 
	 * @param index
	 */
	public void heapInsert(int index) {
		while (comparator.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) {
			swap(index, (index - 1) / 2);
			index = (index - 1) / 2;
		}
	}
	
	/**
	 * 堆往下沉
	 * 
	 * @param index 下标
	 */
	public void heapify(int index) {
		int left = index * 2 + 1;
		while (left < heapSize) {
			int min = left + 1 < heapSize && (comparator.compare(heap.get(left + 1), heap.get(left)) < 0) ? left + 1 : left;
			min = comparator.compare(heap.get(min), heap.get(index)) < 0 ? min : index;
			
			if (min == index) {
				break;
			}
			
			swap(index, min);
			index = min;
			left = index * 2 + 1;
		}
	}
	
	/**
	 * 交换方法
	 * 
	 * @param i 下标
	 * @param j 下标
	 */
	public void swap(int i, int j) {
		T o1 = heap.get(i);
		T o2 = heap.get(j);
		
		heap.set(i, o2);
		heap.set(j, o1);
		
		indexMap.put(o2, i);
		indexMap.put(o1, j);
	}
}
