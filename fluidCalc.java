package pipingsystem;

import java.util.List;

class fluidCalc {

    // getdp calculates pressure drop given inlet pressure, flow rate and single pipe information
    public static double getdp(List<List<Double>> nodes, List<List<Double>> pipes, int pipeNo) {
        double area, volflow, velocity, re;

        // reading data from input starts here
        double rho = nodes.get(0).get(1);
        double mu = nodes.get(0).get(2);
        double massFlow = pipes.get(pipeNo).get(0);
        double length = pipes.get(pipeNo).get(1);
        double dia = pipes.get(pipeNo).get(2) / 12;
        double roughness = pipes.get(pipeNo).get(3);

        // reading ends
        area = Math.PI * dia * dia / 4;
        volflow = massFlow / rho;
        velocity = volflow / (area * 3600);

        re = (dia * velocity * rho) / mu;
        // calculate friction factor using colebrook correlation
        double f = colebrook.f(roughness / dia, Math.abs(re));
        double headLoss = f * length * velocity * velocity / (dia * 2 * 32.2);

        return (velocity / Math.abs(velocity)) * headLoss * (rho / 62.428) / 2.31;

    }

    // getflowrate calculates flow rate using dp between two adjacent nodes and pipe information
    static double[] getflowrate(List<List<Double>> nodes, List<List<Double>> pipes) {
        double tol = 1;
        double dp = nodes.get(0).get(0) - nodes.get(1).get(0);

        double[] flow = new double[pipes.size()];

        if (dp == 0) {
            for (int i = 0; i < pipes.size(); i++) {
                flow[i] = 0;
            }
            return flow;
        }

        for (int i = 0; i < pipes.size(); i++) {
            flow[i] = (dp / Math.abs(dp)) * 10000;
        }
        for (int i = 0; i < 100; i++) {

            for (int j = 0; j < pipes.size(); j++) {

                pipes.get(j).set(0, flow[j]);
                double dpnew = getdp(nodes, pipes, j);
                flow[j] = flow[j] * Math.pow(dp / dpnew, 0.5);
                tol = Math.abs((dp - dpnew) / dpnew);

                if (tol < 1e-9) {
                    break;
                }

            }
            if (tol < 1e-9) {
                break;
            }
        }

        return flow;

    }

    // Calculate dp using inlet flow and system of pipe information
    static double[] getflowrate(List<List<Double>> nodes, List<List<Double>> pipes, double Q) {

        double[] flows = new double[pipes.size()];
        double p2old = nodes.get(0).get(0) - 5;

        for (int it = 0; it < 100; it++) {
            nodes.get(1).set(0, p2old);
            flows = getflowrate(nodes, pipes);
            double sum = 0;
            for (int i = 0; i < flows.length; i++) {
                sum += flows[i];
            }
            // guess new prssure based on previous assumed pressure and calculated flow
            double p2new = p2old * Math.pow((sum / Q), 0.2);
            if (Math.abs(p2new - p2old) < 1e-7) {
                break;
            }
            p2old = p2new;

        }
        return flows;

    }

}
