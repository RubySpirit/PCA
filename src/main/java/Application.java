import enums.Marital;
import java.util.List;

public class Application {


  public static void main (String[] args)
  {
   List<BankCsvModel> modelList= CsvReader.getBankModelList("bank.csv");
   modelList.forEach(bankCsvModel -> System.out.println(bankCsvModel.toString()));

  }
}
