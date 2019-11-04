import enums.Marital;
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

public class Application {


  public static void main (String[] args)
  {
   List<BankCsvModel> modelList= CsvReader.getBankModelList("bank-full.csv");
   double [][] array2d = new double[modelList.size()][];
      for (int i = 0; i < modelList.size() ; i++) {
          array2d[i] = modelList.get(i).toDoubleArray();
      }

      RealMatrix realmatrix = MatrixUtils.createRealMatrix(array2d);
      Covariance covariance = new Covariance(realmatrix);
      RealMatrix covarianceMatrix = covariance.getCovarianceMatrix();
      EigenDecomposition ed = new EigenDecomposition(covarianceMatrix);

      double [][] covarianceArray=covarianceMatrix.getData();
      double [] eigenValues = ed.getRealEigenvalues();
      System.out.println(ed.getDeterminant());

      for (double e: eigenValues
           ) {
          System.out.print(e + " ");
      }
      System.out.println("");
      try {
          File covariancefile = Files.createFile(Path.of("covariance.csv")).toFile();
          BufferedWriter bufferedWriterCov = new BufferedWriter(new FileWriter(covariancefile));

          for (double[] doubles : covarianceArray) {
              double[] dob = doubles;
              for (double v : dob) {
                  bufferedWriterCov.write(v + ";");
              }
              bufferedWriterCov.newLine();
          }
          bufferedWriterCov.flush();
          bufferedWriterCov.close();


      } catch (IOException ioe)
      {
          System.out.println("nie udało sie zapisać plików");
      }
  }
}
