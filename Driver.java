import java.io.File;
import java.io.IOException;

public class Driver {
    public static void main(String[] args) {
        Polynomial p = new Polynomial();
        System.out.println("p(3) = " + p.evaluate(3)); 

        double[] c1 = {6, 5}; 
        int[] e1 = {0, 3};  
        Polynomial p1 = new Polynomial(c1, e1);

        double[] c2 = {-2, -9};  
        int[] e2 = {1, 3};            
        Polynomial p2 = new Polynomial(c2, e2);

        
        Polynomial sum = p1.add(p2);
        System.out.println("sum(0.1) = " + sum.evaluate(0.1));  

        
        if (sum.hasRoot(1))
            System.out.println("1 is a root of sum");
        else
            System.out.println("1 is not a root of sum");

       
        Polynomial product = p1.multiply(p2);
        System.out.println("product(0.1) = " + product.evaluate(0.1));  

        
        try {
            File file = new File("polynomial.txt"); 
            Polynomial pFromFile = new Polynomial(file);
            System.out.println("pFromFile(2) = " + pFromFile.evaluate(2));  

            
            pFromFile.saveToFile("output_polynomial.txt");
            System.out.println("Polynomial saved to output_polynomial.txt");
        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }
    }
}
