package pipingsystem;

//Clamond, Didier. "Efficient resolution of the Colebrook equation." Industrial & Engineering Chemistry Research, February, 2009: 3665–3671.
public class colebrook {

    static double f(double K, double R) {

        // Laminer Flow
        if (R < 2100) {
            return 64 / R;
        }

        // Turbulent Flow
        double F, E;
        double X1 = K * R * 0.12396818633;
        double X2 = Math.log(R) - 0.7793974884;

        F = X2 - 0.2;
        E = (Math.log(X1 + F) + F - X2) / (1 + X1 + F);
        F = F - (1 + X1 + F + 0.5 * E) * E * (X1 + F) / (1 + X1 + F + E * (1 + E / 3));

        E = (Math.log(X1 + F) + F - X2) / (1 + X1 + F);
        F = F - (1 + X1 + F + 0.5 * E) * E * (X1 + F) / (1 + X1 + F + E * (1 + E / 3));

        F = 1.151292546497022842 / F;

        return F * F;

    }

}
