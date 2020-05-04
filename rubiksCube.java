import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class rubiksCube {

    public class graph {
        public ArrayList<int[][][]> createGraph(int[] state) {

            int[] neighbors = new int[6];
            ArrayList<int[][][]> graph = new ArrayList<int[][][]>();
            int[][][] start = new int[2][2][24];
            start[0][1] = state;
            start[0][0] = new int[1];
            start[1][0] = neighbors;
            graph.add(0, start);

            return graph;
        }

        public void printGraph(ArrayList<int[][][]> graph) {
            for(int i = 0; i < graph.size(); i++) {
                System.out.print(Arrays.toString(graph.get(i)[0][0]));
                System.out.print("----");
                System.out.print(Arrays.toString(graph.get(i)[0][1]));
                System.out.print("----");
                System.out.println(Arrays.toString(graph.get(i)[1][0]));
            }
        }

        public ArrayList<int[][][]> createLayer(ArrayList<int[][][]> graph) {
            int size = graph.size();
            for(int i = 0; i < size; i++) {
                if(graph.get(i)[1][0][0] == 0) {
                    rubiksCubeInitalize r = new rubiksCubeInitalize();
                    int[][] permutations = new int[][]{r.f(graph.get(i)[0][1]), r.fi(graph.get(i)[0][1]), r.u(graph.get(i)[0][1]),
                                                        r.ui(graph.get(i)[0][1]), r.l(graph.get(i)[0][1]), r.li(graph.get(i)[0][1])};
                    for(int k = 0; k < 6; k++) {
                        int[] neighbors = new int[6];
                        int[] moves;
                        //Here we find the moves and add them if neccesary.
                        if(graph.get(i)[0][0][0] != 0) {
                            int moveSize = graph.get(i)[0][0].length;
                            //Copies moves over
                            moves = new int[moveSize+1];
                            for(int j = 0; j < moveSize; j++) {
                                moves[j] = graph.get(i)[0][0][j];
                            }
                            //Adds another value to moves
                            moves[moveSize] = k+1;
                        } else {
                            moves = new int[]{k+1};
                        }
                        graph.get(i)[1][0][k] = graph.size();
                        graph.add(new int[][][]{{moves, permutations[k]}, {neighbors}});
                    }
                }
            }
            return graph;
        }

        public ArrayList<int[]> checkEqual(ArrayList<int[][][]> cGraph, ArrayList<int[][][]> sGraph, ArrayList<Integer> sNeighborList, ArrayList<Integer> cNeighborList) {
            int[] cState;
            int[] sState;
            boolean fCheck = false;
            boolean sCheck = false;
            boolean tCheck = false;
            ArrayList<int[]> answerMove = new ArrayList<int[]>();
            // This is the slowest part of the algorithm runs in O(N^2) time because of the two four loops.
            for(int i = 0; i < cNeighborList.size(); i++) {
                cState = cGraph.get(cNeighborList.get(i))[0][1];

                // Here we do a check for as a progress
                float val = (float) i/cNeighborList.size();
                if(val >= 0.75 && fCheck == false) {
                    fCheck = true;
                    System.out.print("-75%-");
                } else if(val >= 0.50 && sCheck == false) {
                    sCheck = true;
                    System.out.print("-50%-");
                } else if(val >= 0.25 && tCheck == false) {
                    tCheck = true;
                    System.out.print("-25%-");
                }
                for(int j = 0; j < sNeighborList.size(); j++) {
                    sState = sGraph.get(sNeighborList.get(j))[0][1];
                    if(Arrays.equals(cState, sState)) {
                        int[] cMoves = cGraph.get(cNeighborList.get(i))[0][0];
                        int[] sMoves = sGraph.get(sNeighborList.get(j))[0][0];
                        // Here we reverse all moves in sMoves
                        int[] temp = new int[sMoves.length];
                        for(int k = 0; k < sMoves.length; k++) {
                            temp[k] = sMoves[sMoves.length-k-1];
                        }
                        sMoves = temp;
                        answerMove.add(cMoves);
                        answerMove.add(sMoves);
                        return answerMove;
                    }
                }
            }
            return answerMove;
        }

        public ArrayList<int[]> BFS(ArrayList<int[][][]> currentGraph, ArrayList<int[][][]> solvedGraph, int[] currentState, int[] solvedState) {
            // Here is the current variables
            ArrayList<int[]> currentQueue = new ArrayList<int[]>();
            int currentNodeSpot = 0;
            currentQueue.add(currentState);

            // Here is the solved variables
            ArrayList<int[]> solvedQueue = new ArrayList<int[]>();
            int solvedNodeSpot = 0;
            solvedQueue.add(solvedState);

            // Here are all the main neighbor that hold most information.
            ArrayList<Integer> solvedNeighborList = new ArrayList<Integer>();
            ArrayList<Integer> currentNeighborList = new ArrayList<Integer>();
            ArrayList<Integer> currentPrevNeighList = new ArrayList<Integer>();
            ArrayList<Integer> solvedPrevNeighList = new ArrayList<Integer>();

            int counter = 0;
            while(counter < 7) {
                int[] currentNode = currentQueue.get(0);
                int[] solvedNode = solvedQueue.get(0);
                int[] currentNeighbors = new int[6];
                int[] solvedNeighbors = new int[6];

                // Here we check if the currentNode happens to be the solved position;
                if(Arrays.equals(currentNode, solvedState)) {
                    ArrayList<int[]> moves = new ArrayList<int[]>();
                    moves.add(currentGraph.get(currentNodeSpot)[0][0]);
                    System.out.println("Completed");
                    return moves;
                }

                // Here we check if the solvedNode happens to get to the current state.
                if(Arrays.equals(solvedNode, currentState)) {
                    ArrayList<int[]> moves = new ArrayList<int[]>();
                    moves.add(new int[0]);
                    int[] solveArray = solvedGraph.get(solvedNodeSpot)[0][0];
                    int[] temp = new int[solveArray.length];
                        for(int k = 0; k < solveArray.length; k++) {
                            temp[k] = solveArray[solveArray.length-k-1];
                        }
                    moves.add(temp);
                    System.out.println("Completed");
                    return moves;
                }

                // Here we check if we show extend to the next layer in the graph.
                int length = currentGraph.get(currentNodeSpot)[0][0].length;
                if(length > counter) {
                    counter++;
                    ArrayList<Integer> tempCurrent = new ArrayList<Integer>();
                    tempCurrent.addAll(currentNeighborList);
                    tempCurrent.addAll(currentPrevNeighList);
                    ArrayList<Integer> tempSolved = new ArrayList<Integer>();
                    tempSolved.addAll(currentNeighborList);
                    tempSolved.addAll(currentPrevNeighList);
                    ArrayList<int[]> answers = checkEqual(currentGraph, solvedGraph, tempSolved, tempCurrent);
                    if(!answers.isEmpty()){
                        System.out.println("Completed");
                        return answers;
                    } else {
                        System.out.println("Moving to next layer");
                    }
                    currentPrevNeighList.clear();
                    currentPrevNeighList.addAll(currentNeighborList);
                    solvedPrevNeighList.clear();
                    solvedPrevNeighList.addAll(solvedNeighborList);
                    currentNeighborList.clear();
                    solvedNeighborList.clear();
                    currentGraph = this.createLayer(currentGraph);
                    solvedGraph = this.createLayer(solvedGraph);
                }

                // Here we get a list of neighbors from the node;
                currentNeighbors = currentGraph.get(currentNodeSpot)[1][0];
                solvedNeighbors = solvedGraph.get(solvedNodeSpot)[1][0];

                for(int i = 0; i < 6; i++) {

                    // This is for the current graph
                    int currentId = currentNeighbors[i];
                    int[] cNode = currentGraph.get(currentId)[0][1];
                    currentQueue.add(cNode);
                    currentNeighborList.add(currentId);

                    // This is for the solved graph
                    int solvedId = solvedNeighbors[i];
                    int[] sNode = solvedGraph.get(solvedId)[0][1];
                    solvedQueue.add(sNode);
                    solvedNeighborList.add(solvedId);
                }

                currentQueue.remove(currentNode);
                solvedQueue.remove(solvedNode);
                currentNodeSpot++;
                solvedNodeSpot++;
            }

            System.out.println("Error: Could not find a match.");
            return new ArrayList<int[]>();
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        rubiksCubeInitalize rubik = new rubiksCubeInitalize();
        rubiksCube r = new rubiksCube();
        graph g = r.new graph();
        rubik.intialize();
        int[] solvedState = rubik.solvedState;

        // As a side project you could create a GUI instead.
        boolean isRunning = true;
        while(isRunning) {
            System.out.println("");
            System.out.println("Write 'exit' to leave");
            System.out.println("Write 'solve' to use the algorithm");
            System.out.print("Write here: ");
            String option = scan.nextLine();
            System.out.println("");
            switch(option.toLowerCase()) {
                case "exit" :
                    isRunning = false;
                    break;
                case "solve" :
                    System.out.print("Enter scramble: ");
                    int[] currentState = rubik.cubeEncoder(scan.nextLine());
                    ArrayList<int[][][]> cGraph = g.createGraph(currentState);
                    ArrayList<int[][][]> sGraph = g.createGraph(solvedState);
                    cGraph = g.createLayer(cGraph);
                    sGraph = g.createLayer(sGraph);
                    ArrayList<int[]> output = g.BFS(cGraph, sGraph, currentState, solvedState);
                    System.out.println(rubik.codeDecoder(output));
                    System.out.print("Continue, yes or no: ");
                    String answer = scan.nextLine().toLowerCase();
                    if(answer.equals("no")) {
                        isRunning = false;
                    } else if(!answer.equals("yes")) {
                        System.out.println("Invalid option");
                    }
                    break;
                default :
                    System.out.println("Invalid option");
                    break;
            }
        }
        scan.close();
    }
}