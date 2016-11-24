

import java.util.Arrays;
import java.util.Comparator;

public class WelshPowell {
    
    public static int[][] getcolors (int[][] adjMatrix){
        int row;
        int column;
        int n = adjMatrix.length;
        int[][] degrees = new int[n][2];
        for (int i = 0; i <n; i++){
            degrees[i][0] = i;
        }
        for (row = 0; row <n; row++){
            for (column = 0; column <n; column++){
               degrees[row][1] += adjMatrix[row][column];
            }
        }
        int[][] colors = clone(degrees);
        Arrays.sort(degrees, Comparator.comparingInt(arr -> arr[1]));
        flip(degrees);
        for (int i =0; i <n; i++){
            colors[i][1] = 0;
            degrees[i][1] = 0;
        }
        for (int i =1; i <=n; i++){                                           
            for (int k = 0; k <n; k++){                                        
                if (degrees[k][1] == 0){                                      
                    int vertex = degrees[k][0];                               
                    boolean free = true;
                    for (int g = 0; g <n; g++){                                 
                        if (adjMatrix[vertex][g] == 1 && colors[g][1] == i){  
                            free = false;                                     
                        }
                    }
                    if (free){
                        degrees[k][1] = i;                                      
                        colors[(degrees[k][0])][1] = i;                       
                    }
                }
            }
        }
        return colors;
    }

    public static int getchromaticnr (int[][] adjMatrix){
        int row;
        int column;
        int n = adjMatrix.length;
        int[][] degrees = new int[n][2];
        for (int i = 0; i <n; i++){
            degrees[i][0] = i;
        }
        for (row = 0; row <n; row++){
            for (column = 0; column <n; column++){
               degrees[row][1] += adjMatrix[row][column];
            }
        }
        int[][] colors = clone(degrees);
        Arrays.sort(degrees, Comparator.comparingInt(arr -> arr[1]));
        flip(degrees);
        for (int i =0; i <n; i++){
            colors[i][1] = 0;
            degrees[i][1] = 0;
        }
        for (int i =1; i <=n; i++){                                           
            for (int k = 0; k <n; k++){                                        
                if (degrees[k][1] == 0){                                      
                    int vertex = degrees[k][0];                               
                    boolean free = true;
                    for (int g = 0; g <n; g++){                                 
                        if (adjMatrix[vertex][g] == 1 && colors[g][1] == i){  
                            free = false;                                     
                        }
                    }
                    if (free){
                        degrees[k][1] = i;                                      
                        colors[(degrees[k][0])][1] = i;                       
                    }
                }
            }
        }
        int max = 0;
        for (int i = 0; i < n; i++){
            if (colors[i][1] >= max){
                max = colors[i][1];
            }
        }
        return max;
    }
    


    public static void flip(int[][] data) {
        int top = 0;
        int bottom = data.length -1;
        int[] temp = new int[2];
        while (top < bottom) {
            temp[0] = data[top][0];
            temp[1] = data[top][1];
            data[top][0] = data[bottom][0];
            data[top][1] = data[bottom][1];
            data[bottom][0] = temp[0];
            data[bottom][1] = temp[1];
            top++;
            bottom--;
        }
    }
    
    public static int[][] clone(int[][] src) {
        int length = src.length;
        int[][] target = new int[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }
        return target;
    }
}