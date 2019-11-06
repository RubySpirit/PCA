import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.Covariance;

import static org.apache.commons.math3.util.Precision.round;

public class Application {
    private static BufferedWriter bw;

    public static void main(String[] args) throws IOException {
        //załadowanie pliku do pamięci:
        List<BankCsvModel> modelList = CsvReader.getBankModelList("bank.csv");
        double[][] array2d = new double[modelList.size()][];
        for (int i = 0; i < modelList.size(); i++) {
            array2d[i] = modelList.get(i).toDoubleArray();
        }
        //zamiana tablicy 2d na macierz
        RealMatrix realmatrix = MatrixUtils.createRealMatrix(array2d);
        //obliczenie kowariancji z macierzy
        Covariance covariance = new Covariance(realmatrix);
        //zapisanie kowariancji do macierzy
        RealMatrix covarianceMatrix = covariance.getCovarianceMatrix();
        //utworzenie obiektu odpowiedzialnego za dekompozycje na wartości własne, wektory własne
        EigenDecomposition ed = new EigenDecomposition(covarianceMatrix);
        double[][] covarianceArray = covarianceMatrix.getData();
        //wyznaczenie wartosci własnych
        double [] eigenValues = ed.getRealEigenvalues();
        //utworzenie macierzy W
        double[][] macierzW = ed.getV().getData();
        RealMatrix Wmatrix = ed.getV();
        //pomnożenie macierzy W przez DataSet
        RealMatrix wynik = realmatrix.multiply(Wmatrix);


        File covariancefile = Files.createFile(Path.of("PCA02.csv")).toFile();
        bw = new BufferedWriter(new FileWriter(covariancefile));
        saveWithNewLine("Macierz kowariancji:");
        printMatrix(covarianceArray, 2, bw);
        Arrays.stream(eigenValues).forEach(value -> saveWithNewLine("Wartosc wlasna:;" + value));
        for (int i = 0; i < eigenValues.length; i++) {
            for (double var : ed.getEigenvector(i).toArray()) {
                saveWithNewLine("Wektor wlasny:;");
                bw.write(var + ";");
            }
            bw.newLine();
        }

        saveWithNewLine("Macierz W");
        printMatrix(macierzW, 7, bw);
        saveWithNewLine("Wynik pomnozenia bazy przez Macierz W:");
        printMatrix(wynik.getData(), 5, bw);
        bw.flush();
        bw.close();
    }

    private static void printMatrix(double[][] wyznacznikMacierzy, int scale, BufferedWriter bw) throws IOException {
        for (double[] wyz : wyznacznikMacierzy) {
            for (double v : wyz) {
                bw.write(" " + round(v, scale) + "  ;");
            }
            bw.newLine();
        }
        bw.newLine();
    }

    private static void saveWithNewLine(String a) {
        try {
            bw.write(a);
            bw.newLine();
        }
        catch (IOException ioe)
        {
            System.out.println("problem z plikiem");
        }
    }
}
