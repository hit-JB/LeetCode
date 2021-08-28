package com.hit.bean;

public class DictTree {
    public DictTree[] next = new DictTree[26];
    public boolean end = false;
    public DictTree(){

    }
    public void addWord(String word){
        DictTree node = this;
        for(char c:word.toCharArray()){
            if(node.next[c-'a']==null){
                node.next[c-'a'] = new DictTree();
            }
            node = node.next[c-'a'];
        }
        node.end = true;
    }
    public boolean containWord(String word){
        DictTree node = this;
        for(char c:word.toCharArray()){
            if(node.next[c-'a']!=null)
            {
                node = node.next[c-'a'];
                continue;
            }
            return false;
        }
        return node.end;
    }
}
