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


  public static void main (String[] args)
  {
   List<BankCsvModel> modelList= CsvReader.getBankModelList("bank-full.csv");
   double [][] array2d = new double[modelList.size()][];
      for (int i = 0; i < modelList.size() ; i++) {
          array2d[i] = modelList.get(i).toDoubleArray();
      }
/*  double [][]array2d = {
          {57.5,0.525,57,51,-47},
          {7.5,0.505,49,47,-69},
          {-67.5,0.545,49,43,-75},
          {-77.5,0.515,49,33,-71},
          {-57.5,0.535,49,29,-75},
          {-357.5,0.325,25,19,-87}
  };*/
      RealMatrix realmatrix = MatrixUtils.createRealMatrix(array2d);
      Covariance covariance = new Covariance(realmatrix);
      RealMatrix covarianceMatrix = covariance.getCovarianceMatrix();
      EigenDecomposition ed = new EigenDecomposition(covarianceMatrix);
    //      covariance.getCovarianceMatrix().




      double [][] covarianceArray=covarianceMatrix.getData();
      double [] eigenValues = ed.getRealEigenvalues();
      //System.out.println(ed.getDeterminant());

      System.out.println("Macierz kowariancji:");
      printMatrix(covarianceArray);
      for (int i= 0;i<eigenValues.length;i++)
      {
          System.out.println("Wartosc wlasna:;" +ed.getRealEigenvalue(i) + " ");
          System.out.println("Wektor wlasny:;");
          for(double var:ed.getEigenvector(i).toArray())
          {
              System.out.print(var+";");
          }
          System.out.println("");
      }
      System.out.println("");


  /*    System.out.println("wektory własne");

      for(int i=0;i<ed.getRealEigenvalues().length;i++) {
          System.out.println(ed.getEigenvector(i));
      }*/
      double [][]wyznacznikMacierzy =ed.getD().getData();


      System.out.println("Wyznacznik macierzy");
      printMatrix(wyznacznikMacierzy);
 /*     try {
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
      }*/
  }

    private static void printMatrix(double[][] wyznacznikMacierzy) {
        for(double[] wyz:wyznacznikMacierzy)
        {
            for (double v: wyz)
            {
                System.out.print(" "+ round(v,2)+"  ;");
            }
            System.out.println("");
        }


        System.out.println("");
    }
}
