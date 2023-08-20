/**
 * An implementation of the AutoCompleteInterface using a DLB Trie.
 */

 import java.util.ArrayList;

 public class AutoComplete implements AutoCompleteInterface {

  private DLBNode root;
  private StringBuilder currentPrefix;
  private DLBNode currentNode;
  private int charPos;
  private int sync = 0;
  private String sb;
  //TODO: Add more instance variables as needed

  public AutoComplete(){
    root = null;
    currentPrefix = new StringBuilder();
    currentNode = null;
  }

  /**
   * Adds a word to the dictionary in O(alphabet size*word.length()) time
   * @param word the String to be added to the dictionary
   * @return true if add is successful, false if word already exists
   * @throws IllegalArgumentException if word is the empty string
   */
    public boolean add(String word){
      if(word.equals(""))
        throw new IllegalArgumentException("string cannot be empty");

      if(word.equals(sb)){
        return false;
      }

      DLBNode temp = root;
      charPos = 0;

      if(root == null){ //check if trie is empty
        root = new DLBNode(word.charAt(0)); // create root with first letter of word
        charPos++;
        addHelper(root,word.substring(charPos));
        return true;
      } else { //root not null -> get node at end of prefix
          while(charPos <= word.length()-1){
            if(temp.data == word.charAt(charPos)){
              temp.size++;
               if(list.contains(temp) == false)
                 list.add(temp);
            }
            if(charPos < word.length()-1 && temp.data == word.charAt(charPos)){//go to child
              if(temp.child == null){
                charPos++;
                addHelper(temp,word.substring(charPos));
              }
              temp = temp.child;
              charPos++;
            } else if(charPos == word.length()-1 && temp.data == word.charAt(charPos)){//end of word
                if(temp.isWord == false){
                  temp.isWord = true;
                  return true;
                } else { //isword == true
                  return false;
                }

            } else {//go to next sibling
              if(temp.nextSibling == null){
                temp.nextSibling = new DLBNode(word.charAt(charPos));
                temp.nextSibling.previousSibling = temp;
                temp = temp.nextSibling;
                charPos++;
                addHelper(temp,word.substring(charPos));
                return true;
              }
              temp = temp.nextSibling;
            }
          }
        }
        return true;
      }

      private void addHelper(DLBNode temp, String word){
        if(word.equals("")){
        temp.isWord = true;
        currentNode = temp;
        reset();
        return;
      }

        temp.child = new DLBNode(word.charAt(0));
        temp.child.parent = temp;
        temp = temp.child;
        if(word.length() == 1){ // last letter of string
          temp.isWord = true;
          currentNode = temp;
          reset();
          return;
        }
         addHelper(temp,word.substring(1));
      }

  /**
   * appends the character c to the current prefix in O(alphabet size) time.
   * This method doesn't modify the dictionary.
   * @param c: the character to append
   * @return true if the current prefix after appending c is a prefix to a word
   * in the dictionary and false otherwise
      also needs to keep track of currentNode
   */
    public boolean advance(char c){
      //System.out.println("currentNode " + currentNode.data);
      //TODO: implement this method
      currentPrefix = currentPrefix.append(c);
      if(sync > 0){
        sync++;
        return false;
      }

      if(currentPrefix.length()==1)
        currentNode = root;
        DLBNode temp = currentNode;

        if(currentPrefix.length() == 1 && currentNode.data == c){ //root.data == c
          return true;
        }
        else if(currentPrefix.length() == 1 && temp.data != c && temp.nextSibling!=null){ // check root siblings
          while(temp.nextSibling != null){
            temp = temp.nextSibling;
            if(temp.data == c){
              currentNode = temp;
              return true;
            }
          }
        }
        else if(temp.child != null && temp.child.data == c){
          temp = temp.child;
          currentNode = temp;
          return true;
        }
        else if(temp.child != null && temp.child.data != c && temp.child.nextSibling != null){ // check sibling
          temp = temp.child;
            while(temp.nextSibling != null){
              temp = temp.nextSibling;
              if(temp.data == c){
                currentNode = temp;
                return true;
              }
              }
          }

         // keep pointer where it is
          sync = 1;
          //System.out.println("sync = "+sync);
          return false;
    }

  /**
   * removes the last character from the current prefix in O(alphabet size) time. This
   * method doesn't modify the dictionary.
   * @throws IllegalStateException if the current prefix is the empty string
   */
    public void retreat(){
      //System.out.println(currentPrefix);

      //TODO: implement this method
      if(currentPrefix.length() == 0){
        throw new IllegalStateException("string cannot be empty");
      }
      currentPrefix = currentPrefix.deleteCharAt(currentPrefix.length()-1);
      if(sync != 0){
        sync--;
        return;
      }


      if(currentNode.parent != null){
        currentNode = currentNode.parent;
      }
      else { //check sibling
        while(currentNode.previousSibling != null){
          currentNode = currentNode.previousSibling;

        }
        currentNode = currentNode.parent;
      }
    }

  /**
   * resets the current prefix to the empty string in O(1) time
   */
    public void reset(){
      //TODO: implement this method
      sync = 0;
      currentPrefix = new StringBuilder();
    }

  /**
   * @return true if the current prefix is a word in the dictionary and false
   * otherwise. The running time is O(1).
   */
    public boolean isWord(){
      //TODO: implement this method
      if(sync > 0){
        return false;
      } else {
        return currentNode.isWord;
      }
    }

  /**
   * adds the current prefix as a word to the dictionary (if not already a word)
   * The running time is O(alphabet size*length of the current prefix).
   */
    public void add(){
      if(isWord() == false){
        add(currentPrefix.toString());
      }
    }

  /**
   * @return the number of words in the dictionary that start with the current
   * prefix (including the current prefix if it is a word). The running time is
   * O(1).
   */
    public int getNumberOfPredictions(){
      //TODO: implement this method
      if(sync != 0 || currentNode == null){

        return 0;
      }
      return currentNode.size;
    }

  /**
   * retrieves one word prediction for the current prefix. The running time is
   * O(prediction.length())
   * @return a String or null if no predictions exist for the current prefix
   */
    public String retrievePrediction(){

      //TODO: implement this method
      if(sync != 0)
        return null;

      String temp = currentPrefix.toString();
      DLBNode curr = currentNode;

      if(currentNode.isWord){
        return currentPrefix.toString();

      } else { //traverse trie and find prefix

        while(curr.child != null){
          curr = curr.child;
          temp = temp+curr.data;
          if(curr.isWord){
            sb = temp;
            return temp;
         }
        }
      }
      return null;
    }


  /* ==============================
   * Helper methods for debugging.
   * ==============================
   */

  //print the subtrie rooted at the node at the end of the start String
  public void printTrie(String start){
    System.out.println("==================== START: DLB Trie Starting from \""+ start + "\" ====================");
    if(start.equals("")){
      printTrie(root, 0);
    } else {
      DLBNode startNode = getNode(root, start, 0);
      if(startNode != null){
        printTrie(startNode.child, 0);
      }
    }

    System.out.println("==================== END: DLB Trie Starting from \""+ start + "\" ====================");
  }

  //a helper method for printTrie
  private void printTrie(DLBNode node, int depth){
    if(node != null){
      for(int i=0; i<depth; i++){
        System.out.print(" ");
      }
      System.out.print(node.data);
      if(node.isWord){
        System.out.print(" *");
      }
      System.out.println(" (" + node.size + ")");
      printTrie(node.child, depth+1);
      printTrie(node.nextSibling, depth);
    }
  }

  //return a pointer to the node at the end of the start String
  //in O(start.length() - index)
  private DLBNode getNode(DLBNode node, String start, int index){
    if(start.length() == 0){
      return node;
    }
    DLBNode result = node;
    if(node != null){
      if((index < start.length()-1) && (node.data == start.charAt(index))) {
          result = getNode(node.child, start, index+1);
      } else if((index == start.length()-1) && (node.data == start.charAt(index))) {
          result = node;
      } else {
          result = getNode(node.nextSibling, start, index);
      }
    }
    charPos = index;
    return result;
  }

  //The DLB node class
  private class DLBNode{
    private char data;
    private int size;
    private boolean isWord;
    private DLBNode nextSibling;
    private DLBNode previousSibling;
    private DLBNode child;
    private DLBNode parent;

    private DLBNode(char data){
        this.data = data;
        size = 1;
        isWord = false;
        nextSibling = previousSibling = child = parent = null;
    }
  }
}
