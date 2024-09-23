import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial {
    double[] coeff;
    int[] exp;

    public Polynomial() {
        coeff = new double[]{0};
        exp = new int[]{0};
    }

    public Polynomial(double[] C, int[] E) {
        coeff = C;
        exp = E;
    }

   public Polynomial(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine(); 
        reader.close();

        Pattern termPattern = Pattern.compile("([+-]?\\d+)(x(\\d+))?");
        Matcher matcher = termPattern.matcher(line);

        int termCount = 0;
        while (matcher.find()) {
            termCount++;
        }

        coeff = new double[termCount];
        exp = new int[termCount];

        matcher.reset(); 
        int index = 0;
        while (matcher.find()) {
            String coefficient = matcher.group(1); 
            String exponent = matcher.group(3); 

            coeff[index] = Double.parseDouble(coefficient);
            exp[index] = (exponent == null) ? 0 : Integer.parseInt(exponent); 
            index++;
        }
    }

    public Polynomial add(Polynomial p) {
        List<Double> newCoeff = new ArrayList<>();
        List<Integer> newExp = new ArrayList<>();

        int i = 0, j = 0;

        while (i < exp.length && j < p.exp.length) {
            if (exp[i] == p.exp[j]) {
                double sum = coeff[i] + p.coeff[j];
                if (sum != 0) { 
                    newCoeff.add(sum);
                    newExp.add(exp[i]);
                }
                i++;
                j++;
            } else if (exp[i] < p.exp[j]) {
                newCoeff.add(coeff[i]);
                newExp.add(exp[i]);
                i++;
            } else {
                newCoeff.add(p.coeff[j]);
                newExp.add(p.exp[j]);
                j++;
            }
        }

        while (i < exp.length) {
            newCoeff.add(coeff[i]);
            newExp.add(exp[i]);
            i++;
        }

        while (j < p.exp.length) {
            newCoeff.add(p.coeff[j]);
            newExp.add(p.exp[j]);
            j++;
        }

        double[] resultCoeff = newCoeff.stream().mapToDouble(Double::doubleValue).toArray();
        int[] resultExp = newExp.stream().mapToInt(Integer::intValue).toArray();

        return new Polynomial(resultCoeff, resultExp);
    }

    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < exp.length; i++) {
            result += Math.pow(x, exp[i]) * coeff[i];
        }
        return result;
    }

    public boolean hasRoot(double root) {
        return evaluate(root) == 0;
    }

    public Polynomial multiply(Polynomial p) {
        Map<Integer, Double> result = new HashMap<>();

        for (int i = 0; i < exp.length; i++) {
            for (int j = 0; j < p.exp.length; j++) {
                int newExp = exp[i] + p.exp[j];
                double newCoeff = coeff[i] * p.coeff[j];

                result.put(newExp, result.getOrDefault(newExp, 0.0) + newCoeff);
            }
        }

        List<Double> resultCoeff = new ArrayList<>(result.values());
        List<Integer> resultExp = new ArrayList<>(result.keySet());

        return new Polynomial(
            resultCoeff.stream().mapToDouble(Double::doubleValue).toArray(),
            resultExp.stream().mapToInt(Integer::intValue).toArray()
        );
    }

    public void saveToFile(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < coeff.length; i++) {
            if (coeff[i] > 0 && i > 0) {
                sb.append("+"); 
            }
            sb.append(coeff[i]);

            if (exp[i] > 0) {
                sb.append("x").append(exp[i]); 
            }
        }

        writer.write(sb.toString());
        writer.close();
    }
}
