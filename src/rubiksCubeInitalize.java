import java.util.ArrayList;

public class rubiksCubeInitalize {

    public int[] currentState;
    public int[] solvedState;

    public void intialize() {
        // Here is a scramble for a 14 move scramble  15, 16, 17, 8, 6, 7, 3, 4, 5, 14, 12, 13, 18, 19, 20, 2, 0, 1, 9, 10, 11, 21, 22, 23
        currentState = new int[]{15, 16, 17, 8, 6, 7, 3, 4, 5, 14, 12, 13, 18, 19, 20, 2, 0, 1, 9, 10, 11, 21, 22, 23};
        solvedState = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
    }

    public String codeDecoder(ArrayList<int[]> code) {
        String[] values = new String[]{"F", "F'", "U", "U'", "L", "L'"};
        String[] valuesPrime = new String[]{"F'", "F", "U'", "U", "L'", "L"};
        String results = "";
        if(code.get(0) != new int[0]) {
            for(int i = 0; i < code.get(0).length; i++) {
                results += values[code.get(0)[i]-1] + " ";
            }
        }
        if(code.size() == 2) {
            for(int i = 0; i < code.get(1).length; i++) {
                results += valuesPrime[code.get(1)[i]-1] + " ";
            }
        }
        return results;
    }

    public int[] cubeEncoder(String code) {
        int[] result = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
        String perm = "";
        if(code.charAt(code.length()-1) != ' ') {
            code += " ";
        }
        for(int i = 0; i < code.length(); i++) {
            char character = code.charAt(i);
            if(character != ' ') {
                perm += character;
            } else {
                switch (perm) {
                    case "F":
                        result = this.f(result);
                        break;
                    case "F'":
                        result = this.fi(result);
                        break;
                    case "U":
                        result = this.u(result);
                        break;
                    case "U'":
                        result = this.ui(result);
                        break;
                    case "L":
                        result = this.l(result);
                        break; 
                    case "L'":
                        result = this.li(result);
                        break;  
                    default:
                        System.out.println("A wrong value was detected: " + perm);
                        return result;
                }
                perm = "";
            }
        }
        return result;
    }

    // All these methods are for turning the cube. Maybe try seeing if there is a pattern instead,
    // and use a for loop which would make it more elegant.

    public int[] f(int[] state) {
        int[] tempState = new int[24];
        for(int i = 0; i < state.length; i++) {
            tempState[i] = state[i];
        }
        tempState[0] = state[9];
        tempState[1] = state[10];
        tempState[2] = state[11];
        tempState[3] = state[0];
        tempState[4] = state[1];
        tempState[5] = state[2];
        tempState[6] = state[3];
        tempState[7] = state[4];
        tempState[8] = state[5];
        tempState[9] = state[6];
        tempState[10] = state[7];
        tempState[11] = state[8];
        return tempState;
    }

    public int[] fi(int[] state) {
        int[] tempState = new int[24];
        for(int i = 0; i < state.length; i++) {
            tempState[i] = state[i];
        }
        tempState[0] = state[3];
        tempState[1] = state[4];
        tempState[2] = state[5];
        tempState[3] = state[6];
        tempState[4] = state[7];
        tempState[5] = state[8];
        tempState[6] = state[9];
        tempState[7] = state[10];
        tempState[8] = state[11];
        tempState[9] = state[0];
        tempState[10] = state[1];
        tempState[11] = state[2];
        return tempState;
    }

    public int[] u(int[] state) {
        int[] tempState = new int[24];
        for(int i = 0; i < state.length; i++) {
            tempState[i] = state[i];
        }
        tempState[0] = state[5];
        tempState[3] = state[16];
        tempState[5] = state[15];
        tempState[16] = state[12];
        tempState[15] = state[14];
        tempState[12]= state[1];
        tempState[14] = state[0];
        tempState[1] = state[3];
        tempState[2] = state[4];
        tempState[4] = state[17];
        tempState[13] = state[2];
        tempState[17] = state[13];
        return tempState;
    }

    public int[] ui(int[] state) {
        int[] tempState = new int[24];
        for(int i = 0; i < state.length; i++) {
            tempState[i] = state[i];
        }
        tempState[0] = state[14];
        tempState[3] = state[1];
        tempState[5] = state[0];
        tempState[16] = state[3];
        tempState[15] = state[5];
        tempState[12]= state[16];
        tempState[14] = state[15];
        tempState[1] = state[12];
        tempState[2] = state[13];
        tempState[4] = state[2];
        tempState[13] = state[17];
        tempState[17] = state[4];
        return tempState;
    }

    public int[] l(int[] state) {
        int[] tempState = new int[24];
        for(int i = 0; i < state.length; i++) {
            tempState[i] = state[i];
        }
        tempState[0] = state[13];
        tempState[9] = state[2];
        tempState[10] = state[0];
        tempState[20] = state[9];
        tempState[18] = state[10];
        tempState[12] = state[20];
        tempState[13] = state[18];
        tempState[2] = state[12];
        tempState[1] = state[14];
        tempState[11] = state[1];
        tempState[19] = state[11];
        tempState[14] = state[19];
        return tempState;
    }

    public int[] li(int[] state) {
        int[] tempState = new int[24];
        for(int i = 0; i < state.length; i++) {
            tempState[i] = state[i];
        }
        tempState[0] = state[10];
        tempState[9] = state[20];
        tempState[10] = state[18];
        tempState[20] = state[12];
        tempState[18] = state[13];
        tempState[12] = state[2];
        tempState[13] = state[0];
        tempState[2] = state[9];
        tempState[1] = state[11];
        tempState[11] = state[19];
        tempState[19] = state[14];
        tempState[14] = state[1];
        return tempState;
    }
}
