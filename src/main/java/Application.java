import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.Covariance;

import static org.apache.commons.math3.util.Precision.round;

public class Application {
    private static BufferedWriter bufferedWriterCov;

    public static void main(String[] args) throws IOException {
        File covariancefile = Files.createFile(Path.of("PCA.csv")).toFile();
        bufferedWriterCov = new BufferedWriter(new FileWriter(covariancefile));

        //loading file:
        List<BankCsvModel> modelList = CsvReader.getBankModelList("bank-full.csv");
        double[][] array2d = new double[modelList.size()][];
        for (int i = 0; i < modelList.size(); i++) {
            array2d[i] = modelList.get(i).toDoubleArray();
        }

        RealMatrix realmatrix = MatrixUtils.createRealMatrix(array2d);
        Covariance covariance = new Covariance(realmatrix);
        RealMatrix covarianceMatrix = covariance.getCovarianceMatrix();
        EigenDecomposition ed = new EigenDecomposition(covarianceMatrix);

        double[][] covarianceArray = covarianceMatrix.getData();
        double[] eigenValues = ed.getRealEigenvalues();

        saveWithNewLine("Macierz kowariancji:");
        printMatrix(covarianceArray, 2, bufferedWriterCov);
        for (int i = 0; i < eigenValues.length; i++) {
            saveWithNewLine("Wartosc wlasna:;" + ed.getRealEigenvalue(i) + " ");
            saveWithNewLine("Wektor wlasny:;");
            for (double var : ed.getEigenvector(i).toArray()) {
                bufferedWriterCov.write(var + ";");
            }
            bufferedWriterCov.newLine();
        }

        double[][] macierzW = ed.getV().getData();


        saveWithNewLine("Macierz W");
        printMatrix(macierzW, 7, bufferedWriterCov);

        RealMatrix Wmatrix = ed.getV();
        RealMatrix wynik = realmatrix.multiply(Wmatrix);

        
        bufferedWriterCov.write("Wynik pomnozenia bazy przez Macierz W:");
        printMatrix(wynik.getData(), 5, bufferedWriterCov);

        bufferedWriterCov.flush();
        bufferedWriterCov.close();
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

    private static void saveWithNewLine(String a) throws IOException {
        bufferedWriterCov.write(a);
        bufferedWriterCov.newLine();
    }
}
