AutoComplete Program

## Background

Autocomplete is a commonly used feature in mobile phones, text editors, and search engines. As a user types in letters, the system shows a list of word predictions to help the user complete the word they are typing. The core of an efficient autocompletion system is a fast algorithm for retrieving word predictions based on the user input. The word predictions are all the words (in a given dictionary) that start with what the user has typed so far (i.e., the list of words for which the user's input is a prefix).

The `AutoCompleteInterface` defines a Java interface for a dictionary that provides word predictions for such an autocompletion system. Besides storing a set of words, the dictionary keeps track of a prefix String, which starts with the empty String. 


```java
//The DLB node class
private class DLBNode {
  private char data;
  private int size;
  private boolean isWord;
  private DLBNode nextSibling;
  private DLBNode previousSibling;
  private DLBNode child;
  private DLBNode parent;

  private DLBNode(char data){
      this.data = data;
      size = 0;
      isWord = false;
      nextSibling = previousSibling = child = parent = null;
  }
}
 ```



```java
  /**
   * appends the character c to the current prefix in O(alphabet size) time. 
   * This method doesn't modify the dictionary.
   * @param c: the character to append
   * @return true if the current prefix after appending c is a prefix to a word 
   * in the dictionary and false otherwise
   */
    public boolean advance(char c){
      //TODO: implement this method
      return false;
    }

  /**
   * removes the last character from the current prefix in O(1) time. This 
   * method doesn't modify the dictionary.
   * @throws IllegalStateException if the current prefix is the empty string
   */
    public void retreat(){
      //TODO: implement this method
    }

  /**
   * resets the current prefix to the empty string in O(1) time
   */
    public void reset(){
      //TODO: implement this method
    }
```

The last set of methods operate on the prefix String by checking whether it is a word in the dictionary (`isWord()`), adding it if not (`add()`), retrieving the number predictions for the prefix String (`getNumberOfPredictions`), and retrieving one of the predictions (if any) (`retrievePrediction`). Retrieving the number predictions should be as simple as returning the `size` field of the `currentNode`. The `add()` method should increment the `size` field for some of the nodes (which ones?). (Hint: you may need to climb up the trie.)

```java
  /**
   * @return true if the current prefix is a word in the dictionary and false
   * otherwise. The running time is O(1).
   */
    public boolean isWord(){
      //TODO: implement this method
      return false;
    }

  /**
   * adds the current prefix as a word to the dictionary (if not already a word)
   * The running time is O(alphabet size*length of the current prefix). 
   */
    public void add(){
      //TODO: implement this method
    }

  /** 
   * @return the number of words in the dictionary that start with the current 
   * prefix (including the current prefix if it is a word). The running time is 
   * O(1).
   */
    public int getNumberOfPredictions(){
      //TODO: implement this method
      return 0;
    }
  
  /**
   * retrieves one word prediction for the current prefix. The running time is 
   * O(prediction.length())
   * @return a String or null if no predictions exist for the current prefix
   */
    public String retrievePrediction(){
      //TODO: implement this method
      return null;
    }
```

