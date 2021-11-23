package com.hit.bean;



public class ListNode {
      public int val;
      public ListNode next;
      public ListNode(){}
      public ListNode(int x) {
         val = x;
         next = null;
      }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        ClassLoader classLoader = ListNode.class.getClassLoader();
        Class<?> aClass = classLoader.loadClass("com.hit.bean.Node");
        Node o = (Node) aClass.newInstance();
        o.child = null;
        o.next = null;
        o.val = 999;
        o.prev = null;
        System.out.println(o);
    }
  }
