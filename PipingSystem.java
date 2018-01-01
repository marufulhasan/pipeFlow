package pipingsystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PipingSystem {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
      
        // User input starts here. Nodes values are pressure, fluid density and viscosity. 
        // Pipes values are flow rates, length, diameter and pipe roughness.
        // use negative value if unknown
        // GUI Required
        
        List<List<Double>> nodes = new ArrayList<>();
        nodes.add(new ArrayList(Arrays.asList(100.0, 62.428, 0.000671968)));
        nodes.add(new ArrayList(Arrays.asList(90.0, 62.428, 0.000671968)));

        List<List<Double>> pipes = new ArrayList<>();
        pipes.add(new ArrayList(Arrays.asList(-120.2, 3800.0, 2.0, 0.00015)));
        pipes.add(new ArrayList(Arrays.asList(-1258.0, 100.00, 1.5, 0.00015)));
        pipes.add(new ArrayList(Arrays.asList(-13658.0, 1000.00, 1.0, 0.00015)));
        
        // user input ends here
        
        System.out.println(Arrays.toString(fluidCalc.getflowrate(nodes, pipes)));
        System.out.println(Arrays.toString(fluidCalc.getflowrate(nodes, pipes,35000.0)));
       

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println(totalTime);
    }

}
