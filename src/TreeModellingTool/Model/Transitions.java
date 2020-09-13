package TreeModellingTool.Model;

import java.util.LinkedList;
import java.util.List;

public class Transitions {
    private static List<String> transitions = new LinkedList<>();
    private static List<Boolean[]> stackedNodes = new LinkedList<>();
    private static List<Boolean[]> markedNodes = new LinkedList<>();
    private static List<String> transitionNames = new LinkedList<>();

    private static int lastVisited = 0;

    public static void add(String s, String name){

        if (transitions.isEmpty()){
            initFirstEmptyTransition();
        }

        visitLast();
        transitions.add(s);
        transitionNames.add(name);
        lastVisited++;
        getColoredNodes(s);
       
    }

    private static void initFirstEmptyTransition() {
        transitions.add("(Empty tree)");
        Boolean[] emptyBoolArr = new Boolean[transitions.get(0).length()];
        for (int i = 0; i < transitions.get(0).length(); i++) {
            emptyBoolArr[i] = Boolean.FALSE;
        }
        stackedNodes.add(emptyBoolArr);
        markedNodes.add(emptyBoolArr);
        transitionNames.add("");
    }


    // Basically scans through looking for special symbols and marks them as decided in BinarySearchTree class diagram
    // solver. It is part of functionality used for highlighting the stacked/marked transitions of BST algorithms
    private static void getColoredNodes(String s){
        char[] diagramChars = s.toCharArray();

        Boolean [] marked =  new Boolean[s.length()];
        Boolean [] stacked =  new Boolean[s.length()];

        boolean doMarked = false;
        boolean doStacked = false;

        for (int i = 0; i < diagramChars.length; i++) {
            marked[i] = Boolean.FALSE;
            stacked[i] = Boolean.FALSE;
            switch(diagramChars[i]){
                case '%': // marked opens
                    marked[i] = true;
                    doMarked = true;
                    break;
                case '$': // stacked opens
                    stacked[i] = true;
                    doStacked = true;
                    break;
                case ']': // stacked or marked closes
                    if(doMarked){
                        marked[i] = true;
                    }
                    if(doStacked){
                        stacked[i] = true;
                    }
                    doMarked = false;
                    doStacked = false;
                    break;
                default:
                    if(doMarked){
                        marked[i] = true;
                    }
                    if(doStacked){
                        stacked[i] = true;
                    }
            }
        }
        stackedNodes.add(stacked);
        markedNodes.add(marked);

    }

    public static Boolean[] getStackedNodes(int transitionIndex){
        return stackedNodes.get(transitionIndex);
    }

    public static Boolean[] getMarkedNodes(int transitionIndex){
        return markedNodes.get(transitionIndex);
    }

    public static Boolean[] getMarkedNodes(){
        return markedNodes.get(lastVisited);
    }

    public static Boolean[] getStackedNodes(){
        return stackedNodes.get(lastVisited);
    }

    public static void clear(){
        transitions = new LinkedList<>();
        markedNodes = new LinkedList<>();
        stackedNodes = new LinkedList<>();
        transitionNames = new LinkedList<>();
        lastVisited = 0;

    }

    public static List<String> getTransitions(){
        return transitions;
    }

    public static void setTransitions(List<String> transtionsList){
        transitions = transtionsList;
    }

    public static String getTransition(int index){
        lastVisited = index;
        return transitions.get(index);
    }

    public static String getPrevTransition(){
       // if (lastVisited > 0) {
        if (lastVisited > 0 && !transitions.isEmpty()){
            lastVisited--;
            return transitions.get(lastVisited);
        }
        else return null;
    }

    public static String getNextTransition(){
        if (lastVisited < transitions.size() - 1) {
            lastVisited++;
            return transitions.get(lastVisited);
        }
        else return null;
    }

    public static String getLastTransition(){
        lastVisited = transitions.size() -1;
        return transitions.get(lastVisited);
    }

    public static void visitLast(){
        lastVisited = transitions.size() -1;
    }

    public static String getTransitionName() {
        if (lastVisited > transitionNames.size()-1){
            return null;
        }
        return transitionNames.get(lastVisited);
    }

    public int getLastVisited(){
        return lastVisited;
    }

}
