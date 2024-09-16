public class Polynomial {
    double[] Array;

    public Polynomial() {
        Array = new double[]{0};  
    }

    public Polynomial(double[] A) {
        Array = A;
    }

    public Polynomial add(Polynomial p) {
        int maxLength = Math.max(Array.length, p.Array.length);
        double[] resultArray = new double[maxLength];

        for (int i = 0; i < maxLength; i++) {
            double thisCoeff = (i < Array.length) ? Array[i] : 0;
            double otherCoeff = (i < p.Array.length) ? p.Array[i] : 0;
            resultArray[i] = thisCoeff + otherCoeff;
        }

        return new Polynomial(resultArray);
    }

    public double evaluate(double x) {
        double result = 0; 
        for (int i = 0; i < Array.length; i++) {
            result += Math.pow(x, i) * Array[i];
        }
        return result;
    }

    public boolean hasRoot(double root) {
        return evaluate(root) == 0; 
    }
}
