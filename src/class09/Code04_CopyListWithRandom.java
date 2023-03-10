package src.class09;

import java.util.HashMap;
import java.util.Map;

/**
 * 一种特殊的单链表节点类描述如下 class Node { int value; Node next; Node rand; Node(int val)
 * {value = val; } }
 * 
 * rand指针是单链表节点结构中新增的指针，rand可能指向链表中的任意一个节点，也可能指向null
 * 给定一个由Node节点类型组成的无环单链表的头节点head，请实现一个函数完成这个链表的复制
 * 
 * 返回复制的新链表的头节点，要求时间复杂度O(N)，额外空间复杂度O(1)
 * 
 * 测试链接 : https://leetcode.com/problems/copy-list-with-random-pointer/
 */
public class Code04_CopyListWithRandom {
	public static class Node {
		int val;
		Node next;
		Node random;

		public Node(int v) {
			val = v;
			next = null;
			random = null;
		}
	}

	/**
	 * use extra space n
	 * 
	 * @param head 头结点
	 * @return Node 拷贝链表头结点
	 */
	public static Node copyRandomList1(Node head) {
		Map<Node, Node> map = new HashMap<Node, Node>();

		Node cur = head;
		while (cur != null) {
			map.put(cur, new Node(cur.val));
			cur = cur.next;
		}

		cur = head;
		while (cur != null) {
			map.get(cur).next = map.get(cur.next);
			map.get(cur).random = map.get(cur.random);
			cur = cur.next;
		}

		return map.get(head);
	}

	/**
	 * use extra space 1
	 * 
	 * @param head 头结点
	 * @return Node 拷贝链表头结点
	 */
	public static Node copyRandomList2(Node head) {
		if (head == null) {
			return head;
		}
		
		Node cur = head;
		Node next = null;
		while (cur != null) {
			next = cur.next;
			cur.next = new Node(cur.val);
			cur.next.next = next;
			cur = next;
		}
		
		cur = head;
		Node copy = null;
		while (cur != null) {
			next = cur.next.next;
			copy = cur.next;
			copy.random = cur.random != null ? cur.random.next : null;
			cur = next;
		}
		
		Node res = head.next;
		cur = head;
		while (cur != null) {
			next = cur.next.next;
			copy = cur.next;
			cur.next = next;
			copy.next = next != null ? next.next : null;
			cur = next;
		}
		
		return res;
	}
}
